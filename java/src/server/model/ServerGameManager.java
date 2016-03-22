package server.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import server.Log;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.GameManager;
import shared.model.GameModel;
import shared.model.ModelException;
import shared.model.Player;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.model.MapGenerator;

/**
 * Special formation of the game manager 
 * @author matthewcarlson
 *
 */
public class ServerGameManager extends GameManager {
	private	boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	
	private Map<Integer,Integer> playerIndexLookup;
	
	public ServerGameManager(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts, int index) 
	{
		super();
		this.gameTitle = name;
		this.gameID = index;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.randomTiles = randomTiles;
		this.playerIndexLookup = new HashMap<>();
		this.gameID = index;
		this.map = MapGenerator.GenerateMap(randomTiles, randomNumbers, randomPorts);
	}

	/**
	 * Updates the version when doing an action
	 */
	private void updateVersion()
	{
		this.version++;
	}
	
	/**
	 * Returns the player index by id
	 * @param playerID
	 * @return
	 */
	public int GetPlayerIndexByID(int playerID)
	{
		return playerIndexLookup.get(playerID);
	}
	
	@Override
	public int AddPlayer(String name, CatanColor color, boolean isHuman, int playerID) throws ModelException
	{
		int index = super.AddPlayer(name, color, isHuman, playerID);
		playerIndexLookup.put(playerID, index);
		
		//Start the game if we have 4 players
		if (this.players.size() == 4){
			this.StartGame();
			this.updateVersion();
		}
			
		
		return index;
	}
	
	/**
	 * Sends a chat for the user
	 * @param playerID by Player ID
	 * @param message to chat with
	 */
	public boolean ServerSendChat(int playerID, String message)
	{
		int playerIndex = GetPlayerIndexByID(playerID);
		if (super.canChat(playerIndex))
		{
			super.PlayerChat(playerIndex, message);
			this.updateVersion();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param number
	 * @return
	 */
	public boolean ServerRollNumber(int playerID, int number)
	{
		int playerIndex = GetPlayerIndexByID(playerID);
		if (!super.CanRollNumber(playerIndex)) 
			return false;
		
		try 
		{
			super.DiceRoll(number);
			this.updateVersion();
			return true;
		}
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param playerID the ID of the player who is moving the robber
	 * @param victimIndex the index of the victim
	 * @param location the new location of the robber
	 * @return true if successful, false if not
	 */
	public boolean ServerRobPlayer(int playerID, int victimIndex, Coordinate location)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);
		
		if (gameState.state != GameRound.ROBBING) 
			return false;
		
		//Check if it's this player's turn
		int currentPlayer = this.GetPlayerIndexByID(playerID);
		if (super.CurrentPlayersTurn() != currentPlayer) 
			return false;
		
		if (!this.map.CanPlaceRobber(location))
			return false;
		
		boolean couldRob = this.ServerExecuteRob(playerIndex, victimIndex, location);
		
		this.updateVersion();		
		return couldRob;
	}
	
	/**
	 * Actually executes the robbing action. NOTE: No rule checking takes place in this function
	 * @param playerIndex
	 * @param victimIndex
	 * @param location
	 * @return
	 */
	private boolean ServerExecuteRob(int playerIndex, int victimIndex, Coordinate location)
	{
		try
		{
			map.PlaceRobber(location);
			Log.GetLog().log(Level.INFO, "Game " + this.gameID + ": Player " + playerIndex 
					+ " moved the robber");
		}
		catch(MapException e)
		{
			e.printStackTrace();
			return false;
		}
		
		ResourceType takenResource = this.takeRandomResourceCard(playerIndex, victimIndex);
		Log.GetLog().log(Level.INFO, "Game " + this.gameID + ": Player " + playerIndex + " took a "
				+ takenResource.toString() + " from Player " + victimIndex);
		
		return true;
	}
	
	/**
	 * Ends a player's turn
	 * @param playerID
	 * @return
	 */
	public boolean ServerFinishTurn(int playerID)
	{
		int currentPlayer = this.GetPlayerIndexByID(playerID);
		if (super.CurrentPlayersTurn() != currentPlayer) 
			return false;
		
		//Go to the next turn
		if (gameState.nextTurn())
		{
			this.updateVersion();
			return true;
		}
		return false;
	}
	
	/**
	 * Buys a dev card
	 * @param playerID
	 * @return
	 */
	public boolean ServerBuyDevCard(int playerID)
	{
		int currentPlayer = this.GetPlayerIndexByID(playerID);
		if (super.CurrentPlayersTurn() != currentPlayer) 
			return false;
		
		if (!super.CanBuyDevCard(currentPlayer))
			return false;
		
		//Buy the dev card
		try 
		{
			super.BuyDevCard(currentPlayer);
			this.updateVersion();
			return true;
		}
		catch (ModelException e) //they didn't have the resources 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param res1
	 * @param res2
	 * @return
	 */
	public boolean ServerYearOfPlenty(int playerID, ResourceType res1, ResourceType res2)
	{
		return false;
	}
	
	

	/**
	 * 
	 * @param playerID
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean ServerRoadBuilding(int playerID, Coordinate p1, Coordinate p2)
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param location
	 * @param victimIndex the victim
	 * @return
	 */
	public boolean ServerSoldier(int playerID, Coordinate location, int victimIndex)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);

		if(this.CurrentPlayersTurn() != playerIndex)
		{
			return false;
		}
		
		if(!this.CanPlayDevCard(playerIndex, DevCardType.SOLDIER))
		{
			return false;
		}
		
		boolean couldRob = this.ServerExecuteRob(playerIndex, victimIndex, location);
		
		//ONLY take the soldier card if this player could actually execute the robbing
		//action
		if(couldRob)
		{
			Player pPlayer = players.get(playerIndex);
			Bank bPlayer = pPlayer.playerBank;
			bPlayer.giveDevCard(DevCardType.SOLDIER);
			int armySize = pPlayer.incrementArmySize();
			this.victoryPointManager.checkPlayerArmySize(playerIndex, armySize);
		}
		
		this.updateVersion();
		return couldRob;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param p
	 * @return
	 */
	public boolean ServerBuildRoad(int playerID, Coordinate start, Coordinate end, boolean free)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		
		CatanColor color = this.getPlayerColorByIndex(playerIndex);
		if (!this.map.CanPlaceRoad(start, end, color))
			return false;
		
		//If they get a road for free
		if (free && !this.gameState.IsSetup())
			return false;
		
		
		//Build the road
		try 
		{
			this.BuildRoad(playerIndex, start, end, free);
		}
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		this.updateVersion();
		return true;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param p
	 * @return
	 */
	public boolean ServerBuildCity(int playerID, Coordinate p)
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param p
	 * @return
	 */
	public boolean ServerBuildSettlement(int playerID, Coordinate p, boolean free)
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param playerIndexTo
	 * @param resourceList
	 * @return
	 */
	public boolean ServerOfferTrade(int playerID, int playerIndexTo, List<Integer> resourceList )
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param willAccept
	 * @return
	 */
	public boolean ServerAcceptTrade(int playerID, boolean willAccept)
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param in
	 * @param out
	 * @return
	 */
	public boolean ServerMaritimeTrading(int playerID, ResourceType in, ResourceType out)
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param resourceList
	 * @return
	 */
	public boolean ServerDiscardCards(int playerID, List<Integer> resourceList)
	{
		return false;
	}
	
	private ResourceType takeRandomResourceCard(int receiver, int giver)
	{
		Player pReceiver = players.get(receiver);
		Player pGiver = players.get(giver);
		Bank bReceiver = pReceiver.playerBank;
		Bank bGiver = pGiver.playerBank;
		
		ResourceType rGiven = bGiver.giveRandomResource();
		
		//if the giver can't give a resource, return null
		if(rGiven == null)
		{
			return null;
		}
		
		//give the resource to the robbing player
		try
		{
			bReceiver.getResource(rGiven);
		}
		catch(ModelException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return rGiven;		
	}
	
	
	/**
	 * Gets the server's current game model in a serializable form
	 * @return
	 */
	public Serializable ServerGetSerializableModel()
	{
		return (Serializable) this.ServerGetModel();
	}
	
	/**
	 * Gets the current game model
	 * @return
	 */
	public GameModel ServerGetModel()
	{
		GameModel gm = new GameModel();
		
		gm.gameBank = this.gameBank;
		gm.gameState = this.gameState;
		gm.log = this.log;
		gm.mapModel = this.map;
		gm.players = this.players;
		gm.version = this.version;
		gm.waterCooler = this.waterCooler;
		gm.victoryPointManager = this.victoryPointManager;
		
		return gm;
	}
	
	
}

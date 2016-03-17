package server.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.definitions.CatanColor;
import shared.definitions.GameRound;
import shared.definitions.ResourceType;
import shared.model.GameManager;
import shared.model.GameModel;
import shared.model.ModelException;
import shared.model.map.Coordinate;
import shared.model.map.model.MapGenerator;
import shared.model.map.model.MapModel;

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
	 * Rob a player
	 * @param playerID
	 * @param playerIndex
	 * @param hex
	 * @return
	 */
	public boolean ServerRobPlayer(int playerID, int playerIndex, Coordinate hex)
	{
		if (gameState.state != GameRound.ROBBING) 
			return false;
		
		//Check if it's this player's turn
		int currentPlayer = this.GetPlayerIndexByID(playerID);
		if (super.CurrentPlayersTurn() != currentPlayer) 
			return false;
		
		
		return false;
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
		return gameState.nextTurn();
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
	 * Gets the server's current game model into the game model object
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
	 * @param p1
	 * @param playerIndex the victim
	 * @return
	 */
	public boolean ServerSolider(int playerID, Coordinate p1, int playerIndex)
	{
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param p
	 * @return
	 */
	public boolean ServerBuildRoad(int playerID, Coordinate p)
	{
		return false;
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
	public boolean ServerBuildSettlement(int playerID, Coordinate p)
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
	
}

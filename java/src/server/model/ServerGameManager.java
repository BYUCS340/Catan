package server.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import server.Log;
import server.ai.AIHandler;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ResourceType;
import shared.model.*;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.model.MapGenerator;

/**
 * Special formation of the game manager
 * @author matthewcarlson
 *
 */
public class ServerGameManager extends GameManager
{
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
		this.playerIndexLookup = new HashMap<Integer,Integer>();
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
		if (this.players.size() == 4)
			this.StartGame();

		this.updateVersion();

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
			for (Player p: this.players)
			{
				if (p.isARobot() && playerIndex != p.playerIndex())
					AIHandler.GetHandler().Chat(p.playerID(), this.gameID, message);
			}
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
	public boolean ServerRollNumber(int playerIndex, int number)
	{
		Log.GetLog().log(Level.INFO, "Player " + playerIndex + " rolled " +number);
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
	public boolean ServerRobPlayer(int playerIndex, int victimIndex, Coordinate location)
	{
		if (gameState.state != GameRound.ROBBING)
			return false;

		//Check if it's this player's turn
		if (super.CurrentPlayersTurn() != playerIndex)
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
		if (takenResource != null)
			Log.GetLog().log(Level.INFO, "Game " + this.gameID + ": Player " + playerIndex + " took a "
				+ takenResource.toString() + " from Player " + victimIndex);
		else
			Log.GetLog().log(Level.INFO, "Game " + this.gameID + ": Player " + playerIndex + " tried to take"
				+ " a card from Player " + victimIndex);

		return gameState.stopRobbing();
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

			int current = this.CurrentPlayersTurn();
			for (Player player : this.players)
			{
				if (player.playerIndex() == current && player.isARobot())
				{
					int aiID = player.playerID();
					AIHandler.GetHandler().RunAI(aiID, gameID);
					break;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if a player is a robot
	 * @param index
	 * @return
	 */
	private boolean IsPlayerRobot(int index)
	{
		for (Player player : this.players)
		{
			if (player.playerIndex() == index && player.isARobot())
			{
				return true;
			}
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

		this.updateVersion();
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
	 * @param playerID
	 * @param res1
	 * @return
	 */
	public boolean ServerMonopoly(int playerID, ResourceType res1)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if (!this.CanPlayDevCard(playerID, DevCardType.MONOPOLY))
			return false;

		try
		{
			//take all cards of type res1 from other players
			int totalResourceCount = 0;
			for (int i = 0; i < players.size(); i++)
			{
				if (i != playerIndex)
				{
					int tempCt = players.get(i).playerBank.getResourceCount(res1);
					totalResourceCount += tempCt;
					players.get(i).playerBank.getResource(res1, players.get(i).playerBank.getResourceCount(res1));
				}
			}

			//give cards taken to current player
			players.get(playerIndex).playerBank.giveResource(res1, totalResourceCount);

			//remove dev card from player
			this.playDevCard(playerID, DevCardType.MONOPOLY);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
	 * @param playerID
	 * @return
	 */
	public boolean ServerMonument(int playerID)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if (!this.CanPlayDevCard(playerID, DevCardType.MONUMENT))
			return false;

		try
		{
			//give victory point to player
			this.victoryPointManager.playedMonument(playerIndex);

			//remove dev card from player
			this.playDevCard(playerID, DevCardType.MONUMENT);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
	 *
	 * @param playerID
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public boolean ServerRoadBuilding(int playerID, Coordinate start1, Coordinate end1,  Coordinate start2, Coordinate end2)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if (!this.CanPlayDevCard(playerID, DevCardType.ROAD_BUILD))
			return false;

		CatanColor color = this.getPlayerColorByIndex(playerIndex);

		if (!this.map.CanPlaceRoad(start1, end1, color) || !this.map.CanPlaceRoad(start2, end2, color))
			return false;

		try
		{
			//build the roads
			this.BuildRoad(playerIndex, start1, end1, true);
			this.BuildRoad(playerIndex, start2, end2, true);
			this.victoryPointManager.playerBuiltRoad(playerIndex);

			//remove dev card from player
			this.playDevCard(playerID, DevCardType.ROAD_BUILD);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
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
		this.map.SetupPhase(free);

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
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
<<<<<<< HEAD
	 * 
	 * @param playerIndex
=======
	 *
	 * @param playerID
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	 * @param p
	 * @return
	 */
	public boolean ServerBuildCity(int playerIndex, Coordinate p)
	{
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		CatanColor color = this.getPlayerColorByIndex(playerIndex);
		if (!this.map.CanPlaceCity(p, color))
			return false;

		try
		{
			this.BuildCity(playerIndex, p);
		}
		catch (ModelException e)
		{
			Log.GetLog().throwing("ServerGameManager", "ServerBuildCity", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
<<<<<<< HEAD
	 * 
	 * @param playerIndex
=======
	 *
	 * @param playerID
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	 * @param p
	 * @return
	 */
	public boolean ServerBuildSettlement(int playerIndex, Coordinate p, boolean free)
	{
		try
		{
			if (!this.CanPlayerPlay(playerIndex))
				return false;

			this.map.SetupPhase(free);

			CatanColor color = this.getPlayerColorByIndex(playerIndex);
			if (!this.map.CanPlaceSettlement(p, color))
				return false;

			if (free && !this.gameState.IsSetup())
				return false;

			this.BuildSettlement(playerIndex, p, free);
		}
		catch (ModelException e)
		{
			Log.GetLog().throwing("ServerGameManager", "ServerBuildSettlement", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
<<<<<<< HEAD
	 * 
	 * @param playerIndexOffering
	 * @param playerIndexReceiving
=======
	 *
	 * @param playerID
	 * @param playerIndexTo
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	 * @param resourceList
	 * @return
	 */
	public boolean ServerOfferTrade(int playerIndexOffering, int playerIndexReceiving, List<Integer> resourceList )
	{
		if (!this.CanPlayerPlay(playerIndexOffering))
			return false;

		if(!this.CanOfferTrade(playerIndexOffering))
			return false;


		//  offer trade
//		try{
			OfferedTrade offer = new OfferedTrade();
			offer.setFromPlayerID(playerIndexOffering);
			offer.setToPlayerID(playerIndexReceiving);
			ResourceType[] resourceTypes = {ResourceType.BRICK, ResourceType.ORE, ResourceType.SHEEP, ResourceType.WHEAT, ResourceType.WOOD};

			//  populate the trade offer
			for(int i = 0; i < resourceList.size(); i++){
				int resource_count = resourceList.get(i);
				if (resource_count != 0){
					if(resource_count > 0){
						offer.setOfferedResourceAmount(resourceTypes[i], resource_count);
					}else{
						offer.setWantedResourceAmount(resourceTypes[i], -1 * resource_count);
					}
				}
			}
			this.setTradeOffer(offer);



//		}catch (ModelException e){
//			Log.GetLog().throwing("ServerGameManager", "ServerOfferTrade", e);
//			e.printStackTrace();
//			return false;
//		}

		this.updateVersion();
		return true;
	}

	/**
<<<<<<< HEAD
	 * 
	 * @param playerIndex
=======
	 *
	 * @param playerID
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	 * @param willAccept
	 * @return
	 */
	public boolean ServerAcceptTrade(int playerIndex, boolean willAccept)
	{
		if(!this.canAcceptTrade(playerIndex))
			return false;

		//  if the player rejects the trade remove the trade offer, no exchange necessary so return
		if(!willAccept) {
			this.removeTradeOffer();
			this.updateVersion();
			return true;
		}

		//  accept trade
		try{
			OfferedTrade offer = this.offeredTrade;
			ResourceType[] resourceTypes = {ResourceType.BRICK, ResourceType.ORE, ResourceType.SHEEP, ResourceType.WHEAT, ResourceType.WOOD};

			//  exchange resources
			int playerIndexSendingOffer = offer.getFromPlayerID();
			int playerIndexReceivingOffer = offer.getToPlayerID();

			Player pSending = players.get(playerIndexSendingOffer);
			Player pReceiving = players.get(playerIndexReceivingOffer);
			Bank bReceiving = pReceiving.playerBank;
			Bank bSending = pSending.playerBank;

			//  take all resources from player who sent the trade and give them to the receiving player
			for(ResourceType resource : resourceTypes){
				int resource_amount = offer.getOfferedResourceAmount(resource);
				if(resource_amount > 0){
					bSending.getResource(resource, resource_amount);
					bReceiving.giveResource(resource, resource_amount);
				}
			}

			//  take all resources from the player who received the offer and give them to the player who sent the original offer
			for(ResourceType resource : resourceTypes){
				int resource_amount = offer.getWantedResourceAmount(resource);
				if(resource_amount > 0){
					bReceiving.getResource(resource, resource_amount);
					bSending.giveResource(resource, resource_amount);
				}
			}

			this.removeTradeOffer();



		}catch (ModelException e){
			Log.GetLog().throwing("ServerGameManager", "ServerAcceptTrade", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
<<<<<<< HEAD
	 * 
	 * @param playerIndex
	 * @param ratio
	 * @param input
	 * @param output
=======
	 *
	 * @param playerID
	 * @param in
	 * @param out
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	 * @return
	 */
	public boolean ServerMaritimeTrading(int playerIndex, int ratio, ResourceType input, ResourceType output)
	{
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if(!this.CanMaritimeTrade(playerIndex))
			return false;



		Player pGiver = players.get(playerIndex);
		Bank bGame = this.gameBank;
		Bank bPlayer = pGiver.playerBank;

		//  exchange resources at ratio rate between player and the bank
		try{
			bPlayer.getResource(input, ratio);
			bGame.getResource(output, 1);

			bPlayer.giveResource(output, 1);
			bGame.giveResource(input, ratio);

		}catch (ModelException e){
			Log.GetLog().throwing("ServerGameManager", "ServerMaritimeTrading", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
<<<<<<< HEAD
	 * 
	 * @param playerIndex
=======
	 *
	 * @param playerID
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	 * @param resourceList
	 * @return
	 */
	public boolean ServerDiscardCards(int playerIndex, List<Integer> resourceList)
	{

		if(!this.CanDiscardCards(playerIndex, ResourceType.BRICK, resourceList.get(0)) ||
				!this.CanDiscardCards(playerIndex, ResourceType.ORE, resourceList.get(1)) ||
				!this.CanDiscardCards(playerIndex, ResourceType.SHEEP, resourceList.get(2)) ||
				!this.CanDiscardCards(playerIndex, ResourceType.WHEAT, resourceList.get(3)) ||
				!this.CanDiscardCards(playerIndex, ResourceType.WOOD, resourceList.get(4)))
			return false;

		Player pGiver = players.get(playerIndex);
		Bank bReceiver = this.gameBank;
		Bank bGiver = pGiver.playerBank;

		//  take the specified resource from the player at playerIndex
		try{
			bGiver.getResource(ResourceType.BRICK, resourceList.get(0));
			bGiver.getResource(ResourceType.ORE, resourceList.get(1));
			bGiver.getResource(ResourceType.SHEEP, resourceList.get(2));
			bGiver.getResource(ResourceType.WHEAT, resourceList.get(3));
			bGiver.getResource(ResourceType.WOOD, resourceList.get(4));

		}catch (ModelException e){
			Log.GetLog().throwing("ServerGameManager", "ServerDiscardCards-GettingResources", e);
			e.printStackTrace();
			return false;
		}

		//give the resource to the game bank
		try
		{
			bReceiver.giveResource(ResourceType.BRICK, resourceList.get(0));
			bReceiver.giveResource(ResourceType.ORE, resourceList.get(1));
			bReceiver.giveResource(ResourceType.SHEEP, resourceList.get(2));
			bReceiver.giveResource(ResourceType.WHEAT, resourceList.get(3));
			bReceiver.giveResource(ResourceType.WOOD, resourceList.get(4));
		}
		catch(ModelException e)
		{
			Log.GetLog().throwing("ServerGameManager", "ServerDiscardCards-GivingingResourcesToGameBank", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

<<<<<<< HEAD
	/**
	 *
	 * @param receiver
	 * @param giver
     * @return
     */
=======
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11
	private ResourceType takeRandomResourceCard(int receiver, int giver)
	{
		if (giver == -1)
			return null;

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
		gm.gameID = this.gameID;
		gm.gameState = this.gameState;
		gm.log = this.log;
		gm.mapModel = this.map;
		gm.players = this.players;
		gm.version = this.version;
		gm.waterCooler = this.waterCooler;
		gm.victoryPointManager = this.victoryPointManager;
<<<<<<< HEAD
		gm.trade =  this.offeredTrade;

=======
>>>>>>> b7c6f001544ab2507cd87165b6316f6d056d3e11

		return gm;
	}


}

package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import server.Log;
import server.ai.AIHandler;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.GameActionLog;
import shared.model.GameManager;
import shared.model.GameModel;
import shared.model.GameState;
import shared.model.ModelException;
import shared.model.OfferedTrade;
import shared.model.Player;
import shared.model.VictoryPointManager;
import shared.model.chat.ChatBox;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.model.MapGenerator;
import shared.model.map.objects.Hex;

/**
 * Special formation of the game manager
 * @author matthewcarlson
 *
 */
public class RealServerGameManager extends ServerGameManager implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1293281;
	private	boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;

	private Map<Integer,Integer> playerIndexLookup;

	private List<Boolean> discardList;

	public RealServerGameManager(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts, int index)
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
	 * Initializes the discard list. One entry is allocated per player in the game.
	 * If shouldBeBlank is true, all of the entries in the list are set to false.
	 * If shouldBeBlank is false, each entry is set to true if the corresponding player
	 * should discard, or false if the player does not need to discard (ie, they have
	 * 7 or less cards)
	 * @param shouldBeBlank whether to set all values to false
	 */
	protected void initDiscard(boolean shouldBeBlank)
	{
		discardList = new ArrayList<Boolean>();
		for(int i = 0; i < players.size(); i++)
		{
			Player tempPlayer = players.get(i);
			if(!shouldBeBlank && tempPlayer.playerBank.getResourceCount() > 7)
			{
				discardList.add(true);
			}
			else
			{
				discardList.add(false);
			}
		}
	}

	/**
	 * Updates the version when doing an action
	 */
	protected void updateVersion()
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
	public void reset()
	{
		this.map = MapGenerator.GenerateMap(randomTiles, randomNumbers, randomPorts);
		version = -1;
		waterCooler = new ChatBox();
		log = new GameActionLog();
		gameBank = new Bank();
		gameState = new GameState();
		map = MapGenerator.BeginnerMap();
		victoryPointManager = new VictoryPointManager();
		offeredTrade = null;
		playerColors = new int[10];
		//fill the array with -1 by default
		Arrays.fill(playerColors,-1);
		playerCanMoveRobber = -1;
		gameBank.resetToBankDefaults();

		for (Player p: players)
		{
			p.playerBank.resetToPlayerDefaults();
		}
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
	@Override
	public boolean ServerSendChat(int playerID, String message)
	{
		int playerIndex = GetPlayerIndexByID(playerID);
		if (super.canChat(playerIndex))
		{
			super.PlayerChat(playerIndex, message);
			ServerChatCommand(playerIndex,message);
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
	@Override
	public boolean ServerRollNumber(int playerIndex, int number)
	{
		Log.GetLog().log(Level.INFO, "Player " + playerIndex + " rolled " +number);
		if (!super.CanRollNumber(playerIndex))
			return false;

		try
		{
			super.DiceRoll(number);

			//initialize the serverside discard list
			if(number == 7 && this.NeedToDiscardAfterRoll())
			{
				this.initDiscard(false);

				//have the AI discard cards
				for(int i = 0; i < players.size(); i++)
				{
					Player p = players.get(i);
					if (p.isARobot() && this.discardList.get(i))
						AIHandler.GetHandler().Discard(p.playerID(), gameID);
				}
			}

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
	@Override
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

		if (!gameState.stopRobbing())
			return false;

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
		{
			this.LogAction(playerIndex, this.getCurrentPlayerName()+" took a "+takenResource+" from "+this.getPlayerNameByIndex(victimIndex));
			Log.GetLog().log(Level.INFO, "Game " + this.gameID + ": Player " + playerIndex + " took a "
				+ takenResource.toString() + " from Player " + victimIndex);
		}
		else
			Log.GetLog().log(Level.INFO, "Game " + this.gameID + ": Player " + playerIndex + " tried to take"
				+ " a card from Player " + victimIndex);

		return true;
	}

	/**
	 * Ends a player's turn
	 * @param playerID
	 * @return
	 */
	@Override
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
				player.playerBank.newToOldDevs();
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
	 * @param index the player index
	 * @return
	 */
	private boolean IsPlayerRobot(int index)
	{
		try
		{
			return GetPlayer(index).isARobot();
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Buys a dev card
	 * @param playerID
	 * @return
	 */
	@Override
	public boolean ServerBuyDevCard(int playerID)
	{
		int playerIndex = this.GetPlayerIndexByID(playerID);
		if (super.CurrentPlayersTurn() != playerIndex)
			return false;

		if (!super.CanBuyDevCard(playerIndex))
			return false;

		//Buy the dev card
		try
		{
			super.BuyDevCard(playerIndex);
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


	private void ServerChatCommand(int playerIndex, String message)
	{
		message = message.toLowerCase();
		try
		{
			switch(message)
			{
				case "give me dev card":
					DevCardType devcard = gameBank.getDevCard();

					GetPlayer(playerIndex).playerBank.giveNewDevCard(devcard);

					victoryPointManager.playerGotDevCard(playerIndex, devcard);
					log.logAction(playerIndex, getPlayerNameByIndex(playerIndex)+" stole a "+devcard+" card");

					break;
				case "player banks":
					for(Player p: players)
					{
						super.PlayerChat(p.playerIndex(), p.playerBank.resourcesToString());
					}
					break;
				case "pay dayz":
					Bank pBank = GetPlayer(playerIndex).playerBank;
					pBank.giveResource(ResourceType.BRICK,1);
					pBank.giveResource(ResourceType.WOOD,1);
					pBank.giveResource(ResourceType.ORE,1);
					pBank.giveResource(ResourceType.WHEAT,1);
					pBank.giveResource(ResourceType.SHEEP,1);
					break;
					
			}
		}
		catch (ModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param playerIndex
	 * @param res1
	 * @param res2
	 * @return
	 */
	@Override
	public boolean ServerYearOfPlenty(int playerIndex, ResourceType res1, ResourceType res2)
	{
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if (!this.CanPlayDevCard(playerIndex, DevCardType.YEAR_OF_PLENTY))
			return false;

		try
		{
			//take resources from bank
			if (res1 == res2 && this.gameBank.getResourceCount(res1) > 1)
			{
				this.gameBank.getResource(res1);
				this.gameBank.getResource(res2);
			}
			else if (this.gameBank.getResourceCount(res1) > 0 && this.gameBank.getResourceCount(res2) > 0)
			{
				this.gameBank.getResource(res1);
				this.gameBank.getResource(res2);
			}
			else
			{
				return false;
			}

			//give resources to player
			players.get(playerIndex).playerBank.giveResource(res1);
			players.get(playerIndex).playerBank.giveResource(res2);

			//remove dev card from player
			this.playDevCard(playerIndex, DevCardType.YEAR_OF_PLENTY);
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
	 * @param playerIndex
	 * @param res1
	 * @return
	 */
	@Override
	public boolean ServerMonopoly(int playerIndex, ResourceType res1)
	{
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if (!this.CanPlayDevCard(playerIndex, DevCardType.MONOPOLY))
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
					if(tempCt > 0)
						players.get(i).playerBank.getResource(res1, tempCt);
				}
			}

			//give cards taken to current player
			players.get(playerIndex).playerBank.giveResource(res1, totalResourceCount);

			//remove dev card from player
			this.playDevCard(playerIndex, DevCardType.MONOPOLY);
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
	 * @param playerIndex
	 * @return
	 */
	@Override
	public boolean ServerMonument(int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex))
			return false;

		if (!this.CanPlayDevCard(playerIndex, DevCardType.MONUMENT))
			return false;

		try
		{
			//give victory point to player
			this.victoryPointManager.playedMonument(playerIndex);

			//remove dev card from player
			this.playDevCard(playerIndex, DevCardType.MONUMENT);
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
	 * @param playerIndex
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	@Override
	public boolean ServerRoadBuilding(int playerIndex, Coordinate start1, Coordinate end1,  Coordinate start2, Coordinate end2)
	{

		if (!this.CanPlayDevCard(playerIndex, DevCardType.ROAD_BUILD))
			return false;

		CatanColor color = this.getPlayerColorByIndex(playerIndex);

		//Log.GetLog().finest("Playing the roadbuilder card on color ");

		if (!this.map.CanPlaceRoad(start1, end1, color) && !this.map.CanPlaceRoad(start2, end2, color))
			return false;

		try
		{
			//Log.GetLog().finest("Playing the roadbuilder card");
			//remove dev card from player
			this.playDevCard(playerIndex, DevCardType.ROAD_BUILD);

			//build the roads
			if (this.map.CanPlaceRoad(start1, end1, color))
			{
				this.BuildRoad(playerIndex, start1, end1, true);
			}
			if (this.map.CanPlaceRoad(start2, end2, color))
			{
				this.BuildRoad(playerIndex, start2, end2, true);
			}
			if (this.map.CanPlaceRoad(start1, end1, color))
			{
				this.BuildRoad(playerIndex, start1, end1, true);
			}
			this.victoryPointManager.playerBuiltRoad(playerIndex);
			this.victoryPointManager.playerBuiltRoad(playerIndex);


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
	@Override
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
			try
			{
				players.get(playerIndex).playerBank.getDevCard(DevCardType.SOLDIER);

				Player pPlayer = players.get(playerIndex);
				Bank bPlayer = pPlayer.playerBank;
				bPlayer.recruitSolider();
				//Log.GetLog().finest("Adding to solider count! current count "+bPlayer.getNumberSolidersRecruited());
				int armySize = pPlayer.incrementArmySize();
				this.victoryPointManager.checkPlayerArmySize(playerIndex, armySize);

			}
			catch(ModelException e)
			{
				e.printStackTrace();
				return false;
			}
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
	@Override
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

			if (this.map.LongestRoadExists())
			{
				CatanColor longestColor = this.map.GetLongestRoadColor();
				int longestIndex = this.getPlayerIndexByColor(longestColor);
				this.victoryPointManager.setPlayerToHaveLongestRoad(longestIndex);
			}

		}
		catch (ModelException | MapException e)
		{
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
	 *
	 * @param playerIndex
	 * @param p
	 * @return
	 */
	@Override
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
	 *
	 * @param playerIndex
	 * @param p
	 * @return
	 */
	@Override
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

			if (this.map.LongestRoadExists())
			{
				CatanColor longestColor = this.map.GetLongestRoadColor();
				int longestIndex = this.getPlayerIndexByColor(longestColor);
				this.victoryPointManager.setPlayerToHaveLongestRoad(longestIndex);
			}

			//give them the resources
			if (this.gameState.state == GameRound.SECONDROUND)
			{
				Iterator<HexType> hexTypes = map.GetResources(p);


				while (hexTypes.hasNext())
				{
					HexType hexType = hexTypes.next();

					ResourceType rt = ResourceType.fromHex(hexType);
					if (rt != null)
					{
						gameBank.getResource(rt);
						GetPlayer(playerIndex).playerBank.giveResource(rt);
					}
				}
			}
		}
		catch (ModelException | MapException e)
		{
			Log.GetLog().throwing("ServerGameManager", "ServerBuildSettlement", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/**
	 *
	 * @param playerIndexOffering
	 * @param playerIndexReceiving
	 * @param resourceList
	 * @return
	 */
	@Override
	public boolean ServerOfferTrade(int playerIndexOffering, int playerIndexReceiving, List<Integer> resourceList )
	{
		if (!this.CanPlayerPlay(playerIndexOffering))
			return false;

		if(!this.CanOfferTrade(playerIndexOffering))
			return false;

		OfferedTrade offer = new OfferedTrade();
		offer.setFromPlayerID(playerIndexOffering);
		offer.setToPlayerID(playerIndexReceiving);
		ResourceType[] resourceTypes = {ResourceType.BRICK, ResourceType.ORE, ResourceType.SHEEP, ResourceType.WHEAT, ResourceType.WOOD};

		//  populate the trade offer
		for(int i = 0; i < resourceList.size(); i++)
		{
			int resource_count = resourceList.get(i);
			if (resource_count != 0)
			{
				if(resource_count < 0)
				{
					offer.setOfferedResourceAmount(resourceTypes[i], -1 * resource_count);
				}
				else
				{
					offer.setWantedResourceAmount(resourceTypes[i], resource_count);
				}
			}
		}
		this.setTradeOffer(offer);
		System.out.println("Reached Offer1");

		if (this.IsPlayerRobot(playerIndexReceiving))
		{
			int aiID = this.GetPlayerIDbyIndex(playerIndexReceiving);
			AIHandler.GetHandler().Trade(aiID, this.gameID, offer);
		}

		this.updateVersion();
		return true;
	}

	/**
	 *
	 * @param playerIndex
	 * @param willAccept
	 * @return
	 */
	@Override
	public boolean ServerAcceptTrade(int playerIndex, boolean willAccept)
	{
		OfferedTrade offer = this.offeredTrade;

		//THIS SAYS ID but it's actually an index
		//TODO Chris sorts this out?
		if (offer.getToPlayerID() != playerIndex)
		{
			Log.GetLog().finest("Player with index: "+playerIndex+" and ID:"+this.GetPlayerIDbyIndex(playerIndex)+" cannot accept offer for playerID:"+offer.getToPlayerID());
			return false;
		}
		//  if the player rejects the trade remove the trade offer, no exchange necessary so return
		if(!willAccept)
		{
			this.LogAction(playerIndex, this.getPlayerNameByIndex(playerIndex) + " turned down offer from "+this.getCurrentPlayerName());
			this.removeTradeOffer();
			this.updateVersion();
			return true;
		}

		//  accept trade
		try
		{
			ResourceType[] resourceTypes = {ResourceType.BRICK, ResourceType.ORE, ResourceType.SHEEP, ResourceType.WHEAT, ResourceType.WOOD};

			//  exchange resources
			int playerIndexSendingOffer = offer.getFromPlayerID();
			int playerIndexReceivingOffer = offer.getToPlayerID();

			Player pSending = players.get(playerIndexSendingOffer);
			Player pReceiving = players.get(playerIndexReceivingOffer);
			Bank bReceiving = pReceiving.playerBank;
			Bank bSending = pSending.playerBank;

			//  take all resources from player who sent the trade and give them to the receiving player
			for(ResourceType resource : resourceTypes)
			{
				int resource_amount = offer.getOfferedResourceAmount(resource);
				if(resource_amount > 0)
				{
					bSending.giveResource(resource, resource_amount);
					bReceiving.getResource(resource, resource_amount);
				}
			}

			//  take all resources from the player who received the offer and give them to the player who sent the original offer
			for(ResourceType resource : resourceTypes)
			{
				int resource_amount = offer.getWantedResourceAmount(resource);
				if(resource_amount > 0)
				{
					bReceiving.giveResource(resource, resource_amount);
					bSending.getResource(resource, resource_amount);
				}
			}

			this.removeTradeOffer();
			this.LogAction(playerIndex, this.getPlayerNameByIndex(playerIndex) + " accepted an offer from "+this.getCurrentPlayerName());


		}
		catch (ModelException e)
		{
			Log.GetLog().throwing("ServerGameManager", "ServerAcceptTrade", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		return true;
	}

	/***
	 * @param playerIndex
	 * @param ratio
	 * @param input
	 * @param output
	 * @return
	 */
	@Override
	public boolean ServerMaritimeTrading(int playerIndex, int ratio, ResourceType input, ResourceType output)
	{
		if (!this.CanPlayerPlay(playerIndex)) {
			System.out.println("entered 1");
			return true;
		}

		Player pGiver = players.get(playerIndex);
		Bank bGame = this.gameBank;
		Bank bPlayer = pGiver.playerBank;

		//  exchange resources at ratio rate between player and the bank
		try
		{
			bPlayer.getResource(input, ratio);
			bGame.getResource(output, 1);

			bPlayer.giveResource(output, 1);
			bGame.giveResource(input, ratio);

		}
		catch (ModelException e)
		{
			System.out.println("entered 3");

			Log.GetLog().throwing("ServerGameManager", "ServerMaritimeTrading", e);
			e.printStackTrace();
			return false;
		}

		this.updateVersion();
		System.out.println("entered 4");

		return true;
	}

	/**
	 *
	 * @param playerIndex
	 * @param resourceList
	 * @return
	 */
	@Override
	public boolean ServerDiscardCards(int playerIndex, List<Integer> resourceList)
	{

//		if(!this.CanDiscardCards(playerIndex, ResourceType.BRICK, resourceList.get(0)) ||
//				!this.CanDiscardCards(playerIndex, ResourceType.ORE, resourceList.get(1)) ||
//				!this.CanDiscardCards(playerIndex, ResourceType.SHEEP, resourceList.get(2)) ||
//				!this.CanDiscardCards(playerIndex, ResourceType.WHEAT, resourceList.get(3)) ||
//				!this.CanDiscardCards(playerIndex, ResourceType.WOOD, resourceList.get(4)))
//			return false;

		Player pGiver = players.get(playerIndex);
		Bank bReceiver = this.gameBank;
		Bank bGiver = pGiver.playerBank;

		//  take the specified resource from the player at playerIndex
		try{
			if(resourceList.get(0) > 0)
			{
				bGiver.getResource(ResourceType.BRICK, resourceList.get(0));
				discardList.set(playerIndex, false);
			}
			if(resourceList.get(1) > 0)
			{
				bGiver.getResource(ResourceType.ORE, resourceList.get(1));
				discardList.set(playerIndex, false);
			}
			if(resourceList.get(2) > 0)
			{
				bGiver.getResource(ResourceType.SHEEP, resourceList.get(2));
				discardList.set(playerIndex, false);
			}
			if(resourceList.get(3) > 0)
			{
				bGiver.getResource(ResourceType.WHEAT, resourceList.get(3));
				discardList.set(playerIndex, false);
			}
			if(resourceList.get(4) > 0)
			{
				bGiver.getResource(ResourceType.WOOD, resourceList.get(4));
				discardList.set(playerIndex, false);
			}

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


		boolean stillNeedToDiscard = false;
		for(int i = 0; i < players.size(); i++){
			if(discardList.get(i))
			{
				stillNeedToDiscard = true;
			}
		}

		if(!stillNeedToDiscard)
			this.gameState.state = GameRound.ROBBING;

		this.updateVersion();
		return true;
	}

	/**
	 *
	 * @param receiver
	 * @param giver
     * @return
     */
	protected ResourceType takeRandomResourceCard(int receiver, int giver)
	{
		if (giver == -1)
			return null;

		Player pReceiver = players.get(receiver);
		Player pGiver = players.get(giver);
		Bank bReceiver = pReceiver.playerBank;
		Bank bGiver = pGiver.playerBank;

		ResourceType rGiven = null;
		try
		{
			rGiven = bGiver.takeRandomResource();
		}
		catch(ModelException e)
		{
			e.printStackTrace();
		}
		//if the giver can't give a resource, return null
		if(rGiven == null)
		{
			return null;
		}

		//give the resource to the robbing player
		try
		{
			bReceiver.giveResource(rGiven);
		}
		catch(ModelException e)
		{
			e.printStackTrace();
			return null;
		}

		return rGiven;
	}

	/**
	 * Gets the current game model
	 * @return
	 */
	@Override
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
		gm.trade =  this.offeredTrade;


		return gm;
	}


}

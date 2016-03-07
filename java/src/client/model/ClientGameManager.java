package client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import client.data.ClientDataTranslator;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.map.RobView;
import client.networking.RealServerProxy;
import client.networking.ServerProxy;
import client.networking.ServerProxyException;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ModelNotification;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.definitions.TurnState;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.Player;
import shared.model.Translate;
import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import shared.model.map.*;
import shared.model.map.model.IMapModel;
import shared.model.map.model.MapModel;
import shared.model.map.model.UnmodifiableMapModel;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetResourceList;
import shared.networking.transport.NetTradeOffer;

public class ClientGameManager extends GameManager
{
	private ServerProxy proxy;
	private int myPlayerIndex = -1;
	private TurnState turnState;
	private int playerIndexWithTradeOffer = -2;
	private int playerIndexSendingOffer = -2;
	private int[] resourceToTrade;

	private int refreshCount = 0;
	private CatanColor myPlayerColor;
	
	private PieceType lastSelectedPiece = null;
	private EdgeLocation lastRoadBuilt  = null;
	/**
	 * Creates the client game manager with the proxy
	 * @param clientProxy
	 */
	public ClientGameManager(ServerProxy clientProxy)
	{
		super();
		this.proxy = clientProxy;
		turnState = TurnState.WAITING_FOR_PLAYERS;
	}

	/**
	 * Gets a client proxy
	 * @param clientProxy
	 * @param myPlayerID
	 */
	public ClientGameManager(RealServerProxy clientProxy, int myPlayerIndex)
	{
		this(clientProxy);
		this.myPlayerIndex = myPlayerIndex;
	}


	/**
	 * Get the Index of the current player client
	 * @return
	 */
	public int myPlayerIndex()
	{
		return this.myPlayerIndex;
	}


	/**
	 * The current player's color
	 * @return the current player color
	 */
	public CatanColor myPlayerColor()
	{
		return this.myPlayerColor;
	}
	
	public PieceType myPlayerLastPiece()
	{
		return this.lastSelectedPiece;
	}

	/**
	 * Gets the number of resources for the player
	 * @param type
	 * @return
	 */
	public int playerResourceCount(ResourceType type)
	{
		return this.players.get(this.myPlayerIndex).playerBank.getResourceCount(type);
	}

	/**
	 * Gets the number of devCards for the current player
	 * @param type
	 * @return
	 */
	public int playerDevCardCount(DevCardType type)
	{
		return this.players.get(this.myPlayerIndex).playerBank.getDevCardCount(type);
	}
	
	
	//TEMP DEV CARD STUFF  ===========================================
	//This is needed to get the view right without allowing these
	//cards to be played.
	public int playerNewDevCardCount(DevCardType type)
	{
		return this.players.get(this.myPlayerIndex).playerBank.getNewDevCardCount(type);
	}
	//TEMP DEV CARD STUFF  ===========================================
	
	
	/**
	 * Gets the number of devCards for the current player
	 * @param type
	 * @return
	 */
	public int playerPieceCount(PieceType type)
	{
		try 
		{
			return this.players.get(this.myPlayerIndex).playerBank.getPieceCount(type);
		} 
		catch (ModelException e) 
		{
			e.printStackTrace();
			return 0;
		}
	}
	

	/**
	 *
	 * @return
	 */
	public PlayerInfo[] allCurrentPlayers()
	{
		PlayerInfo[] allplayers = new PlayerInfo[this.players.size()];
		for (int i=0; i< this.players.size(); i++)
		{
			allplayers[i] = ClientDataTranslator.convertPlayerInfo(players.get(i));
		}

		return allplayers;
	}

	/**
	 *
	 * @param playerIndex
	 */
	public String getPlayerNameByIndex(int playerIndex)
	{
		if(playerIndex > 3 || playerIndex < 0)
			return null;

		return players.get(playerIndex).name;
	}


	/**
	 * 
	 * @param index
	 */
	public void setPlayerIndexWithTradeOffer(int index){
		playerIndexWithTradeOffer = index;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPlayerIndexWithTradeOffer(){
		return playerIndexWithTradeOffer;
	}
	
	/**
	 * 
	 * @param index
	 */
	public void setPlayerSendingOfferIndex(int index){
		playerIndexSendingOffer = index;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPlayerSendingOfferIndex(){
		return playerIndexSendingOffer;
	}
	
	/**
	 * Returns the resources of the offered trade
	 * @return
	 */
	public int[] getResourceToTrade(){
		return this.resourceToTrade;
	}

	/**
	 * Joins a game
	 * @param gameID
	 * @param color
	 * @return
	 */
	public boolean joinGame(GameInfo game, CatanColor color)
	{
		try
		{
			proxy.joinGame(game.getId(), color);
			this.gameID = game.getId();
			this.gameTitle = game.getTitle();
			this.myPlayerIndex = -1;
			this.myPlayerColor = color;
			List<Player> newplayers = new ArrayList<>();
			Iterator<PlayerInfo> iter = game.getPlayers().iterator();
			while(iter.hasNext())
			{
				PlayerInfo newplay = iter.next();
				System.out.println("YOU:    "+proxy.getUserId()+" Player: "+newplay.getName()+":"+newplay.getId()+" at index:"+newplay.getPlayerIndex());
				if (newplay.getId() == proxy.getUserId())
				{
					System.out.println("Joined with player index:"+newplay.getPlayerIndex());
					this.myPlayerIndex = newplay.getPlayerIndex();
					
				}
				Player play = ClientDataTranslator.convertPlayerInfo(newplay);
				newplayers.add(play);

			}
			
			//set the players correctly
			this.SetPlayers(newplayers);
			
			System.out.println("Joiing a game with playerIndex:"+this.myPlayerIndex);
			ClientGame.startPolling();
			//If we can't joining a game then an exception will be thrown

		} 
		catch (ServerProxyException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Joins a game
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @param name
	 * @return
	 */
	public NetGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		try
		{
			NetGame game = proxy.createGame(randomTiles, randomNumbers, randomPorts, name);
			this.LoadGame(game);
			return game;
		}
		catch (ServerProxyException e)
		{
			e.printStackTrace();
			
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			
		}

		return null;
	}
	/**
	 * Notifies the server after rolling the dice
	 */
	@Override
	public int RollDice()
	{
		int roll = 0;
		try
		{
			roll = super.RollDice();
			NetGameModel model = proxy.rollNumber(roll);
			this.reloadGame(model,true);
		}
		catch (ServerProxyException|ModelException e)
		{
			e.printStackTrace();
		}
		return roll;
	}
	
	/**
	 * Sets the turn state and calls the notification center
	 * @param newstate
	 */
	private void setTurnState(TurnState newstate){
		this.turnState = newstate;
		this.notifyCenter.notify(ModelNotification.STATE);
	}
	
	/**
	 * Builds a road for the current player
	 * @param start
	 * @param end
	 */
	public void BuildRoad(Coordinate start, Coordinate end)
	{
		try
		{
			boolean free = false;
			
			//TODOD This logic will need added to
			if (turnState == TurnState.FIRST_ROUND_MY_TURN || turnState == TurnState.SECOND_ROUND_MY_TURN)
				free = true;
			else if (turnState == TurnState.ROAD_BUILDER_SECOND || turnState == TurnState.ROAD_BUILDER)
				free = true;
			
			this.BuildRoad(myPlayerIndex, start, end, free);

			EdgeLocation location = Translate.GetEdgeLocation(start, end);
			System.out.println("Building a road in state "+this.turnState);
			if (turnState == TurnState.ROAD_BUILDER_SECOND)
			{
				proxy.roadBuildingCard(lastRoadBuilt, location);
				this.setTurnState(TurnState.PLAYING);
			}
			else if (turnState != TurnState.ROAD_BUILDER)
			{
				proxy.buildRoad(location, free);
			}
			else{
				this.setTurnState(TurnState.ROAD_BUILDER_SECOND);
			}
			lastRoadBuilt = location;

		}
		catch (ModelException e)
		{
			e.printStackTrace();
		}
		catch (ServerProxyException e)
		{
			// TODO I don't know how this should be handled, but probably shouldn't be thrown.
			e.printStackTrace();
		}
	}

	/**
	 * Builds a settlement
	 * @param point
	 */
	public void BuildSettlement(Coordinate point)
	{
		try
		{
			boolean free = false;
			
			if (turnState == TurnState.FIRST_ROUND_MY_TURN || turnState == TurnState.SECOND_ROUND_MY_TURN)
				free = true;
			
			this.BuildSettlement(myPlayerIndex, point, free);

			VertexLocation location = Translate.GetVertexLocation(point);

			NetGameModel newmodel = proxy.buildSettlement(location, free);
			this.reloadGame(newmodel,true);
		}
		catch (ModelException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (ServerProxyException e)
		{
			//TODO How should this be handled?
			e.printStackTrace();
		}
	}
	
	/**
	 * Buys a dev card for the current player
	 */
	public boolean BuyDevCard()
	{
		try
		{
			NetGameModel newmodel = proxy.buyDevCard();
			this.reloadGame(newmodel,true);
			return true;
		} 
		catch (ServerProxyException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Plays the monopoly card for the current player
	 * @param resource
	 */
	public boolean PlayMonopoly(ResourceType resource)
	{
		if (!super.CanPlayDevCard(this.myPlayerIndex, DevCardType.MONOPOLY))
			return false;
		try 
		{
			this.playDevCard(this.myPlayerIndex, DevCardType.MONOPOLY);
			NetGameModel model = proxy.monopolyCard(resource);
			this.reloadGame(model, true);
			return true;
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Plays the monopoly card for the current player
	 * @param resource
	 */
	public boolean PlaySolider()
	{
		if (!super.CanPlayDevCard(this.myPlayerIndex, DevCardType.SOLDIER))
			return false;
		System.out.println("Playing Solider Card");
		this.setTurnState(TurnState.SOLIDER_CARD);
		return true;
	}
	
	/**
	 * Plays the monument card for the current player
	 * @param resource
	 */
	public boolean PlayMonument()
	{
		if (!super.CanPlayDevCard(this.myPlayerIndex, DevCardType.MONUMENT))
			return false;
		try 
		{
			NetGameModel model = proxy.monumentCard();
			this.reloadGame(model, true);
			return true;
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Plays the road builder card for the current player
	 * @param resource
	 */
	public boolean PlayRoadBuilder()
	{
		if (!super.CanPlayDevCard(this.myPlayerIndex, DevCardType.ROAD_BUILD))
			return false;
	
		this.turnState = TurnState.ROAD_BUILDER;
		this.notifyCenter.notify(ModelNotification.STATE);
		return true;
		/*try 
		{
			
			//TODO implement
			//NetGameModel model = proxy.roadBuildingCard(location1, location2)
			//this.reloadGame(model, true);
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	

	/**
	 * Plays the road builder card for the current player
	 * @param resource
	 */
	public boolean PlayYearOfPlenty(ResourceType resource1, ResourceType resource2)
	{
		if (!super.CanPlayDevCard(this.myPlayerIndex, DevCardType.YEAR_OF_PLENTY))
			return false;
		try 
		{
			NetGameModel model = proxy.yearOfPlentyCard(resource1, resource2);
			this.reloadGame(model, true);
			return true;
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	/**
	 * Builds a city for the current player
	 * @param point
	 */
	public void BuildCity(Coordinate point)
	{
		try
		{
			//super.BuildCity(myPlayerIndex, point);

			VertexLocation location = Translate.GetVertexLocation(point);

			NetGameModel newmodel = proxy.buildCity(location);
			this.reloadGame(newmodel,true);
		}
		catch (ModelException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (ServerProxyException e)
		{
			//TODO How should this be handled?
			e.printStackTrace();
		}
	}
	
	public void PlaceRobber(Coordinate point)
	{
		try
		{
			map.PlaceRobber(point);
			
			Iterator<CatanColor> colors = map.GetOccupiedVertices(point);
			PlayerInfo[] infoArray = allCurrentPlayers();
			
			List<PlayerInfo> toRob = new ArrayList<PlayerInfo>(3);
			List<Player> playerInfo = new ArrayList<Player>(3);
			while (colors.hasNext())
			{
				CatanColor color = colors.next();
				int index = getPlayerIndexByColor(color);
				
				if (infoArray[index].getPlayerIndex() != myPlayerIndex)
				{
					toRob.add(infoArray[index]);
					playerInfo.add(players.get(index));
				}
			}
			
			RobPlayerInfo[] robArray = new RobPlayerInfo[toRob.size()];
			for (int i = 0; i < toRob.size(); i++)
			{
				PlayerInfo player = toRob.get(i);
				
				robArray[i] = new RobPlayerInfo(player);
				robArray[i].setNumCards(playerInfo.get(i).totalResources());
			}
			
			RobView view = new RobView();
			view.setPlayers(robArray);
			view.showModal();
		} 
		catch (MapException e) 
		{
			//This shouldn't occur. The map should verify it.
			e.printStackTrace();
		}
	}
	
	public void RobVictim(int victimIndex)
	{
		if (this.turnState == TurnState.SOLIDER_CARD)
		{
			//Play a solider card
			try{
				turnState = TurnState.PLAYING;
				notifyCenter.notify(ModelNotification.STATE);
				HexLocation location = Translate.GetHexLocation(map.GetRobberLocation().getPoint());
				NetGameModel newmodel = this.proxy.soldierCard(victimIndex, location);
				this.reloadGame(newmodel,true);
			}
			catch (ServerProxyException | ModelException e) 
			{
				e.printStackTrace();
			} 
		}
		else if (super.CanPlaceRobber(this.myPlayerIndex))
		{
			try 
			{
				super.placeRobber(this.myPlayerIndex);
				
				turnState = TurnState.PLAYING;
				notifyCenter.notify(ModelNotification.STATE);
				
				HexLocation location = Translate.GetHexLocation(map.GetRobberLocation().getPoint());
				NetGameModel newmodel = this.proxy.robPlayer(victimIndex, location);
				this.reloadGame(newmodel,true);
			} 
			catch (ServerProxyException | ModelException e) 
			{
				e.printStackTrace();
			} 
		}
		
	}

	/**
	 * Ends the current player's turn
	 */
	public void endTurn()
	{

		try
		{
			if (!gameState.nextTurn())
				throw new ModelException("Unable to finish turn");
			NetGameModel newmodel = proxy.finishTurn();
			//Our observes will be updated when we reload the game
			this.reloadGame(newmodel,true);

		}
		catch(ServerProxyException | ModelException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Discards the cards specified by the resourceList list for the current player
	 * @param resourceList the list of resources to discard
	 */
	public void DiscardCards(List<Integer> resourceList)
	{
		try
		{
			//Check to make sure we can discard cards
			NetGameModel newmodel = proxy.discardCards(resourceList);
			this.reloadGame(newmodel,true);
		}
		catch(ServerProxyException | ModelException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Sends a chat message for the current player
	 * @param message
	 */
	public void SendChat(String message)
	{
		super.PlayerChat(myPlayerIndex, message);
		try
		{
			NetGameModel newModel = proxy.sendChat(message);
			this.reloadGame(newModel, true);
		}
		catch (ServerProxyException | ModelException e)
		{
			System.err.println("Unable to send chat!");
			e.printStackTrace();
		}

	}
	
	
	
	//CAN DO METHOD
	public boolean canBuildPiece(PieceType p){
		
		switch(p){
			case CITY:
				return super.CanBuildCity(this.myPlayerIndex);
			case NONE:
				break;
			case ROAD:
				return super.CanBuildRoad(this.myPlayerIndex);
			case ROBBER:
				break;
			case SETTLEMENT:
				return super.CanBuildSettlement(this.myPlayerIndex);
			default:
				break;
		}
		return false;
	}
	
	@Override
	public boolean CanPlaceRobber(int playerIndex)
	{	
		
		if (super.CanPlaceRobber(playerIndex))
			return true;
		//Check if we've played a solider card
		if (this.turnState == TurnState.SOLIDER_CARD)
			return true;
		return false;
	}
	
	/**
	 * Starts building a piece
	 * @param road
	 */
	public void startBuilding(PieceType piece) {
		// TODO Auto-generated method stub
		this.lastSelectedPiece = piece;
		this.setTurnState(TurnState.PLACING_PIECE);
	}
	
	
	@Override
	public void StartGame()
	{
		super.StartGame();
		this.notifyCenter.notify(ModelNotification.ALL);
	}

	/**
	 * Gets the points of the current player
	 * @return
	 */
	public int PlayerPoints()
	{
		return this.victoryPointManager.getVictoryPoints(this.myPlayerIndex);
	}

	//--------------------------------------------------------------------------
	//Networking methods

	/**
	 * Loads in a game
	 * @param model the model to be loaded in
	 * @throws ModelException if model is incorrect
	 */
	private void reloadGame(NetGameModel model) throws ModelException
	{
		//reload the game but don't force it
		this.reloadGame(model, false);
	}

	/**
	 * Reloads the game
	 * @param model the model to reload
	 * @param forced if true, the version number won't be checked
	 * @throws ModelException
	 */
	private boolean updateInProgress = false;
	private void reloadGame(NetGameModel model, boolean forced) throws ModelException
	{
		if (forced == false && model.getVersion() == this.version && this.version > 0 )
		{
			return;
		}
		if (updateInProgress && !forced)
		{
			System.out.println("\n--------------------- UPDATE IN PROGRESS -------------------------");
			return;
		}
		updateInProgress = true;

		System.out.println("\n--------------------- Refresh: "+this.refreshCount+" -------------------------");
		/*if (forced)
			System.out.println("Forced update of game");
		else
			System.out.println("Reloading the game from "+this.version+" to "+model.getVersion());*/
		
		

		//Add new players if needed
		Translate trans = new Translate();
		GameModel game = trans.fromNetGameModel(model);
		
		//If there are new players or the number of resources have changed
		List<Player> newplayers = game.players;
		List<Player> oldplayers = this.players;
		
		//Check if we need to got our player index
		if (this.myPlayerIndex == -1)
		{
			Iterator<Player> iter = this.players.iterator();
			while (iter.hasNext())
			{
				Player p = iter.next();
				if (p.playerID() == this.proxy.getUserId())
				{
					System.out.println("Setting player ID to be "+p.playerIndex());
					this.myPlayerIndex = p.playerIndex();
					break;
				}
			}
		}
		
		//Check if we have a different size
		if (newplayers.size() != oldplayers.size() || !newplayers.equals(oldplayers))
		{
			//System.out.println("Updated the players");
			this.SetPlayers(newplayers);
			this.notifyCenter.notify(ModelNotification.PLAYERS);
		}
		
		//If we don't have a player index
		if (this.myPlayerIndex == -1 && this.players.size() == 4)
		{
			for (int i=0;i<4; i++)
			{
				Player p = this.players.get(i);
				if (p.playerID() == this.proxy.getUserId())
				{
					this.myPlayerIndex = i;
					System.out.println("===Received new player Index of "+this.myPlayerIndex+" ===");
					break;
				}
			}
		}
		
		
//		//check if resources have changed
//		if (oldresources != newresources)
//		{
//			System.out.println("Resource changed: " + newresources);
//			this.SetPlayers(newplayers);
//			this.notifyCenter.notify(ModelNotification.RESOURCES);
//		}
//		if (ClientGame.getGame().CurrentState() == GameRound.PLAYING)
		Bank oldPlayerbank = this.players.get(this.myPlayerIndex).playerBank;
		Bank newPlayerbank = newplayers.get(this.myPlayerIndex).playerBank;
		//check if resources have changed
		if (!newPlayerbank.equals(oldPlayerbank) && newPlayerbank != null)
		{
			this.players.get(this.myPlayerIndex).playerBank = newPlayerbank;
			this.notifyCenter.notify(ModelNotification.RESOURCES);
		}


		//Update our chat
		if (game.waterCooler.size() > this.waterCooler.size())
		{
			this.waterCooler = game.waterCooler;
			//System.out.println("New watercooler size: " + waterCooler.size());
			this.notifyCenter.notify(ModelNotification.CHAT);
		}

		GameState newgamestate = game.gameState;
		GameRound oldstate = game.gameState.state;
		GameRound newstate = game.gameState.state;
		
		TurnState oldTurnState = this.turnState;
		//Handle the new player state
		if(this.version != -1 && players.size() < 4)
		{
			this.turnState = TurnState.WAITING_FOR_PLAYERS;
		}
		else if(this.getVictoryPointManager().anyWinner())
		{
			this.turnState = TurnState.GAME_OVER;
		}
		//TODO implement trade offer turnstate
		//TODO implement placing piece turnstate
		//TODO implement domestic_trade turnstate
		//TODO implement maritime_trade turnstate
		else
		{
			switch (newstate){
				case FIRSTROUND: 
					if (newgamestate.activePlayerIndex == this.myPlayerIndex)
						this.turnState = TurnState.FIRST_ROUND_MY_TURN;
					else
						this.turnState = TurnState.FIRST_ROUND_WAITING;
					break;
				case SECONDROUND:
					if (newgamestate.activePlayerIndex == this.myPlayerIndex)
						this.turnState = TurnState.SECOND_ROUND_MY_TURN;
					else
						this.turnState = TurnState.SECOND_ROUND_WAITING;
					break;
				case ROLLING:
					if (newgamestate.activePlayerIndex == this.myPlayerIndex)
						this.turnState = TurnState.ROLLING;
					else
						this.turnState = TurnState.WAITING;
					break;
				case ROBBING:
					if (newgamestate.activePlayerIndex == this.myPlayerIndex)
						this.turnState = TurnState.ROBBING;
					else
						this.turnState = TurnState.WAITING;
					break;
				case DISCARDING:
					if(this.turnState != TurnState.DISCARDED_WAITING)
					{
						this.turnState = TurnState.DISCARDING;
						
					}
					break;
				case PLAYING:
					if (newgamestate.activePlayerIndex == this.myPlayerIndex)
						this.turnState = TurnState.PLAYING;
					else
						this.turnState = TurnState.WAITING;
					
					if (this.playerIndexSendingOffer == this.myPlayerIndex)
						this.turnState = TurnState.OFFERED_TRADE;
					
					break;
				default:
					this.turnState = TurnState.WAITING;
					break;
			}
		}
		
		if (this.turnState != oldTurnState || (!this.gameState.equals(newgamestate) && newstate != null))
		{
			gameState = newgamestate;
			//handle the logic from this
			System.out.println("STATE Refreshed to "+newstate+ " current player:"+newgamestate.activePlayerIndex);
			System.out.println("Old TS: "+oldTurnState+" New: "+this.turnState);
			this.notifyCenter.notify(ModelNotification.STATE);
			
		}
		
		
		
		//Update the map model
		MapModel newmap = game.mapModel;
		
		if (!this.map.equals(newmap) && newmap != null)
		{
			boolean force = this.map.IsForced();
			boolean setup = this.map.IsSetup();
			
			this.map = newmap;
			this.map.ForceUpdate(force);
			this.map.SetupPhase(setup);
			
			this.notifyCenter.notify(ModelNotification.MAP);
		}
		
		//updates the bank
		Bank newbank = game.gameBank;
		if (!this.gameBank.equals(newbank) && newbank != null)
		{
			this.gameBank = newbank;
			this.notifyCenter.notify(ModelNotification.BANK);
		}
		
		//Victory point manager
		VictoryPointManager newVPM = game.victoryPointManager;
		if (!victoryPointManager.equals(newVPM) && newVPM != null)
		{
			this.victoryPointManager = newVPM;
			this.notifyCenter.notify(ModelNotification.SCORE);
		}
		
		//Game action log
		GameActionLog newLog = game.log;
		if (!newLog.equals(this.log) && newLog != null)
		{
			this.log = newLog;
			this.notifyCenter.notify(ModelNotification.LOG);
		}
		
		if (this.version == -1)
			this.notifyCenter.notify(ModelNotification.ALL);
		
		this.version = model.getVersion();
		//throw new ModelException();


		//  check for trade offer, set to -1 if there is no trade in process
		NetTradeOffer offer = model.getNetTradeOffer();
//		System.out.println("Offer: " + offer);
		if(offer != null){
			playerIndexWithTradeOffer =  offer.getReceiver();
			playerIndexSendingOffer = offer.getSender();
//			System.out.println("Receiver: " + playerIndexWithTradeOffer);
//			System.out.println("Sender: " + playerIndexSendingOffer);
			if(playerIndexWithTradeOffer == this.myPlayerIndex()){
				//  if the player has a trade waiting for them, get resources, then notify
				NetResourceList resourcesForTrade = offer.getNetResourceList();
				resourceToTrade = new int[5];
				resourceToTrade[0] = resourcesForTrade.getNumBrick();
				resourceToTrade[1] = resourcesForTrade.getNumOre();
				resourceToTrade[2] = resourcesForTrade.getNumSheep();
				resourceToTrade[3] = resourcesForTrade.getNumWheat();
				resourceToTrade[4] = resourcesForTrade.getNumWood();

//				System.out.println("Resources: " + resourceToTrade);
				
				this.notifyCenter.notify(ModelNotification.STATE);
				this.notifyCenter.notify(ModelNotification.TRADE);
			}
		}
		else
		{
//			System.out.println("with offer: " + playerIndexWithTradeOffer);
//			System.out.println("sending offer: " + playerIndexSendingOffer);
//			System.out.println("resources1; " + resourceToTrade);
			//  if the offer previously existed then send out a noficication as you update
			if(playerIndexWithTradeOffer >= 0){
//				System.out.println("resources2; " + resourceToTrade);
				playerIndexWithTradeOffer = -2;
				playerIndexSendingOffer = -2;
				resourceToTrade = null;
				this.notifyCenter.notify(ModelNotification.STATE);
			}
			else
			{
				//  set them all the the default if there is not offer just to be safe
				playerIndexWithTradeOffer = -2;
				playerIndexSendingOffer = -2;
				resourceToTrade = null;
			}
//			System.out.println("resources3; " + resourceToTrade);
		}
		updateInProgress = false;
	}

	public void LoadGame(NetGame model) throws ModelException
	{
		this.reset();
		Translate trans = new Translate();
		this.SetPlayers(trans.fromNetPlayers(model.getNetPlayers()));
		this.gameID = model.getId();
		this.gameTitle = model.getTitle();

		//TODO  make sure I assign the colors correctly
	}
	
	public void doneDiscarding()
	{
		if(this.gameState.state == GameRound.DISCARDING)
		{
			
			this.setTurnState(TurnState.DISCARDED_WAITING);
		}
		
	}

	/**
	 * The number of times the server has refreshed itself- used to test the poller
	 * @return
	 */
	public int GetRefreshCount()
	{
		return refreshCount;
	}
	/**
	 * What the poller pokes to refresh the game model from teh server
	 * @see client.networking.Poller
	 */
	public void RefreshFromServer() throws ModelException
	{

		try {
			if (proxy == null)
			{
				System.err.println("Proxy was null");
				return;
			}
			NetGameModel model = proxy.getGameModel();
			if (model == null) {
				System.err.println("Model was null from the server");
				throw new ModelException("Model was null from server");
			}
			//Refresh the game
			this.reloadGame(model);
		} catch (ServerProxyException e) {
			System.err.println("Wasn't able to update");
			e.printStackTrace();
			throw new ModelException("Server proxy wasn't able to update");
		}
		this.refreshCount++;

	}

	/**
	 * Gets the current TurnState
	 * @return the TurnState
	 */
	public TurnState getTurnState()
	{
		return turnState;
	}

	public IMapModel GetMapModel()
	{
		return new UnmodifiableMapModel(map);
	}
	
	
	public void offerTrade(List<Integer> resourceList, int receiver) throws ServerProxyException{
		
		//  TODO:  Fix this!
		
		this.proxy.offerTrade(resourceList, receiver);
//		this.proxy.acceptTrade(false);
	}

	
	/**
	 * Takes resource from the bank and gives them to the current player
	 * @param type
	 * @param count
	 * @throws ModelException
	 * @throws ServerProxyException 
	 */
	public void maritimeTradeCurrentPlayer(int ratio, ResourceType inputResource, ResourceType outputResource) throws ModelException, ServerProxyException{
//		System.out.println("In taking from bank: The bank has" + gameBank.getResourceCount(inputResource) + " of " + inputResource);
		
		//  give bank the player's resources
		gameBank.giveResource(inputResource, ratio);
		this.players.get(this.myPlayerIndex).playerBank.getResource(inputResource, ratio);
		
		//  give the player the bought resource
		gameBank.getResource(outputResource, 1);
		this.players.get(this.myPlayerIndex).playerBank.giveResource(outputResource, 1);
		
		this.notifyCenter.notify(ModelNotification.RESOURCES);
		this.proxy.maritimeTrade(ratio, inputResource, outputResource);
		
	}
	

	
	
//	/**
//	 * Takes resources from the bank and gives them to the passed player index
//	 * @param playerIndex
//	 * @param type
//	 * @param count
//	 * @throws ModelException
//	 */
//	public void giveResourcesToPlayer(int playerIndex, ResourceType type, int count) throws ModelException{
//		gameBank.getResource(type, count);
//		this.players.get(playerIndex).playerBank.giveResource(type, count);
//		this.notifyCenter.notify(ModelNotification.RESOURCES);
//	}
//	
//	/**
//	 * Takes resources from the passed player index and gives them to the bank
//	 * @param playerIndex
//	 * @param type
//	 * @param count
//	 * @throws ModelException
//	 */
//	public void takeResourcesFromPlayer(int playerIndex, ResourceType type, int count) throws ModelException{
//		gameBank.giveResource(type, count);
//		this.players.get(playerIndex).playerBank.getResource(type, count);
//		this.notifyCenter.notify(ModelNotification.RESOURCES);
//	}
//
}

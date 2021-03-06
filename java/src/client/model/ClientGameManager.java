package client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import client.data.RobPlayerInfo;
import client.map.RobView;
import client.networking.GSONServerProxy;
import client.networking.ServerProxy;
import client.networking.ServerProxyException;
import shared.data.DataTranslator;
import shared.data.GameInfo;
import shared.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ModelNotification;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.definitions.TurnState;
import shared.model.Bank;
import shared.model.GameActionLog;
import shared.model.GameManager;
import shared.model.GameModel;
import shared.model.GameState;
import shared.model.ModelException;
import shared.model.ModelObserver;
import shared.model.ModelSubject;
import shared.model.NotificationCenter;
import shared.model.OfferedTrade;
import shared.model.Player;
import shared.model.VictoryPointManager;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.model.IMapModel;
import shared.model.map.model.MapModel;
import shared.model.map.model.UnmodifiableMapModel;

public class ClientGameManager extends GameManager implements ModelSubject
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
	private Coordinate lastRoadBuiltStart = null;
	private Coordinate lastRoadBuiltEnd = null;
	protected NotificationCenter notifyCenter;
	/**
	 * Creates the client game manager with the proxy
	 * @param clientProxy
	 */
	public ClientGameManager(ServerProxy clientProxy)
	{
		super();
		this.proxy = clientProxy;
		notifyCenter = new NotificationCenter();
		
		turnState = TurnState.WAITING_FOR_PLAYERS;
	}

	/**
	 * Gets a client proxy
	 * @param clientProxy
	 * @param myPlayerID
	 */
	public ClientGameManager(GSONServerProxy clientProxy, int myPlayerIndex)
	{
		this(clientProxy);
		this.myPlayerIndex = myPlayerIndex;
	}

	

	//========================================================================================
	//Notification Center

	/**
	 * Starts listening to all changes 
	 */
	public boolean startListening(ModelObserver listener)
	{
		notifyCenter.add(listener);
		return true;
	}
	/**
	 * starts listening for a specific type of change
	 */
	public boolean startListening(ModelObserver listener, ModelNotification type)
	{
		notifyCenter.add(listener,type);
		return true;
	}
	
	/**
	 * Stops listening on a model observer
	 */
	public boolean stopListening(ModelObserver listener)
	{
		notifyCenter.remove(listener);
		return true;
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
				Player play = DataTranslator.convertPlayerInfo(newplay);
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
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		try
		{
			GameInfo game = proxy.createGame(randomTiles, randomNumbers, randomPorts, name);
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
			GameModel model = proxy.rollNumber(roll);
			this.reloadGame(model,true);
		}
		catch (ServerProxyException|ModelException e)
		{
			System.out.println(e.getMessage());
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
			
			//TODO This logic will need added to
			if (turnState == TurnState.FIRST_ROUND_MY_TURN || turnState == TurnState.SECOND_ROUND_MY_TURN)
				free = true;
			else if (turnState == TurnState.ROAD_BUILDER_SECOND || turnState == TurnState.ROAD_BUILDER)
				free = true;
			
			this.BuildRoad(myPlayerIndex, start, end, free);

			System.out.println("Building a road in state "+this.turnState);
			if (turnState == TurnState.ROAD_BUILDER_SECOND)
			{
				proxy.roadBuildingCard(lastRoadBuiltStart, lastRoadBuiltEnd, start, end);
				this.setTurnState(TurnState.PLAYING);
			}
			else if (turnState != TurnState.ROAD_BUILDER)
			{
				proxy.buildRoad(start, end, free);
			}
			else{
				this.setTurnState(TurnState.ROAD_BUILDER_SECOND);
			}
			lastRoadBuiltStart = start;
			lastRoadBuiltEnd = end;

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

			GameModel newmodel = proxy.buildSettlement(point, free);
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
			GameModel newmodel = proxy.buyDevCard();
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
			GameModel model = proxy.monopolyCard(resource);
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
			GameModel model = proxy.monumentCard();
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
			GameModel model = proxy.yearOfPlentyCard(resource1, resource2);
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
			GameModel newmodel = proxy.buildCity(point);
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
	 * 
	 * @param point
	 */
	public void PlaceRobber(Coordinate point)
	{
		try
		{
			map.PlaceRobber(point);
			
			Iterator<CatanColor> colors = map.GetOccupiedVertices(point);
			List<RobPlayerInfo> toRob = new ArrayList<>();
			
			while (colors.hasNext())
			{
				CatanColor color = colors.next();
				int index = getPlayerIndexByColor(color);
				
				if (index != myPlayerIndex)
				{
					
					Player p = this.players.get(index);
					System.out.print("Rob:"+p);
					System.out.println("\t Bank:"+p.playerBank);
					if (p.playerIndex() != index) {
						System.err.println("Index should match on player index"+p.playerIndex() +" and "+index);
						continue;
					}
					RobPlayerInfo rpi = new RobPlayerInfo();
					rpi.setId(p.playerID());
					rpi.setName(p.name);
					rpi.setColor(color);
					rpi.setPlayerIndex(index);
					rpi.setNumCards(p.totalResources());
					toRob.add(rpi);
				}
			}
			RobPlayerInfo[] robArray = new RobPlayerInfo[0];
			robArray = toRob.toArray(robArray);
			
			//TODO This is really bad practice
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
	
	/**
	 * 
	 * @param victimIndex
	 */
	public void RobVictim(int victimIndex)
	{
		if (this.turnState == TurnState.SOLIDER_CARD)
		{
			//Play a solider card
			try{
				turnState = TurnState.PLAYING;
				notifyCenter.notify(ModelNotification.STATE);
				GameModel newmodel = this.proxy.soldierCard(victimIndex, map.GetRobberLocation().getPoint());
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
				
				GameModel newmodel = this.proxy.robPlayer(victimIndex, map.GetRobberLocation().getPoint());
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
			GameModel newmodel = proxy.finishTurn();
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
			GameModel newmodel = proxy.discardCards(resourceList);
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
			notifyCenter.notify(ModelNotification.CHAT);
			GameModel newModel = proxy.sendChat(message);
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
	private void reloadGame(GameModel model) throws ModelException
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
	private void reloadGame(GameModel game, boolean forced) throws ModelException
	{
		if (forced == false && game.version == this.version && this.version > 0 )
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
		
		
		//If there are new players or the number of resources have changed
		List<Player> newplayers = game.players;
		List<Player> oldplayers = this.players;
		
		//Check if we need to got our player index
		if (this.myPlayerIndex == -1)
		{
			Iterator<Player> iter = newplayers.iterator();
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
			System.out.println("Updated the players");
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
		
		if (this.players.size() != 4 || this.myPlayerIndex == -1)
		{
			System.out.println("updated aborted");
			this.updateInProgress = false;
			return;
		}
		
		
		boolean changedNumResources = false;
		if(newplayers.size() == oldplayers.size())
		{
			for(int i = 0; i < oldplayers.size(); i++)
			{
				Bank oldPlayerbank = this.players.get(i).playerBank;
				Bank newPlayerbank = newplayers.get(i).playerBank;
				//check if resources have changed
				if (!newPlayerbank.equals(oldPlayerbank) && newPlayerbank != null)
				{
					this.players.get(i).playerBank = newPlayerbank;
					changedNumResources = true;
				}
			}
		}
		//notify if any player had a resource change
		if(changedNumResources)
		{
			this.notifyCenter.notify(ModelNotification.RESOURCES);
		}
		
		//Update the game bank
		if (!game.gameBank.equals(gameBank))
		{
			gameBank = game.gameBank;
		}
		
		//Update the other players's banks
		for(Player p: newplayers)
		{
			if (!p.playerBank.equals(players.get(p.playerIndex()).playerBank))
				players.get(p.playerIndex()).playerBank = p.playerBank;
		}

		//Update our chat
		if (game.waterCooler.size() > this.waterCooler.size())
		{
			this.waterCooler = game.waterCooler;
			this.notifyCenter.notify(ModelNotification.CHAT);
		}
		
	//  check for trade offer, set to -1 if there is no trade in process
		OfferedTrade offer = game.trade;

		if(offer != null)
		{
			playerIndexWithTradeOffer =  offer.getToPlayerID();
			playerIndexSendingOffer = offer.getFromPlayerID();

			if(playerIndexWithTradeOffer == this.myPlayerIndex())
			{
				//  if the player has a trade waiting for them, get resources, then notify
				resourceToTrade = new int[5];
				resourceToTrade[0] = offer.getWantedResourceAmount(ResourceType.BRICK)
						- offer.getOfferedResourceAmount(ResourceType.BRICK);
				resourceToTrade[1] = offer.getWantedResourceAmount(ResourceType.ORE)
						- offer.getOfferedResourceAmount(ResourceType.ORE);
				resourceToTrade[2] = offer.getWantedResourceAmount(ResourceType.SHEEP)
						- offer.getOfferedResourceAmount(ResourceType.SHEEP);
				resourceToTrade[3] = offer.getWantedResourceAmount(ResourceType.WHEAT)
						- offer.getOfferedResourceAmount(ResourceType.WHEAT);
				resourceToTrade[4] = offer.getWantedResourceAmount(ResourceType.WOOD)
						- offer.getOfferedResourceAmount(ResourceType.WOOD);
			}
		}
		else
		{
			playerIndexWithTradeOffer = -2;
			playerIndexSendingOffer = -2;
			resourceToTrade = null;
		}

		GameState newgamestate = game.gameState;
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
			this.gameState.endGame();
		}
		else
		{
			switch (newstate)
			{
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
					{
						if (oldTurnState == TurnState.DISCARDED_WAITING)
							this.turnState = TurnState.DISCARDED_CLOSING;
						else
							this.turnState = TurnState.ROBBING;
					}
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
					
					if (this.playerIndexWithTradeOffer == this.myPlayerIndex)
						this.turnState = TurnState.OFFERED_TRADE;
					
					if (this.playerIndexSendingOffer == this.myPlayerIndex)
						this.turnState = TurnState.DOMESTIC_TRADE;
					
					break;
				default:
					this.turnState = TurnState.WAITING;
					break;
			}
		}
		
		//If the game is over the game better stay over
		if (oldTurnState == TurnState.GAME_OVER || this.victoryPointManager.anyWinner())
		{
			this.turnState = TurnState.GAME_OVER;
		}
		
		if (this.turnState != oldTurnState || (!this.gameState.equals(newgamestate) && newstate != null))
		{
			gameState = newgamestate;
			System.out.println("STATE Refreshed to "+newstate+ " current player:"+newgamestate.activePlayerIndex);
			System.out.println("Old TS: "+oldTurnState+" New: "+this.turnState);
			this.notifyCenter.notify(ModelNotification.STATE);
			
		}
		
		
		
		//Update the map model
		MapModel newmap = game.mapModel;
		
		if (!this.map.equals(newmap) && newmap != null)
		{
			boolean setup = this.map.IsSetup();
			
			this.map = newmap;
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
		updateInProgress = false;
		this.version = game.version;
		updateInProgress = false;
	}

	/**
	 * Loads the game from the model recieved from the server
	 * @param model
	 * @throws ModelException
	 */
	public void LoadGame(GameInfo model) throws ModelException
	{
		this.reset();
		this.SetPlayers(DataTranslator.convertPlayerInfo(model.getPlayers()));
		this.gameID = model.getId();
		this.gameTitle = model.getTitle();

		//TODO  make sure I assign the colors correctly
	}
	
	/**
	 * Uses to let the discarding state to finish
	 */
	public void doneDiscarding()
	{
		if(this.gameState.state == GameRound.DISCARDING)
		{
			
			this.setTurnState(TurnState.DISCARDED_WAITING);
		}
		
		
	}
	
	public void doneClosingDiscard()
	{
		if(this.turnState == TurnState.DISCARDED_CLOSING)
		{
			try 
			{
				ForceRefreshFromServer();
			}
			catch (ModelException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

		try 
		{
			if (proxy == null)
			{
				System.err.println("Proxy was null");
				return;
			}
			GameModel model = proxy.getGameModel(this.version);
			if (model == null) 
				return;
			
			//Refresh the game
			this.reloadGame(model);
		} 
		catch (ServerProxyException e) 
		{
			System.err.println("Wasn't able to update");
			throw new ModelException("Server proxy wasn't able to update");
		}
		
		this.refreshCount++;
	}
	
	public void ForceRefreshFromServer() throws ModelException
	{

		try 
		{
			if (proxy == null)
			{
				System.err.println("Proxy was null");
				return;
			}
			GameModel model = proxy.getGameModel(this.version);
			if (model == null) 
				return;
			
			//Refresh the game
			this.reloadGame(model,true);
		} 
		catch (ServerProxyException e) 
		{
			System.err.println("Wasn't able to update");
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

	/**
	 * 
	 * @return
	 */
	public IMapModel GetMapModel()
	{
		return new UnmodifiableMapModel(map);
	}
	
	/**
	 * Offers a trade to a player
	 * @param resourceList the resources to trade
	 * @param receiver the player index getting the offer
	 * @throws ServerProxyException
	 */
	public void offerTrade(List<Integer> resourceList, int receiver) throws ServerProxyException
	{
		//  TODO:  Fix this!
		//if (!super.CanOfferTrade(this.myPlayerIndex))
		//	return;
		this.proxy.offerTrade(resourceList, receiver);

	}

	
	/**
	 * Takes resource from the bank and gives them to the current player
	 * @param type
	 * @param count
	 * @throws ModelException
	 * @throws ServerProxyException 
	 */
	public void maritimeTradeCurrentPlayer(int ratio, ResourceType inputResource, ResourceType outputResource) throws ModelException, ServerProxyException
	{
		
		//  give bank the player's resources
		gameBank.giveResource(inputResource, ratio);
		this.players.get(this.myPlayerIndex).playerBank.getResource(inputResource, ratio);
		
		//  give the player the bought resource
		gameBank.getResource(outputResource, 1);
		this.players.get(this.myPlayerIndex).playerBank.giveResource(outputResource, 1);
		
		this.notifyCenter.notify(ModelNotification.RESOURCES);
		this.proxy.maritimeTrade(ratio, inputResource, outputResource);
		
	}

	/**
	 * Gets the number of soliders the current player has
	 * @return
	 */
	public int GetPlayerSoliderCount() 
	{
		try 
		{
			return GetPlayer(this.myPlayerIndex).playerBank.getNumberSolidersRecruited();
		}
		catch (ModelException e) 
		{
			return -1;
		}
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

package client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import client.data.ClientDataTranslator;
import client.data.GameInfo;
import client.data.PlayerInfo;
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
import shared.model.map.Coordinate;

import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import shared.model.map.*;
import shared.model.map.model.IMapModel;
import shared.model.map.model.MapModel;
import shared.model.map.model.UnmodifiableMapModel;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;

public class ClientGameManager extends GameManager
{
	private ServerProxy proxy;
	private int myPlayerIndex = -1;
	private TurnState turnState;

	private int refreshCount = 0;
	private CatanColor myPlayerColor;
	/**
	 * Creates the client game manager with the proxy
	 * @param clientProxy
	 */
	public ClientGameManager(ServerProxy clientProxy)
	{
		super();
		this.proxy = clientProxy;
		turnState = null;
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
			this.myPlayerColor = color;
			boolean rejoining = false;
			List<Player> newplayers = new ArrayList<>();
			Iterator<PlayerInfo> iter = game.getPlayers().iterator();
			while(iter.hasNext())
			{
				PlayerInfo newplay = iter.next();
				System.out.println("Player: "+newplay.getName()+" at index:"+newplay.getPlayerIndex());
				if (newplay.getId() == proxy.getUserId())
				{
					System.out.println("Joined with player index:"+newplay.getPlayerIndex());
					this.myPlayerIndex = newplay.getPlayerIndex();
					rejoining = true;
				}
				Player play = ClientDataTranslator.convertPlayerInfo(newplay);
				newplayers.add(play);

			}
			//If we are rejoining then don't add ourselves
			if (!rejoining)
			{
				newplayers.add(new Player(proxy.getUserName(), players.size(), color, true));
				this.myPlayerIndex = players.size();
			}
			this.SetPlayers(newplayers);
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
	public boolean createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		try
		{
			NetGame game = proxy.createGame(randomTiles, randomNumbers, randomPorts, name);
			this.LoadGame(game);
		}
		catch (ServerProxyException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
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
			
			this.BuildRoad(myPlayerIndex, start, end, free);

			EdgeLocation location = Translate.GetEdgeLocation(start, end);
			
			proxy.buildRoad(location, free);

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

	public void BuildSettlement(Coordinate point)
	{
		try
		{
			boolean free = false;
			
			if (turnState == TurnState.FIRST_ROUND_MY_TURN || turnState == TurnState.SECOND_ROUND_MY_TURN)
				free = true;
			
			this.BuildSettlement(myPlayerIndex, point, free);

			VertexLocation location = Translate.GetVertexLocation(point);

			proxy.buildSettlement(location, free);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
		}
		catch (ServerProxyException e)
		{
			//TODO How should this be handled?
			e.printStackTrace();
		}
	}

	public void BuildCity(Coordinate point)
	{
		try
		{
			this.BuildCity(myPlayerIndex, point);

			VertexLocation location = Translate.GetVertexLocation(point);

			proxy.buildCity(location);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
		}
		catch (ServerProxyException e)
		{
			//TODO How should this be handled?
			e.printStackTrace();
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
	private void reloadGame(NetGameModel model, boolean forced) throws ModelException
	{
		if (forced == false && model.getVersion() == this.version && this.version > 0 )
		{
			return;
		}
		System.out.print("\n-----------------------------------------\nRefresh: "+this.refreshCount+":");
		if (forced)
			System.out.println("Forced update of game");
		else
			System.out.println("Reloading the game from "+this.version+" to "+model.getVersion());
		
		

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
		
		int oldresources = ClientDataTranslator.totalPlayerResouces(newplayers);
		int newresources = ClientDataTranslator.totalPlayerResouces(oldplayers);
		
		//check if resources have changed
		if (oldresources != newresources)
		{
			this.notifyCenter.notify(ModelNotification.RESOURCES);
		}
		

		//Update our chat
		if (game.waterCooler.size() > this.waterCooler.size())
		{
			this.waterCooler = game.waterCooler;
			System.out.println("New watercooler size: " + waterCooler.size());
			this.notifyCenter.notify(ModelNotification.CHAT);
		}

		GameState newgamestate = game.gameState;
		GameRound oldstate = game.gameState.state;
		GameRound newstate = game.gameState.state;
		//System.out.println("STATE current:"+gameState.state+" new:"+newstate.state);
		if (!this.gameState.equals(newgamestate) && newstate != null)
		{
			gameState = newgamestate;
			//handle the logic from this
			System.out.println("STATE Refreshed to "+newstate);
			this.notifyCenter.notify(ModelNotification.STATE);
		}
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
					if(players.get(this.myPlayerIndex).totalResources() > 7)
						this.turnState = TurnState.DISCARDING;
					else
						this.turnState = TurnState.DISCARDED_WAITING;
					break;
				case PLAYING:
					if (newgamestate.activePlayerIndex == this.myPlayerIndex)
						this.turnState = TurnState.PLAYING;
					else
						this.turnState = TurnState.WAITING;
				default:
					this.turnState = TurnState.WAITING;
					break;
			}
		}
		
		if (this.turnState != oldTurnState)
		{
			System.out.println("Old TS: "+oldTurnState+" New: "+this.turnState);
			this.notifyCenter.notify(ModelNotification.STATE);
		}
		
		
		
		//Update the map model
		MapModel newmap = game.mapModel;
		
		if (!this.map.equals(newmap) && newmap != null)
		{
			this.map = newmap;
			this.notifyCenter.notify(ModelNotification.MAP);
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
		System.out.println("Refresh finished");
		//throw new ModelException();
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
}

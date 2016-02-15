package client.model;

import java.util.ArrayList;
import java.util.List;

import client.data.GameInfo;
import client.networking.ServerProxy;
import client.networking.ServerProxyException;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.definitions.TurnState;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.Player;
import shared.model.Translate;
import shared.model.map.Coordinate;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;

public class ClientGameManager extends GameManager
{
	private ServerProxy proxy;
	private int myPlayerID;
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
	}
	
	/**
	 * Gets a client proxy
	 * @param clientProxy
	 * @param myPlayerID
	 */
	public ClientGameManager(ServerProxy clientProxy, int myPlayerID)
	{
		this(clientProxy);
		this.myPlayerID = myPlayerID;
	}
	
	
	/**
	 * Get the ID of the current player client
	 * @return
	 */
	public int myPlayerID()
	{
		return this.myPlayerID;
	}
	
	
	/**
	 * The current player's color
	 * @return
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
		return this.players.get(this.myPlayerID).playerBank.getResourceCount(type);
	}
	
	/**
	 * Gets the number of devCards for the current player
	 * @param type
	 * @return
	 */
	public int playerDevCardCount(DevCardType type)
	{
		return this.players.get(this.myPlayerID).playerBank.getDevCardCount(type);
	}
	
	/**
	 * Gets the number of devCards for the current player
	 * @param type
	 * @return
	 */
	public int playerPieceCount(PieceType type)
	{
		try {
			return this.players.get(this.myPlayerID).playerBank.getPieceCount(type);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Joins a game
	 * @param gameID
	 * @param color
	 * @return
	 */
	public boolean joinGame(GameInfo game, CatanColor color)
	{
		try {
			proxy.joinGame(game.getId(), color);
			this.gameID = game.getId();
			this.gameTitle = game.getTitle();
			this.myPlayerColor = color;
			List<Player> players = new ArrayList<>();
			for (int i=0; i< game.getPlayers().size(); i++)
			{
				
			}
			//this.SetPlayers(ClientDataTranslator.);
			//If we can't joining a game then an exception will be thrown
			
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
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
		try {
			NetGame game = proxy.createGame(randomTiles, randomNumbers, randomPorts, name);
			this.LoadGame(game);
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ModelException e) {
			// TODO Auto-generated catch block
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
		try {
			roll = super.RollDice();
			proxy.rollNumber(roll);
		} catch (ServerProxyException|ModelException e) {
			// TODO Auto-generated catch block
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
		try {
			this.BuildRoad(myPlayerID, start, end);
			//proxy.buildRoad(edgeLocation, false);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Discards the cards specified by the resourceList list
	 * @param resourceList the list of resources to discard
	 */
	public void DiscardCards(List<Integer> resourceList)
	{
		try
		{
			proxy.discardCards(resourceList);
		}
		catch(ServerProxyException e)
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
		this.PlayerChat(myPlayerID, message);
	}
	

	@Override
	/**
	 * Over rides player chat to talk to the server
	 */
	public void PlayerChat(int playerID, String message)
	{
		super.PlayerChat(playerID, message);
		try {
			proxy.sendChat(message);
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to send chat!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the points of the current player
	 * @return
	 */
	public int PlayerPoints()
	{
		return this.victoryPointManager.getVictoryPoints(this.myPlayerID);
	}
	
	//--------------------------------------------------------------------------
	//Networking methods
	
	/**
	 * Loads in a game 
	 * @param model the model to be loaded in
	 * @throws ModelException if model is incorrect
	 */
	public void reloadGame(NetGameModel model) throws ModelException
	{
		if (model.getVersion() == this.version)
			return;
		this.version = model.getVersion();
		//TODO All of this
		//TODO update turn status
		//throw new ModelException();
	}
	
	public void LoadGame(NetGame model) throws ModelException
	{
		this.reset();
		Translate trans = new Translate();
		this.SetPlayers(trans.fromNetPlayers(model.getNetPlayers()));
		this.gameID = model.getId();
		this.gameTitle = model.getTitle();
		
		//make sure I assign the colors correctly
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
				return;
			}
			this.reloadGame(model);
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			System.err.println("Wasn't able to update");
			//e.printStackTrace();
			//throw new ModelException();
		}
		this.refreshCount++;
		
	}
}

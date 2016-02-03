package client.model;

import client.networking.ServerProxy;
import client.networking.ServerProxyException;
import shared.definitions.CatanColor;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.Translate;
import shared.model.map.Coordinate;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;

public class ClientGameManager extends GameManager
{
	private ServerProxy proxy;
	private int myPlayerID;
	private int gameID;
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
	 * Joins a game
	 * @param gameID
	 * @param color
	 * @return
	 */
	public boolean joinGame(int gameID, CatanColor color)
	{
		try {
			NetGame game = proxy.joinGame(gameID, color);
			this.LoadGame(game);
		} catch (ServerProxyException | ModelException e) {
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
		int roll = super.RollDice();
		try {
			proxy.rollNumber(roll);
		} catch (ServerProxyException e) {
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
		throw new ModelException();
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
	 * What the poller pokes to refresh the game model from teh server
	 * @see client.networking.Poller
	 */
	public void RefreshFromServer() throws ModelException
	{
		NetGameModel model;
		try {
			model = proxy.getGameModel();
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ModelException();
		}
		this.reloadGame(model);
	}
}

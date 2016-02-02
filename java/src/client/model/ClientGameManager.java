package client.model;

import client.networking.ServerProxy;
import client.networking.ServerProxyException;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.map.Coordinate;

public class ClientGameManager extends GameManager
{
	private ServerProxy proxy;
	private int myPlayerID;
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
	
	public int PlayerPoints()
	{
		return this.victoryPointManager.getVictoryPoints(this.myPlayerID);
	}
}

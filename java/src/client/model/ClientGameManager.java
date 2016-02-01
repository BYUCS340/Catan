package client.model;

import client.networking.ServerProxy;
import client.networking.ServerProxyException;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.map.Coordinate;

public class ClientGameManager extends GameManager
{
	private ServerProxy proxy;
	
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
	
	@Override
	public void BuildRoad(int playerID,Coordinate start, Coordinate end) throws ModelException
	{
		super.BuildRoad(playerID, start, end);
		
		//proxy.buildRoad(edgeLocation, false);
	}
}
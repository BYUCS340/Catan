package client.model;

import client.networking.Poller;
import client.networking.RealEarlyServerProxy;
import client.networking.EarlyServerProxy;
import shared.model.GameManager;
import shared.model.ModelException;

public class ClientGame {
	static private ClientGameManager game;
	static private RealEarlyServerProxy proxy;
	static private Poller poller;
	
	/**
	 * Gets the current game manager
	 * @return
	 */
	static public ClientGameManager getGame()
	{
		if (game == null)
			ClientGame.startGameWithProxy(null);
		return ClientGame.game;
	}
	
	/**
	 * This must be called before getting a game
	 * @param proxy
	 */
	static public void startGameWithProxy(RealEarlyServerProxy proxy)
	{
		ClientGame.proxy = proxy;
		ClientGame.game = new ClientGameManager(proxy);
		ClientGame.poller = new Poller();
	}
	
	/**
	 * Starting the game for testing purposes
	 * @param proxy
	 */
	static public void startGameWithProxy(EarlyServerProxy proxy)
	{
		ClientGame.game = new ClientGameManager(proxy);
		ClientGame.poller = new Poller();
	}
	
	static public RealEarlyServerProxy getCurrentProxy()
	{
		return ClientGame.proxy;
	}
	
	/**
	 * Starts Polling
	 */
	static public void startPolling()
	{
		poller.beginPolling();
	}
	
	/**
	 * Stops Polling
	 */
	static public void stopPolling()
	{
		poller.stopPolling();
	}
}

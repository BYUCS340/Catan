package client.model;

import client.networking.GSONServerProxy;
import client.networking.Poller;
import client.networking.RealEarlyServerProxy;
import client.networking.ServerProxy;

public class ClientGame {
	static private ClientGameManager game;
	static private GSONServerProxy proxy;
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
	static public void startGameWithProxy(GSONServerProxy proxy)
	{
		ClientGame.proxy = proxy;
		ClientGame.game = new ClientGameManager(proxy);
		ClientGame.poller = new Poller();
	}
	
	/**
	 * Starting the game for testing purposes
	 * @param proxy
	 */
	static public void startGameWithProxy(ServerProxy proxy)
	{
		ClientGame.game = new ClientGameManager(proxy);
		ClientGame.poller = new Poller();
	}
	
	static public ServerProxy getCurrentProxy()
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

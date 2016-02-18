package client.model;

import client.networking.RealServerProxy;
import client.networking.ServerProxy;
import shared.model.GameManager;
import shared.model.ModelException;

public class ClientGame {
	static private ClientGameManager game;
	static private RealServerProxy proxy;
	
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
	static public void startGameWithProxy(RealServerProxy proxy)
	{
		ClientGame.proxy = proxy;
		ClientGame.game = new ClientGameManager(proxy);
	}
	
	/**
	 * Starting the game for testing purposes
	 * @param proxy
	 */
	static public void startGameWithProxy(ServerProxy proxy)
	{
		ClientGame.game = new ClientGameManager(proxy);
	}
	
	static public RealServerProxy getCurrentProxy()
	{
		return ClientGame.proxy;
	}
}

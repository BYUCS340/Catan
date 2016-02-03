package client.model;

import client.networking.ServerProxy;
import shared.model.GameManager;
import shared.model.ModelException;

public class ClientGame {
	static private ClientGameManager game;
	static private ServerProxy proxy;
	
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
	static public void startGameWithProxy(ServerProxy proxy)
	{
		ClientGame.proxy = proxy;
		ClientGame.game = new ClientGameManager(proxy);
	}
	
	static public ServerProxy getCurrentProxy()
	{
		return ClientGame.proxy;
	}
}

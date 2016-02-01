package client.model;

import client.networking.ServerProxy;
import shared.model.GameManager;
import shared.model.ModelException;

public class ClientGame {
	static private ClientGameManager game;
	
	/**
	 * Gets the current game manager
	 * @return
	 * @throws ModelException 
	 */
	static GameManager getGame() throws ModelException
	{
		if (game == null)
			throw new ModelException();
		return ClientGame.game;
	}
	
	/**
	 * This must be called before getting a game
	 * @param proxy
	 */
	static void startGameWithProxy(ServerProxy proxy)
	{
		ClientGame.game = new ClientGameManager(proxy);
	}
}

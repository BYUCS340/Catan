package client.join;

import client.base.*;
import client.data.*;
import shared.definitions.*;

/**
 * Interface for the join game controller
 */
public interface IJoinGameController extends IController
{
	
	/**
	 * Displays the join game view
	 */
	void start();
	
	/**
	 * Called by the join game view when the user clicks "Create new game"
	 * button. Displays the new game view.
	 */
	void startCreateNewGame();
	
	/**
	 * Called by the new game view when the user clicks the "Cancel" button
	 */
	void cancelCreateNewGame();
	
	/**
	 * Called by the new game view when the user clicks the "Create Game" button
	 */
	void createNewGame();
	
	/**
	 * Called by the join game view when the user clicks a "Join" or "Re-join"
	 * button. Displays the select color view.
	 * 
	 * @param game
	 *            The game that the user is joining
	 */
	void startJoinGame(GameInfo game);
	
	/**
	 * Called by the select color view when the user clicks the "Cancel" button
	 */
	void cancelJoinGame();
	
	/**
	 * Called by the select color view when the user clicks the "Join Game"
	 * button
	 * 
	 * @param color
	 *            The color selected by the user
	 */
	void joinGame(CatanColor color);
	
}


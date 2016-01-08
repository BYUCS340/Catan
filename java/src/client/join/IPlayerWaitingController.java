package client.join;

import client.base.*;

/**
 * Interface for the player waiting controller
 */
public interface IPlayerWaitingController extends IController
{
	
	/**
	 * Displays the player waiting view
	 */
	void start();
	
	/**
	 * Called when the "Add AI" button is clicked in the player waiting view
	 */
	void addAI();
}


package client.join;

import client.base.*;
import client.data.*;

/**
 * Interface for the player waiting view, which is displayed when the user is
 * waiting for other players to join their game
 */
public interface IPlayerWaitingView extends IOverlayView
{
	
	/**
	 * Sets the list of players who have already joined the game
	 * 
	 * @param value
	 *            List of players who have already joined the game
	 */
	void setPlayers(PlayerInfo[] value);
	
	/**
	 * Sets the list of AI types from which the user may select
	 * 
	 * @param value
	 *            List of AI types from which the user may select
	 */
	void setAIChoices(String[] value);
	
	/**
	 * Returns the type of AI selected by the user
	 * 
	 * @return The type of AI selected by the user
	 */
	String getSelectedAI();
}


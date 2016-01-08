package client.points;

import client.base.*;

/**
 * Interface for the game finished view, which is displayed when the game is
 * over
 */
public interface IGameFinishedView extends IOverlayView
{
	
	/**
	 * Sets the information about the winner displayed in the view
	 * 
	 * @param name
	 *            The winner's name
	 * @param isLocalPlayer
	 *            Indicates whether or not the winner is the local player
	 */
	void setWinner(String name, boolean isLocalPlayer);
	
}


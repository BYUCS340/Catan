package client.points;

import client.base.*;

/**
 * Interface for the points view, which displays the user's victory points
 */
public interface IPointsView extends IView
{
	
	/**
	 * Sets the number of victory points the player has
	 * 
	 * @param points
	 *            The number of victory points to display
	 */
	void setPoints(int points);
}


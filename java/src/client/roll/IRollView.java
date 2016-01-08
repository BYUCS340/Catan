package client.roll;

import client.base.*;

/**
 * Interface for the roll view, which allows the user to roll the dice
 */
public interface IRollView extends IOverlayView
{
	
	/**
	 * Sets the message displayed in the roll view
	 * 
	 * @param message
	 *            The message to be displayed
	 */
	void setMessage(String message);
	
}


package client.misc;

import client.base.*;

/**
 * Interface for the message view, which is used to display messages to the user
 */
public interface IMessageView extends IOverlayView
{
	
	/**
	 * Sets the title to be displayed.
	 * 
	 * @param title
	 *            The title
	 */
	void setTitle(String title);
	
	/**
	 * Sets the message to be displayed.
	 * 
	 * @param message
	 *            The message
	 */
	void setMessage(String message);
}


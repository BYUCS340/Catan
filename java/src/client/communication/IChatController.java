package client.communication;

import client.base.*;

/**
 * Chat controller interface
 */
public interface IChatController extends IController
{
	
	/**
	 * Called by the view when a message is sent
	 * 
	 * @param message
	 *            The message being sent
	 */
	void sendMessage(String message);
}


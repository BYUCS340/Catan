package server.commands;

import java.io.Serializable;

/**
 * Interface used for command objects.
 * @author Jonathan Sadler
 *
 */
public interface ICommand extends Serializable
{
	/**
	 * Executes a command
	 * @return True if successful, else false.
	 */
	public boolean Execute();
	
	/**
	 * Unexecutes a command.
	 * @return True if successful, else false.
	 */
	public boolean Unexecute();
	
	/**
	 * Gets the response for the server.
	 * @return The serialized response from the server.
	 */
	public String GetResponse();
	
	/**
	 * Gets the new server header associated with the request.
	 * @return Returns the header if applicable, else null.
	 */
	public String GetHeader();
}

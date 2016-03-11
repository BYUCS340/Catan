package server.commands;

/**
 * Interface used for command objects.
 * @author Jonathan Sadler
 *
 */
public interface ICommand 
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
	public String Response();
}

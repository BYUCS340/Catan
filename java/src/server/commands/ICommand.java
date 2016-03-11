package server.commands;

/**
 * Interface used for command objects.
 * @author Jonathan Sadler
 *
 */
public interface ICommand 
{
	/**
	 * Executes a command.
	 */
	public void Execute();
	
	/**
	 * Unexecutes a command.
	 */
	public void Unexecute();
	
	/**
	 * Gets the response for the server.
	 * @return The serialized response from the server.
	 */
	public String Response();
}

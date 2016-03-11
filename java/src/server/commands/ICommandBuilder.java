package server.commands;

/**
 * Default interface for builder classes. The builders generate the needed commands.
 * @author Jonathan Sadler
 *
 */
public interface ICommandBuilder 
{
	/**
	 * Builds a command.
	 * @return The command to be built.
	 */
	ICommand BuildCommand();
	
	/**
	 * The serialized data that needs passed to the command.
	 * @param object Serialized object data.
	 */
	void SetData(String object);
}

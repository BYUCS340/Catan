package server.commands.util;

import java.util.logging.Level;

import server.commands.ICommand;

/**
 * Command for changing the log level of the server.
 * @author Jonathan Sadler
 *
 */
public class UtilChangeLogLevelCommand implements ICommand 
{
	private Level level;
	
	/**
	 * Creates a command to change the log level.
	 * @param level The new log level.
	 */
	public UtilChangeLogLevelCommand(Level level)
	{
		this.level = level;
	}

	@Override
	public boolean Execute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String Response() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}

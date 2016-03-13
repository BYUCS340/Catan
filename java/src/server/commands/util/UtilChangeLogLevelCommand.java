package server.commands.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.commands.ICommand;

/**
 * Command for changing the log level of the server.
 * @author Jonathan Sadler
 *
 */
public class UtilChangeLogLevelCommand implements ICommand 
{
	private Logger logger;
	
	private Level level;
	private Level previousLevel;
	
	/**
	 * Creates a command to change the log level.
	 * @param level The new log level.
	 */
	public UtilChangeLogLevelCommand(Level level)
	{
		this.logger = Logger.getLogger("CatanServer");
		
		this.level = level;
	}

	@Override
	public boolean Execute() 
	{
		previousLevel = logger.getLevel();
		logger.setLevel(level);
		
		return true;
	}

	@Override
	public boolean Unexecute() 
	{
		logger.setLevel(previousLevel);
		
		return true;
	}

	@Override
	public String Response() 
	{
		return null;
	}

}

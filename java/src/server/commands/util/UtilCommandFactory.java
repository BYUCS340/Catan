package server.commands.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.commands.*;

/**
 * Creates util command objects.
 * @author Jonathan Sadler
 *
 */
public class UtilCommandFactory extends Factory 
{
	private static int INITIAL_SIZE = 1;
	private Map<String, ICommandDirector> directors;
	
	/**
	 * Creates a UtilCommandFactory.
	 */
	public UtilCommandFactory() 
	{
		directors = new HashMap<String, ICommandDirector>(INITIAL_SIZE);
		
		directors.put("CHANGELOGLEVEL", new ChangeLogLevelDirector());
	}

	@Override
	public ICommand GetCommand(StringBuilder param, int playerID, String object) throws InvalidFactoryParameterException
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("UtilCommandFactory", "GetCommand", e);
			throw e;
		}
		
		ICommandBuilder builder = directors.get(key).GetBuilder();
		builder.SetData(object);
		return builder.BuildCommand();
	}
	
	private class ChangeLogLevelDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new ChangeLogLevelBuilder();
		}
	}
	
	private class ChangeLogLevelBuilder implements ICommandBuilder
	{
		private Level level;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new UtilChangeLogLevelCommand(level);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub	
		}
	}
}

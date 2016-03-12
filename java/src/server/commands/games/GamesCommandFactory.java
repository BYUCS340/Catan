package server.commands.games;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.*;

/**
 * Creates games (notice the s) command objects.
 * @author Jonathan Sadler
 *
 */
public class GamesCommandFactory extends Factory 
{
	private Map<String, ICommandDirector> directors;
	
	/**
	 * Creates a GamesCommandFactory.
	 */
	public GamesCommandFactory() 
	{
		directors = new HashMap<String, ICommandDirector>(5);
		
		directors.put("CREATE", new CreateDirector());
		directors.put("JOIN", new JoinDirector());
		directors.put("LIST", new ListDirector());
		directors.put("LOAD", new LoadDirector());
		directors.put("SAVE", new SaveDirector());
	}

	@Override
	public ICommand GetCommand(StringBuilder param, int playerID, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("GamesCommandFactory", "GetCommand", e);
			throw e;
		}
		
		ICommandBuilder builder = directors.get(key).GetBuilder();
		builder.SetData(object);
		return builder.BuildCommand();
	}
	
	private class CreateDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new CreateBuilder();
		}
	}
	
	private class JoinDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new JoinBuilder();
		}
	}
	
	private class ListDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new ListBuilder();
		}
	}
	
	private class LoadDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new LoadBuilder();
		}
	}
	
	private class SaveDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new SaveBuilder();
		}
	}
	
	private class CreateBuilder implements ICommandBuilder
	{
		private boolean randomTiles;
		private boolean randomNumbers;
		private boolean randomPorts;
		private String name;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GamesCreateCommand(randomTiles, randomNumbers, randomPorts, name);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class JoinBuilder implements ICommandBuilder
	{
		private int id;
		private String color;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GamesJoinCommand(id, color);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class ListBuilder implements ICommandBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new GamesListCommand();
		}

		@Override
		public void SetData(String object) 
		{
			return;
		}
	}
	
	private class LoadBuilder implements ICommandBuilder
	{
		private String name;
		
		@Override
		public ICommand BuildCommand()
		{
			return new GamesLoadCommand(name);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class SaveBuilder implements ICommandBuilder
	{
		private int id;
		private String name;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GamesSaveCommand(id, name);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
}

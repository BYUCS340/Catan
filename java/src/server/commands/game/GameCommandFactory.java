package server.commands.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.CookieBuilder;
import server.commands.Factory;
import server.commands.ICommand;
import server.commands.ICommandBuilder;
import server.commands.ICommandDirector;
import server.commands.InvalidFactoryParameterException;
import shared.definitions.AIType;
import shared.networking.GSONUtils;
import shared.networking.parameter.PAddAI;

/**
 * Creates game command objects.
 * @author Jonathan Sadler and Parker Ridd
 *
 */
public class GameCommandFactory extends Factory 
{
	private Map<String, ICommandDirector> directors;
	/**
	 * Creates a GameCommandFactory.
	 */
	public GameCommandFactory()
	{	
		directors = new HashMap<String, ICommandDirector>(5);
		
		directors.put("ADDAI", new AddAIDirector());
		directors.put("COMMANDS", new CommandsDirector());
		directors.put("LISTAI", new ListAIDirector());
		directors.put("MODEL", new ModelDirector());
		directors.put("RESET", new ResetDirector());
	}

	@Override
	public ICommand GetCommand(StringBuilder param, int playerID, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("GameCommandFactory", "GetCommand", e);
			throw e;
		}
		
		CookieBuilder builder = (CookieBuilder)directors.get(key).GetBuilder();
		builder.SetData(object);
		builder.SetPlayerData(playerID);
		return builder.BuildCommand();
	}
	
	private class AddAIDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new AddAIBuilder();
		}
	}
	
	private class CommandsDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new CommandsBuilder();
		}
	}
	
	private class ListAIDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new ListAIBuilder();
		}
	}
	
	private class ModelDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new ModelBuilder();
		}
	}
	
	private class ResetDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new ResetBuilder();
		}
	}

	private class AddAIBuilder extends CookieBuilder
	{
		private AIType type;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GameAddAICommand(playerID, type);
		}

		@Override
		public void SetData(String object) 
		{
			PAddAI addai = GSONUtils.deserialize(object, PAddAI.class);
			type = addai.getAiType();			
		}
	}
	
	private class CommandsBuilder extends CookieBuilder
	{
		private List<ICommand> commands;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GameCommandsCommand(playerID, commands);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			//Acts as both get and post.
		}
	}
	
	private class ListAIBuilder extends CookieBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new GameListAICommand(playerID);
		}

		@Override
		public void SetData(String object) 
		{
			return;
		}
	}
	
	private class ModelBuilder extends CookieBuilder
	{
		private int version;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GameModelCommand(playerID, version);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class ResetBuilder extends CookieBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new GameResetCommand(playerID);
		}

		@Override
		public void SetData(String object) 
		{
			return;
		}
	}
}

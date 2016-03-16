package server.commands.games;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.*;
import shared.definitions.CatanColor;
import shared.networking.GSONUtils;
import shared.networking.cookie.NetworkCookie;
import shared.networking.parameter.PCreateGame;
import shared.networking.parameter.PJoinGame;

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
	public ICommand GetCommand(StringBuilder param, NetworkCookie cookie, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("GamesCommandFactory", "GetCommand", e);
			throw e;
		}
		
		CookieBuilder builder = (CookieBuilder)directors.get(key).GetBuilder();
		builder.SetData(object);
		builder.SetCookie(cookie);
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
	
	private class CreateBuilder extends CookieBuilder
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
			PCreateGame create = GSONUtils.deserialize(object, PCreateGame.class);
			randomTiles = create.isRandomTiles();
			randomNumbers = create.isRandomNumbers();
			randomPorts = create.isRandomPorts();
			name = create.getName();
		}
	}
	
	private class JoinBuilder extends CookieBuilder
	{
		private int gameID;
		private CatanColor color;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new GamesJoinCommand(cookie, gameID, color);
		}

		@Override
		public void SetData(String object) 
		{
			PJoinGame join = GSONUtils.deserialize(object, PJoinGame.class);
			gameID = join.getId();
			color = join.getColor();
		}
	}
	
	private class ListBuilder extends CookieBuilder
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
	
	private class LoadBuilder extends CookieBuilder
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
	
	private class SaveBuilder extends CookieBuilder
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

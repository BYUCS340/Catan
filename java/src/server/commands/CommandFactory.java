package server.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.game.GameCommandFactory;
import server.commands.games.GamesCommandFactory;
import server.commands.moves.MovesCommandFactory;
import server.commands.user.UserCommandFactory;
import server.commands.util.UtilCommandFactory;

/**
 * The command factory generates command objects necessary for performing operations.
 * @author Jonathan Sadler
 *
 */
public class CommandFactory extends Factory
{
	private static CommandFactory factory = null;
	
	/**
	 * Gets the command factory.
	 * @return The command factory.
	 */
	public static CommandFactory GetCommandFactory()
	{
		if (factory == null)
			factory = new CommandFactory();
		
		return factory;
	}
	
	private Map<String, Factory> factories;
	
	private CommandFactory() 
	{
		factories = new HashMap<String, Factory>(5);
		
		factories.put("GAME", new GameCommandFactory());
		factories.put("GAMES", new GamesCommandFactory());
		factories.put("MOVES", new MovesCommandFactory());
		factories.put("USER", new UserCommandFactory());
		factories.put("UTIL", new UtilCommandFactory());
	}

	@Override
	public ICommand GetCommand(StringBuilder param, int playerID, int gameID, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!factories.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("CommandFactory", "GetCommand", e);
			throw e;
		}
		
		return factories.get(key).GetCommand(param, playerID, gameID, object);
	}
}

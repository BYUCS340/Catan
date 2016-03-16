package server.commands.game;

import java.util.List;

import server.ai.AIHandler;
import server.commands.CookieCommand;
import server.model.GameArcade;
import shared.networking.GSONUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object that returns a list of available AI
 * @author Jonathan Sadler
 *
 */
public class GameListAICommand extends CookieCommand 
{
	List<String> types;
	
	/**
	 * Creates a command to get the available AI list.
	 * @param playerID The player ID.
	 */
	public GameListAICommand(NetworkCookie cookie) 
	{
		super(cookie);
	}

	@Override
	public boolean Execute() 
	{
		types = GameArcade.games().ListAI();
		return true;
	}

	@Override
	public boolean Unexecute() 
	{
		return true;
	}

	@Override
	public String GetResponse() 
	{
		return GSONUtils.serialize(types);
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}

}

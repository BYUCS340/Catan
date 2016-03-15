package server.commands.game;

import server.commands.CookieCommand;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object that returns a list of available AI
 * @author Jonathan Sadler
 *
 */
public class GameListAICommand extends CookieCommand 
{
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
	public String GetResponse() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}

}

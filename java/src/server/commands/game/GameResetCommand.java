package server.commands.game;

import server.commands.CookieCommand;
import shared.networking.cookie.NetworkCookie;

/**
 * Command to reset the command history of the current game.
 * @author Jonathan Sadler
 *
 */
public class GameResetCommand extends CookieCommand
{
	/**
	 * Creates a command that allows the game to be reset.
	 * @param playerID The player ID executing the reset.
	 */
	public GameResetCommand(NetworkCookie cookie) 
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

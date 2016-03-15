package server.commands.game;

import server.commands.CookieCommand;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object that gets game models from the server.
 * @author Jonathan Sadler
 *
 */
public class GameModelCommand extends CookieCommand
{
	private int version;
	
	/**
	 * Gets a game model from the server.
	 * @param version The version number as had by the client.
	 */
	public GameModelCommand(NetworkCookie cookie, int version) 
	{
		super(cookie);
		this.version = version;
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

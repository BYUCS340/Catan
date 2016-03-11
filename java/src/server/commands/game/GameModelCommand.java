package server.commands.game;

import server.commands.CookieCommand;

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
	public GameModelCommand(int playerID, int version) 
	{
		super(playerID);
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
	public String Response() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}

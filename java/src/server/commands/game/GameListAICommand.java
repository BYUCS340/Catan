package server.commands.game;

import server.commands.CookieCommand;

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
	public GameListAICommand(int playerID) 
	{
		super(playerID);
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

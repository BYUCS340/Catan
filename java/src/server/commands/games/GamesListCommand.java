package server.commands.games;

import server.commands.ICommand;

/**
 * Command to get all the games from the server.
 * @author Jonathan Sadler
 *
 */
public class GamesListCommand implements ICommand 
{
	/**
	 * Creates a command to get a list of games.
	 */
	public GamesListCommand()
	{
		return;
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
		return true;
	}

	@Override
	public String Response() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}

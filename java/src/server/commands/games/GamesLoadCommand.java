package server.commands.games;

import server.commands.ICommand;

/**
 * Command that handles loading games that have been previously saved.
 * @author Jonathan Sadler
 *
 */
public class GamesLoadCommand implements ICommand 
{
	private String name;
	
	/**
	 * Creates a command object to load a game.
	 * @param name The name of the saved game file.
	 */
	public GamesLoadCommand(String name)
	{
		this.name = name;
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

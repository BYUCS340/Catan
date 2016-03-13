package server.commands.games;

import server.commands.ICommand;

/**
 * Command to handle joining an existing game.
 * @author Jonathan Sadler
 *
 */
public class GamesJoinCommand implements ICommand 
{
	private int id;
	private String color;
	
	/**
	 * Creates a command to join a game.
	 * @param id The ID of the game to join.
	 * @param color The desired piece color.
	 */
	public GamesJoinCommand(int id, String color) 
	{
		this.id = id;
		this.color = color;
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

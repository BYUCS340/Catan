package server.commands.game;

import java.util.List;

import server.commands.*;

/**
 * Command class that allows commands to be executed on the server. Seems sketchy.
 * Requires user to be logged into a game.
 * @author Jonathan Sadler
 *
 */
public class GameCommandsCommand extends CookieCommand 
{
	private List<ICommand> commands;
	
	/**
	 * Creates a command to allow the execution of commands.
	 * @param playerID The ID of the player.
	 * @param commands The command list.
	 */
	public GameCommandsCommand(int playerID, List<ICommand> commands) 
	{
		super(playerID);
		this.commands = commands;
	}

	@Override
	public boolean Execute()
	{
		// TODO Auto-generated method stub
		// This is going to act as both get and post. If commands = null, assume get. Else, post.
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

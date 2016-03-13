package server.commands.game;

import server.commands.CookieCommand;

/**
 * Command object that adds an AI to a game.
 * @author Jonathan Sadler
 *
 */
public class GameAddAICommand extends CookieCommand 
{
	private String type;
	
	/**
	 * Creates a command to add a certain type of AI to the game.
	 * @param playerID The ID of the player.
	 * @param type The type of AI to add.
	 */
	public GameAddAICommand(int playerID, String type) 
	{
		super(playerID);
		this.type = type;
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

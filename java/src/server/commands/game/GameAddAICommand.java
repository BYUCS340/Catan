package server.commands.game;

import server.commands.CookieCommand;
import server.model.GameArcade;
import shared.definitions.AIType;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object that adds an AI to a game.
 * @author Jonathan Sadler
 *
 */
public class GameAddAICommand extends CookieCommand 
{
	private AIType type;
	
	/**
	 * Creates a command to add a certain type of AI to the game.
	 * @param playerID The ID of the player.
	 * @param type The type of AI to add.
	 */
	public GameAddAICommand(NetworkCookie cookie, AIType type) 
	{
		super(cookie);
		this.type = type;
	}

	@Override
	public boolean Execute() 
	{
		return GameArcade.games().AddAI(playerID, gameID, type);
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
		return null;
	}

	@Override
	public String GetHeader()
	{
		return null;
	}
}

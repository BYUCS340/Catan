package server.commands.game;

import server.Log;
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
	private static final long serialVersionUID = -4948411720898203317L;
	
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
		Log.GetLog().fine("Adding AI");
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
		return "success";
	}

	@Override
	public String GetHeader()
	{
		return null;
	}
}

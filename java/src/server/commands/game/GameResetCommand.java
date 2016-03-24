package server.commands.game;

import server.commands.CookieCommand;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.GameModel;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command to reset the command history of the current game.
 * @author Jonathan Sadler
 *
 */
public class GameResetCommand extends CookieCommand
{
	private GameModel gm;
	/**
	 * Creates a command that allows the game to be reset.
	 * @param playerID The player ID executing the reset.
	 */
	public GameResetCommand(NetworkCookie cookie) 
	{
		super(cookie);
	}

	@Override
	public boolean Execute() 
	{
		// TODO Auto-generated method stub
		try 
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			
			sgm.reset();
			gm = sgm.ServerGetModel();
			return true;
			
		}
		catch (GameException e) 
		{ //game not found
			e.printStackTrace();
		}
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
		if (gm != null) 
			return SerializationUtils.serialize(gm);
		return "ERROR";
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

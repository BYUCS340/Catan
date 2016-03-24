package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.GameModel;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command class that handles playing of monument card.
 * @author Jonathan Sadler
 *
 */
public class MovesMonumentCommand extends MovesCommand 
{
	private GameModel gm;
	
	/**
	 * Creates a command object to play the monument card.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 */
	public MovesMonumentCommand(NetworkCookie cookie, int playerIndex) 
	{
		super(cookie, playerIndex);
	}

	@Override
	public boolean Execute()
	{
		try
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			sgm.ServerMonument(playerID);
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

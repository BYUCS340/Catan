package server.commands.moves;

import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.GameModel;

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
			return sgm.ServerMonument(playerIndex);
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

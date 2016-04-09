package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Class that handles finishing a turn.
 * @author Jonathan Sadler
 *
 */
public class MovesFinishTurnCommand extends MovesCommand 
{
	private static final long serialVersionUID = -475612965248775620L;

	transient private ServerGameManager sgm;
	
	/**
	 * Creates a command object that finishes a player's turn.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 */
	public MovesFinishTurnCommand(NetworkCookie cookie, int playerIndex) 
	{
		super(cookie, playerIndex);
	}

	@Override
	public boolean Execute() 
	{
		// TODO Auto-generated method stub
		
		try 
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerFinishTurn(this.playerID);
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
		// TODO Auto-generated method stub
		if (sgm != null) 
			return SerializationUtils.serialize(sgm.ServerGetSerializableModel());
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

package server.commands.moves;

import shared.networking.cookie.NetworkCookie;

/**
 * Class that handles finishing a turn.
 * @author Jonathan Sadler
 *
 */
public class MovesFinishTurnCommand extends MovesCommand 
{
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

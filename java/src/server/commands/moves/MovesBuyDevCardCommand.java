package server.commands.moves;

import shared.networking.cookie.NetworkCookie;

/**
 * Command class that allows Dev cards to be bought.
 * @author Jonathan Sadler
 *
 */
public class MovesBuyDevCardCommand extends MovesCommand 
{
	/**
	 * Creates a command object that buys a Dev card.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 */
	public MovesBuyDevCardCommand(NetworkCookie cookie, int playerIndex) 
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

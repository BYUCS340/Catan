package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command that handles accepting of trades.
 * @author Jonathan Sadler
 *
 */
public class MovesAcceptTradeCommand extends MovesCommand 
{
	private ServerGameManager sgm;
	private boolean willAccept;
	
	/**
	 * Creates a command to accept the trade.
	 * @param cookie The ID of the player who is accepting/game ID
	 * @param playerIndex The index of the player who is accepting.
	 * @param willAccept True if they accept, else false.
	 */
	public MovesAcceptTradeCommand(NetworkCookie cookie, int playerIndex, boolean willAccept)
	{
		super(cookie, playerIndex);
		this.willAccept = willAccept;
	}

	@Override
	public boolean Execute() 
	{
		try
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerAcceptTrade(this.playerIndex, this.willAccept);
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

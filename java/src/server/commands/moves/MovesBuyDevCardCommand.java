package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command class that allows Dev cards to be bought.
 * @author Jonathan Sadler
 *
 */
public class MovesBuyDevCardCommand extends MovesCommand 
{
	private static final long serialVersionUID = -2312144013203230759L;

	transient private ServerGameManager sgm;

	/**
	 * Creates a command object that buys a Dev card.
	 * @param cookie The player ID/game ID
	 * @param playerIndex The player index.
	 */
	public MovesBuyDevCardCommand(NetworkCookie cookie, int playerIndex) 
	{
		super(cookie, playerIndex);
	}

	@Override
	public boolean Execute() 
	{
		try
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerBuyDevCard(this.playerID);
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

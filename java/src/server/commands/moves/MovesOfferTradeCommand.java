package server.commands.moves;

import java.util.List;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command that handles offering of trades to amigos.
 * @author Jonathan Sadler
 *
 */
public class MovesOfferTradeCommand extends MovesCommand 
{
	private static final long serialVersionUID = 9117894924760177797L;

	transient private ServerGameManager sgm;
	
	private int receiverIndex;
	private List<Integer> offer;
	
	/**
	 * Creates a command that offers a trade.
	 * @param cookie The player ID/game ID
	 * @param playerIndex The player index.
	 * @param receiverIndex The index of the receiver.
	 * @param offer The resources being offered. (brick, ore, sheep, wheat, wood; + is what is offered by 
	 * the player, - is what the player is hoping to receive)
	 */
	public MovesOfferTradeCommand(NetworkCookie cookie, int playerIndex, int receiverIndex, List<Integer> offer)
	{
		super(cookie, playerIndex);
		this.receiverIndex = receiverIndex;
		this.offer = offer;
	}

	@Override
	public boolean Execute() 
	{
		try
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerOfferTrade(this.playerIndex, this.receiverIndex, this.offer);
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

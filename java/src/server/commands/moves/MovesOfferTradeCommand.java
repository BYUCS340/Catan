package server.commands.moves;

import java.util.List;

/**
 * Command that handles offering of trades to amigos.
 * @author Jonathan Sadler
 *
 */
public class MovesOfferTradeCommand extends MovesCommand 
{
	private int receiverIndex;
	private List<Integer> offer;
	
	/**
	 * Creates a command that offers a trade.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param receiverIndex The index of the receiver.
	 * @param offer The resources being offered. (brick, ore, sheep, wheat, wood; + is what is offered by 
	 * the player, - is what the player is hoping to receive)
	 */
	public MovesOfferTradeCommand(int playerID, int playerIndex, int receiverIndex, List<Integer> offer)
	{
		super(playerID, playerIndex);
		this.receiverIndex = receiverIndex;
		this.offer = offer;
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
	public String Response()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

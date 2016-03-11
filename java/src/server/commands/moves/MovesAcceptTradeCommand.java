package server.commands.moves;

/**
 * Command that handles accepting of trades.
 * @author Jonathan Sadler
 *
 */
public class MovesAcceptTradeCommand extends MovesCommand 
{
	private boolean willAccept;
	
	/**
	 * Creates a command to accept the trade.
	 * @param playerID The ID of the player who is accepting.
	 * @param playerIndex The index of the player who is accepting.
	 * @param willAccept True if they accept, else false.
	 */
	public MovesAcceptTradeCommand(int playerID, int playerIndex, boolean willAccept)
	{
		super(playerID, playerIndex);
		this.willAccept = willAccept;
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

package server.commands.moves;

import shared.definitions.ResourceType;

/**
 * Command that handles maritime trade.
 * @author Jonathan Sadler and Parker Ridd
 *
 */
public class MovesMaritimeTradeCommand extends MovesCommand 
{
	private int ratio;
	private ResourceType input;
	private ResourceType output;
	
	/**
	 * Creates a command to perform a maritime trade.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param ratio The ratio of the trade (4:1, 3:1, 2:1).
	 * @param input The input (what is being turned in by the player).
	 * @param output The output (what is being received).
	 */
	public MovesMaritimeTradeCommand(int playerID, int playerIndex, int ratio, ResourceType input, ResourceType output)
	{
		super(playerID, playerIndex);
		this.ratio = ratio;
		this.input = input;
		this.output = output;
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

package server.commands.moves;

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
	public MovesBuyDevCardCommand(int playerID, int playerIndex) 
	{
		super(playerID, playerIndex);
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

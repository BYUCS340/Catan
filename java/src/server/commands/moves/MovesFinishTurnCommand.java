package server.commands.moves;

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
	public MovesFinishTurnCommand(int playerID, int playerIndex) 
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

package server.commands.moves;

/**
 * Command class that handles playing of monument card.
 * @author Jonathan Sadler
 *
 */
public class MovesMonumentCommand extends MovesCommand 
{
	/**
	 * Creates a command object to play the monument card.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 */
	public MovesMonumentCommand(int playerID, int playerIndex) 
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

package server.commands.moves;

/**
 * Command that deals with the rolling of dice.
 * @author Jonathan Sadler
 *
 */
public class MovesRollNumberCommand extends MovesCommand 
{
	private int roll;
	
	/**
	 * Creates a command object to handle dice rolls.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param roll The value on the dice.
	 */
	public MovesRollNumberCommand(int playerID, int playerIndex, int roll) 
	{
		super(playerID, playerIndex);
		this.roll = roll;
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

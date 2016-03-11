package server.commands.moves;


/**
 * Command object updates the water cooler.
 * @author Jonathan Sadler
 *
 */
public class MovesSendChatCommand extends MovesCommand 
{
	private String message;
	
	/**
	 * Creates a command to send a chat.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param message The message being sent.
	 */
	public MovesSendChatCommand(int playerID, int playerIndex, String message) 
	{
		super(playerID, playerIndex);
		this.playerIndex = playerIndex;
		this.message = message;
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

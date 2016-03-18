package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.GSONUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object updates the water cooler.
 * @author Jonathan Sadler
 *
 */
public class MovesSendChatCommand extends MovesCommand 
{
	private String message;
	private ServerGameManager sgm;
	/**
	 * Creates a command to send a chat.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param message The message being sent.
	 */
	public MovesSendChatCommand(NetworkCookie cookie, int playerIndex, String message) 
	{
		super(cookie, playerIndex);
		this.playerIndex = playerIndex;
		this.message = message;
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerSendChat(playerID, message);
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
			return GSONUtils.serialize(sgm.ServerGetSerializableModel());
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

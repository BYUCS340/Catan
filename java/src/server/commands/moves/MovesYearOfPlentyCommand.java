package server.commands.moves;

import shared.definitions.ResourceType;
import shared.networking.cookie.NetworkCookie;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;

/**
 * Command object that allows the playing of the Year of Plenty card.
 * @author Jonathan Sadler
 *
 */
public class MovesYearOfPlentyCommand extends MovesCommand 
{
	private ResourceType resource1;
	private ResourceType resource2;
	
	/**
	 * Creates a command object that plays the Year of Plenty card.
	 * @param playerID The player ID.
	 * @param playerIndex The Player index.
	 * @param resource1 The resource type of card 1.
	 * @param resource2 The resource type of card 2.
	 */
	public MovesYearOfPlentyCommand(NetworkCookie cookie, int playerIndex, ResourceType resource1, ResourceType resource2) 
	{
		super(cookie, playerIndex);
		this.resource1 = resource1;
		this.resource2 = resource2;
	}

	@Override
	public boolean Execute()
	{
		try
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerYearOfPlenty(playerID, resource1, resource2);
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
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

package server.commands.moves;

import shared.definitions.ResourceType;
import shared.networking.cookie.NetworkCookie;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;

/**
 * Command for handling the monopoly card.
 * @author Jonathan Sadler
 *
 */
public class MovesMonopolyCommand extends MovesCommand 
{
	private ResourceType resource;
	
	/**
	 * Creates a command object to play the monopoly card.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param resource The resource type to monopolize.
	 */
	public MovesMonopolyCommand(NetworkCookie cookie, int playerIndex, ResourceType resource) 
	{
		super(cookie, playerIndex);
		this.resource = resource;
	}

	@Override
	public boolean Execute()
	{
		try
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerMonopoly(playerID, this.resource);
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

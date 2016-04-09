package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.definitions.ResourceType;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;
import shared.model.GameModel;

/**
 * Command for handling the monopoly card.
 * @author Jonathan Sadler
 *
 */
public class MovesMonopolyCommand extends MovesCommand 
{
	private static final long serialVersionUID = 1098691127093527138L;
	
	transient private GameModel gm;
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
			return sgm.ServerMonopoly(playerIndex, resource);
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
		if (gm != null) 
			return SerializationUtils.serialize(gm);
		return "ERROR";
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

package server.commands.games;

import java.util.List;

import server.commands.ICommand;
import server.model.GameArcade;
import shared.data.GameInfo;
import shared.networking.SerializationUtils;

/**
 * Command to get all the games from the server.
 * @author Jonathan Sadler
 *
 */
public class GamesListCommand implements ICommand 
{
	private static final long serialVersionUID = 6040602245599921636L;

	private List<GameInfo> games;
	
	/**
	 * Creates a command to get a list of games.
	 */
	public GamesListCommand()
	{
		return;
	}

	@Override
	public boolean Execute() 
	{
		games = GameArcade.games().GetAllGames();
		return true;
	}

	@Override
	public boolean Unexecute() 
	{
		return true;
	}

	@Override
	public String GetResponse() 
	{
		return SerializationUtils.serialize(games);
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

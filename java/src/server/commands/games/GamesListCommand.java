package server.commands.games;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import server.commands.ICommand;
import server.model.GameArcade;
import shared.data.GameInfo;
import shared.networking.GSONUtils;

/**
 * Command to get all the games from the server.
 * @author Jonathan Sadler
 *
 */
public class GamesListCommand implements ICommand 
{
	/**
	 * Creates a command to get a list of games.
	 */
	private List<GameInfo> games;
	public GamesListCommand()
	{
		
	}

	@Override
	public boolean Execute() 
	{
		// TODO Auto-generated method stub
		games = GameArcade.games().GetAllGames();
		return true;
	}

	@Override
	public boolean Unexecute() 
	{
		return true;
	}

	@Override
	public String Response() 
	{
		System.out.println(games);
		return GSONUtils.serialize(games);
	}

}

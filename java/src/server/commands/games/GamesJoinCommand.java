package server.commands.games;

import server.commands.ICommand;
import server.model.GameArcade;
import shared.definitions.CatanColor;
import shared.networking.GSONUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command to handle joining an existing game.
 * @author Jonathan Sadler
 *
 */
public class GamesJoinCommand implements ICommand 
{
	private NetworkCookie cookie;
	private int gameID;
	private CatanColor color;
	
	/**
	 * Creates a command to join a game.
	 * @param playerID The id of the joining player.
	 * @param gameId The ID of the game to join.
	 * @param color The desired piece color.
	 */
	public GamesJoinCommand(NetworkCookie cookie, int gameID, CatanColor color) 
	{
		this.cookie = cookie;
		this.gameID = gameID;
		this.color = color;
	}

	@Override
	public boolean Execute() 
	{
		boolean result = GameArcade.games().JoinGame(cookie.getPlayerID(), gameID, color);
		
		if (result)
			cookie.setGameID(gameID);
		
		return result;
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
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return GSONUtils.serialize(cookie);
	}
}

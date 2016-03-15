package server.commands.games;

import server.commands.ICommand;
import server.model.GameArcade;
import shared.definitions.CatanColor;

/**
 * Command to handle joining an existing game.
 * @author Jonathan Sadler
 *
 */
public class GamesJoinCommand implements ICommand 
{
	private int playerID;
	private int gameID;
	private CatanColor color;
	
	/**
	 * Creates a command to join a game.
	 * @param playerID The id of the joining player.
	 * @param gameId The ID of the game to join.
	 * @param color The desired piece color.
	 */
	public GamesJoinCommand(int playerID, int gameID, CatanColor color) 
	{
		this.playerID = playerID;
		this.gameID = gameID;
		this.color = color;
	}

	@Override
	public boolean Execute() 
	{
		GameArcade.games().JoinGame(playerID, gameID, color);
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

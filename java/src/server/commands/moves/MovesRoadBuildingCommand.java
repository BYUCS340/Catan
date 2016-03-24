package server.commands.moves;

import shared.model.map.Coordinate;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object that handles the road building card.
 * @author Jonathan Sadler
 *
 */
public class MovesRoadBuildingCommand extends MovesCommand 
{
	private Coordinate start1;
	private Coordinate end1;
	private Coordinate start2;
	private Coordinate end2;
	
	/**
	 * Creates a command object for building roads.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param start1 The start of the first road.
	 * @param end1 The end of the first road.
	 * @param start2 The start of the second road.
	 * @param end2 The end of the second road.
	 */
	public MovesRoadBuildingCommand(NetworkCookie cookie, int playerIndex, 
			Coordinate start1, Coordinate end1, Coordinate start2, Coordinate end2)
	{
		super(cookie, playerIndex);
		this.start1 = start1;
		this.end1 = end1;
		this.start2 = start2;
		this.end2 = end2;
	}

	@Override
	public boolean Execute() 
	{
		try
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerRoadBuilding(playerIndex, start1, end1, start2, end2);
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

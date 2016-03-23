package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.GameModel;
import shared.model.map.Coordinate;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command class that handles building roads.
 * @author Jonathan Sadler
 *
 */
public class MovesBuildRoadCommand extends MovesCommand
{
	private Coordinate start;
	private Coordinate end;
	private boolean free;
	private GameModel gm;
	
	/**
	 * Creates a command object that builds a road.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param start The start of the road.
	 * @param end The end of the road.
	 * @param free True if free, else false. (Free for setup).
	 */
	public MovesBuildRoadCommand(NetworkCookie cookie, int playerIndex, Coordinate start, Coordinate end, boolean free)
	{
		super(cookie, playerIndex);
		this.start = start;
		this.end = end;
		this.free = free;
	}

	@Override
	public boolean Execute()
	{
		try 
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			if (sgm.ServerBuildRoad(playerID, start, end, free))
			{
				gm = sgm.ServerGetModel();
				return true;
			}
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

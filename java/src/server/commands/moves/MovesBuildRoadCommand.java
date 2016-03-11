package server.commands.moves;

import shared.model.map.Coordinate;

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
	
	/**
	 * Creates a command object that builds a road.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param start The start of the road.
	 * @param end The end of the road.
	 * @param free True if free, else false. (Free for setup).
	 */
	public MovesBuildRoadCommand(int playerID, int playerIndex, Coordinate start, Coordinate end, boolean free)
	{
		super(playerID, playerIndex);
		this.start = start;
		this.end = end;
		this.free = free;
	}

	@Override
	public boolean Execute()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String Response()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

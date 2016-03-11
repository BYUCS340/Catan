package server.commands.moves;

import shared.model.map.Coordinate;

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
	public MovesRoadBuildingCommand(int playerID, int playerIndex, 
			Coordinate start1, Coordinate end1, Coordinate start2, Coordinate end2)
	{
		super(playerID, playerIndex);
		this.start1 = start1;
		this.end1 = end1;
		this.start2 = start2;
		this.end2 = end2;
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

package server.commands.moves;

import shared.model.map.Coordinate;

/**
 * Command that handles the building up of cities.
 * @author Jonathan Sadler
 *
 */
public class MovesBuildCityCommand extends MovesCommand 
{
	private Coordinate point;
	
	/**
	 * Creates a command that builds a city.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param point The point to build the city at.
	 */
	public MovesBuildCityCommand(int playerID, int playerIndex, Coordinate point)
	{
		super(playerID, playerIndex);
		this.point = point;
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

package server.commands.moves;

import shared.model.map.Coordinate;

/**
 * Command object that handles playing of the soldier card.
 * @author Jonathan Sadler
 *
 */
public class MovesSoldierCommand extends MovesCommand 
{
	private int victimIndex;
	private Coordinate point;
	
	/**
	 * Creates a command object to place a soldier card.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param victimIndex The victim index.
	 * @param point The coordinate to place the robber at.
	 */
	public MovesSoldierCommand(int playerID, int playerIndex, int victimIndex, Coordinate point) 
	{
		super(playerID, playerIndex);
		this.victimIndex = victimIndex;
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

package client.map.view.mapState;

import shared.definitions.PieceType;

/**
 * Creates a normal state. Normal state is when any object (including no object) is 
 * being placed during normal game play. 
 * @author Jonathan Sadler
 *
 */
public class NormalState implements IMapState
{
	private PieceType type;
	
	/**
	 * Creates a normal state object.
	 * @param type The type of piece to place.
	 */
	public NormalState(PieceType type)
	{
		this.type = type;
	}
	
	@Override
	public PieceType GetPieceType()
	{
		return type;
	}

	@Override
	public IMapState GetNextMapState()
	{
		return new NormalState(PieceType.NONE);
	}

	@Override
	public Boolean IsSetup()
	{
		return false;
	}
	
	@Override
	public Boolean AllowCancel()
	{
		if (type == PieceType.ROBBER)
			return false;
		else
			return true;
	}
}

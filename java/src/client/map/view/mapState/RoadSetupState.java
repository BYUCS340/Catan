package client.map.view.mapState;

import shared.definitions.PieceType;

/**
 * Road setup state is during the intial setup phase of the game when a road is being
 * placed.
 * @author Jonathan Sadler
 *
 */
public class RoadSetupState implements IMapState
{
	@Override
	public PieceType GetPieceType()
	{
		return PieceType.ROAD;
	}

	@Override
	public IMapState GetNextMapState()
	{
		return new NormalState(PieceType.NONE);
	}

	@Override
	public Boolean IsSetup()
	{
		return true;
	}

	@Override
	public Boolean AllowCancel()
	{
		return false;
	}
}

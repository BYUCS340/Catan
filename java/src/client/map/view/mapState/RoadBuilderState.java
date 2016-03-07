package client.map.view.mapState;

import shared.definitions.PieceType;

public class RoadBuilderState implements IMapState
{
	@Override
	public PieceType GetPieceType()
	{
		return PieceType.ROAD;
	}

	@Override
	public IMapState GetNextMapState()
	{
		return new RoadBuilderState();
	}

	@Override
	public Boolean IsSetup()
	{
		return false;
	}

	@Override
	public Boolean AllowCancel()
	{
		return false;
	}
}

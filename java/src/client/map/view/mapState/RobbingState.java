package client.map.view.mapState;

import shared.definitions.PieceType;

public class RobbingState implements IMapState
{
	
	public RobbingState()
	{
		
	}
	
	@Override
	public PieceType GetPieceType()
	{
		return PieceType.ROBBER;
	}

	@Override
	public IMapState GetNextMapState()
	{
		return this;
	}

	@Override
	public Boolean IsSetup()
	{
		return true;
	}
}

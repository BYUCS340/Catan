package client.map.view.mapState;

import shared.definitions.PieceType;

public class SettlementSetupState implements IMapState
{
	@Override
	public PieceType GetPieceType()
	{
		return PieceType.SETTLEMENT;
	}

	@Override
	public IMapState GetNextMapState()
	{
		return new RoadSetupState();
	}

	@Override
	public Boolean IsSetup()
	{
		return true;
	}
	

}

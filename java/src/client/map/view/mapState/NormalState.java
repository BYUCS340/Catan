package client.map.view.mapState;

import shared.definitions.PieceType;

public class NormalState implements IMapState
{
	private PieceType type;
	
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
		return this;
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

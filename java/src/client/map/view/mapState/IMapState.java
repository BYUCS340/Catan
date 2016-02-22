package client.map.view.mapState;

import shared.definitions.PieceType;

public interface IMapState
{
	public PieceType GetPieceType();
	
	public IMapState GetNextMapState();
	
	public Boolean IsSetup();
}

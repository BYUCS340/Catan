package client.map.view.mapState;

import shared.definitions.PieceType;

/**
 * Road builder state is associated with the road builder card. It allows for consecutive
 * placement of roads.
 * @author Matthew Carlson
 *
 */
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

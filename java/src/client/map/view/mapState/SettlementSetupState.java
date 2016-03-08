package client.map.view.mapState;

import shared.definitions.PieceType;

/**
 * Settlement setup is used when settlements are being placed during the setup phase
 * of the game.
 * @author Jonathan Sadler
 *
 */
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
	
	@Override
	public Boolean AllowCancel()
	{
		return false;
	}
}

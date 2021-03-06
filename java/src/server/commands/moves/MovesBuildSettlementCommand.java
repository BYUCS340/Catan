package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.map.Coordinate;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command that handles the building of settlements.
 * @author Jonathan Sadler
 *
 */
public class MovesBuildSettlementCommand extends MovesCommand
{
	private static final long serialVersionUID = 2614637999252976183L;

	transient private ServerGameManager sgm;
	private Coordinate point;
	private boolean free;
	
	/**
	 * Creates a command the builds a settlement.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param point The point to build the settlement.
	 * @param free True if free, else false. (Used during setup).
	 */
	public MovesBuildSettlementCommand(NetworkCookie cookie, int playerIndex, Coordinate point, boolean free)
	{
		super(cookie, playerIndex);
		this.point = point;
		this.free = free;
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerBuildSettlement(playerIndex, point,free);
		}
		catch (GameException e) 
		{ //game not found
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetResponse() 
	{
		if (sgm != null) 
			return SerializationUtils.serialize(sgm.ServerGetSerializableModel());
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

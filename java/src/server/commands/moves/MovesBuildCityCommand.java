package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.map.Coordinate;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command that handles the building up of cities.
 * @author Jonathan Sadler
 *
 */
public class MovesBuildCityCommand extends MovesCommand 
{
	private Coordinate point;
	private ServerGameManager sgm;


	/**
	 * Creates a command that builds a city.
	 * @param cookie The player ID/game ID
	 * @param playerIndex The player index.
	 * @param point The point to build the city at.
	 */
	public MovesBuildCityCommand(NetworkCookie cookie, int playerIndex, Coordinate point)
	{
		super(cookie, playerIndex);
		this.point = point;
	}

	@Override
	public boolean Execute() 
	{
		try
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerBuildCity(this.playerIndex, this.point);
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

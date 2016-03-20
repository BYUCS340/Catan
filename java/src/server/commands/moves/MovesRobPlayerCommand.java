package server.commands.moves;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.map.Coordinate;
import shared.networking.GSONUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Class that handles robbing a player.
 * @author Jonathan Sadler
 *
 */
public class MovesRobPlayerCommand extends MovesCommand 
{
	private int victimIndex;
	private Coordinate point;
	private ServerGameManager sgm;
	
	/**
	 * Creates a command class that robs a player.
	 * @param playerID The player ID.
	 * @param playerIndex The index of the player.
	 * @param victimIndex The index of the victim.
	 * @param point The hex coordinate to place the robber.
	 */
	public MovesRobPlayerCommand(NetworkCookie cookie, int playerIndex, int victimIndex, Coordinate point) 
	{
		super(cookie, playerIndex);
		this.victimIndex = victimIndex;
		this.point = point;
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerRobPlayer(playerIndex, victimIndex, point);
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
		if(sgm != null)
		{
			return GSONUtils.serialize(sgm.ServerGetSerializableModel());
		}
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

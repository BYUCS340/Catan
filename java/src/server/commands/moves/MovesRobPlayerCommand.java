package server.commands.moves;

import shared.model.map.Coordinate;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}

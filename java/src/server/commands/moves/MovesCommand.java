package server.commands.moves;

import server.commands.CookieCommand;
import shared.networking.cookie.NetworkCookie;

public abstract class MovesCommand extends CookieCommand 
{
	protected int playerIndex;
	
	public MovesCommand(NetworkCookie cookie, int playerIndex) 
	{
		super(cookie);
		this.playerIndex = playerIndex;
	}
}

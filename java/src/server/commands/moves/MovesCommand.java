package server.commands.moves;

import server.commands.CookieCommand;
import shared.networking.cookie.NetworkCookie;

public abstract class MovesCommand extends CookieCommand 
{
	private static final long serialVersionUID = -7039269162067245299L;

	protected int playerIndex;
	
	public MovesCommand(NetworkCookie cookie, int playerIndex) 
	{
		super(cookie);
		this.playerIndex = playerIndex;
	}
}

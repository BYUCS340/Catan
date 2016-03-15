package server.commands;

import shared.networking.cookie.NetworkCookie;

public abstract class CookieCommand implements ICommand 
{
	protected int playerID;
	protected int gameID;
	
	protected CookieCommand(NetworkCookie cookie)
	{
		this.playerID = cookie.getPlayerID();
		this.gameID = cookie.getGameID();
	}
}

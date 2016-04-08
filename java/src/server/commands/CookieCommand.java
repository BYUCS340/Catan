package server.commands;

import shared.networking.cookie.NetworkCookie;

public abstract class CookieCommand implements ICommand 
{
	private static final long serialVersionUID = -1456911120280719029L;

	protected int playerID;
	protected int gameID;
	
	protected CookieCommand(NetworkCookie cookie)
	{
		this.playerID = cookie.getPlayerID();
		this.gameID = cookie.getGameID();
	}
	
	public int GetPlayerID()
	{
		return playerID;
	}
	
	public int GetGameID()
	{
		return gameID;
	}
}

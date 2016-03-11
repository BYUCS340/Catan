package server.cookie;

import shared.networking.Cookie;

public class ServerCookie implements Cookie 
{
	private long cookieID = 0;
	private int  playerID = 0;
	private int  gameID = 0;
	private long created;
	private final long minutesValid = 180;
	
	public ServerCookie(int playerid)
	{
		this.playerID = playerid;
		created = System.currentTimeMillis();
		cookieID = (long) (Math.random() * Long.MAX_VALUE);
	}
	
	@Override
	public String getCookieText()
	{
		return String.valueOf(cookieID);
	}
	
	public int getPlayerID()
	{
		return this.playerID;
	}
	
	public boolean equals(String cookieText)
	{
		return this.getCookieText().equals(cookieText);
	}
	
	/**
	 * Checks if the cookie has expired (180 minutes)
	 * @return true or false
	 */
	public boolean isExpired()
	{
		long currentTime = System.currentTimeMillis();
		long expiredTime = this.created + (minutesValid * 1000 * 60);
		return currentTime  < expiredTime;
	}

}

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
	
	/**
	 * Checks if the cookie matches the sa,e 
	 * @param cookieText
	 * @return
	 */
	public boolean matchs(String cookieText)
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
		return currentTime  > expiredTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cookieID ^ (cookieID >>> 32));
		result = prime * result + playerID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ServerCookie))
			return false;
		ServerCookie other = (ServerCookie) obj;
		if (cookieID != other.cookieID)
			return false;
		if (playerID != other.playerID)
			return false;
		return true;
	}

}

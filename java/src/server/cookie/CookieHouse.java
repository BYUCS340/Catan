package server.cookie;

import java.util.HashMap;
import java.util.Map;

import server.model.GameException;

/**
 * Keeps track of a user's activity 
 * @author matthewcarlson
 *
 */
public class CookieHouse {

	private Map<String,ServerCookie> cookies;
	
	public CookieHouse()
	{
		cookies = new HashMap<>();
	}
	
	/**
	 * creates a new cookie for the player ID
	 * @param playerID
	 * @return
	 */
	public ServerCookie bakeCookie(int playerID)
	{
		ServerCookie newcookie = new ServerCookie(playerID);
		cookies.put(newcookie.getCookieText(), newcookie);
		return newcookie;
	}
	
	/**
	 * Gets the cookie for the cookie text
	 * @param cookieText
	 * @return the cookie 
	 * @throws GameException if the cookie isn't found or stale 
	 */
	public ServerCookie checkCookie(String cookieText) throws GameException 
	{
		//Check to see if the cookie map has this particular cookie text
		if (!cookies.containsKey(cookieText))
			throw new GameException("No cookie found");
		ServerCookie sc =  cookies.get(cookieText);
		if (sc.isExpired())
		{
			cookies.remove(cookieText);
			throw new GameException("Cookie has gone stale!");
		}
		return sc;
	}
}

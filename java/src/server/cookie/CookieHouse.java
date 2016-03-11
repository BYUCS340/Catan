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
		return null;
		
	}
	
	/**
	 * Gets the cookie for the cookie text
	 * @param cookieText
	 * @return
	 * @throws GameException 
	 */
	public ServerCookie checkCookie(String cookieText) throws GameException 
	{
		//TODO either create new exception type or return null
		throw new GameException("This cookie is bad");
	}
}

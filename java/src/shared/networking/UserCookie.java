package shared.networking;

public class UserCookie implements Cookie
{
	String cookie; 
	String username;
	String password;
	int playerID;
	
	/**
	 * Sets the cookie string
	 * @param cookie The raw cookie text
	 * @param username The username of the user
	 * @param playerID The playerID of the user
	 */
	public UserCookie(String cookie, String username, String password, int playerID){
		this.cookie = cookie;
		this.username = username;
		this.password = password;
		this.playerID = playerID;
	}

	@Override
	public String getCookieText()
	{
		return cookie;
	}

	/**
	 * @return the cookie
	 */
	public String getCookie()
	{
		return cookie;
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	

}

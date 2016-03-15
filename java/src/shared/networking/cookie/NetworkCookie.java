package shared.networking.cookie;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NetworkCookie implements Serializable
{
	private String name;
	private String password;
	private int playerID;
	private int gameID;
	
	public NetworkCookie(String name, String password, int playerID)
	{
		this.name = name;
		this.password = password;
		this.playerID = playerID;
		this.gameID = -1;
	}
	
	/**
	 * 
	 * @param gameID the gameID
	 */
	public void setGameID(int gameID)
	{
		this.gameID = gameID;
	}

	/**
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID() 
	{
		return playerID;
	}
	
	/**
	 * @return the gameID
	 */
	public int getGameID() 
	{
		return gameID;
	}
}

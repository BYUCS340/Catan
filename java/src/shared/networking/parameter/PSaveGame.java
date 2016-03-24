package shared.networking.parameter;

import java.io.Serializable;

public class PSaveGame implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8891732380278707079L;
	private int id;
	private String name;

	public PSaveGame()
	{
		
	}
	
	/**
	 * @param roll
	 */
	public PSaveGame(int gameID, String name)
	{
		super();
		this.id = gameID;
		this.name = name;
	}

	public int getGameID() {
		return id;
	}

	public void setGameID(int gameID) {
		this.id = gameID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

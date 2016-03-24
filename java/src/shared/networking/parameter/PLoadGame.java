package shared.networking.parameter;

import java.io.Serializable;

public class PLoadGame implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8891732380278707079L;
	private String name;

	public PLoadGame()
	{
		
	}
	
	/**
	 * @param roll
	 */
	public PLoadGame(int gameID, String name)
	{
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

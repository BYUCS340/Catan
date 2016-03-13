package server.model;

/**
 * Class that encaplses the player 
 * @author matthewcarlson
 *
 */
public class ServerPlayer {
	private String name;
	private int index;
	private String password;
	
		
	public ServerPlayer(String name, String password,int index)
	{
		this.name = name;
		this.password = password;
		this.index = index;
	}
	
	public String GetName()
	{
		return this.name;
	}
	
	public int GetID()
	{
		return this.index;
	}

}
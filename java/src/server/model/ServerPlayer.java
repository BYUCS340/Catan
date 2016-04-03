package server.model;

/**
 * Class that encaplses the player 
 * @author matthewcarlson
 *
 */
public class ServerPlayer 
{
	private String name;
	private int ID;
	private String password;
	
		
	public ServerPlayer(String name, String password, int id)
	{
		this.name = name;
		this.password = password;
		this.ID = id;
	}
	
	public String GetName()
	{
		return this.name;
	}
	
	public int GetID()
	{
		return this.ID;
	}
	
	/**
	 * Checks if password matches
	 * @param pass
	 * @return
	 */
	public boolean PasswordMatches(String pass)
	{
		return password.equals(pass);
	}

}
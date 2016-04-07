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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerPlayer [" + (name != null ? "name=" + name + ", " : "") + "ID=" + ID + ", "
				+ (password != null ? "password=" + password : "") + "]";
	}

}
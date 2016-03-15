package shared.networking.parameter;

import java.io.Serializable;

public class PCredentials implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4487298504220720762L;
	private String username;
	private String password;
	
	public PCredentials()
	{
		
	}
	
	/**
	 * @param username
	 * @param password
	 */
	public PCredentials(String username, String password)
	{
		super();
		this.username = username;
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

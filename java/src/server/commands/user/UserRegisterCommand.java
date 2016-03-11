package server.commands.user;

import server.commands.ICommand;

/**
 * Handles registering a user.
 * @author Jonathan Sadler
 *
 */
public class UserRegisterCommand implements ICommand 
{
	private String username;
	private String password;
	
	/**
	 * Creates a command to register a user.
	 * @param username The desired user name.
	 * @param password The desired password.
	 */
	public UserRegisterCommand(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean Execute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String Response() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}

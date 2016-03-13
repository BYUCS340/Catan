package server.commands.user;

import server.commands.ICommand;
import server.model.GameArcade;
import server.model.GameException;

/**
 * Handles logging in to the game.
 * @author Jonathan Sadler
 *
 */
public class UserLoginCommand implements ICommand 
{
	private String username;
	private String password;
	private String response;
	
	/**
	 * Creates a command to log in the user.
	 * @param username The user name.
	 * @param password The user's password.
	 */
	public UserLoginCommand(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			GameArcade.games().Login(username, password);
			response = "success";
			return true;
		} 
		catch (GameException e) 
		{
			response = "failure";
			return false;
		}
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
		return this.response;
	}

}

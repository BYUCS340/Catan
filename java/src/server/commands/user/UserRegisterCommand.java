package server.commands.user;

import server.commands.ICommand;
import server.cookie.ServerCookie;
import server.model.GameArcade;
import server.model.GameException;

/**
 * Handles registering a user.
 * @author Jonathan Sadler
 *
 */
public class UserRegisterCommand implements ICommand 
{
	private String username;
	private String password;
	private ServerCookie response = null;
	
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
		try 
		{
			response =GameArcade.games().RegisterPlayer(username, password);
			return true;
		} 
		catch (GameException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if (response == null)
		{
			return "error";
		}
		return response.getCookieText();
		
	}
}

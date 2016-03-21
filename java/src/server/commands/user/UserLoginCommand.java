package server.commands.user;

import server.commands.ICommand;
import server.model.GameArcade;
import server.model.GameException;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Handles logging in to the game.
 * @author Jonathan Sadler
 *
 */
public class UserLoginCommand implements ICommand 
{
	private String username;
	private String password;
	private int playerID;
	
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
		this.response = null;
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			playerID = GameArcade.games().Login(username, password);
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
	public String GetResponse() 
	{
		return this.response;
	}

	@Override
	public String GetHeader() 
	{
		NetworkCookie cookie = new NetworkCookie(username, password, playerID);
		return SerializationUtils.serialize(cookie);
	}
}

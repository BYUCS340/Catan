package server.commands.user;

import server.Log;
import server.commands.ICommand;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerPlayer;

/**
 * Handles registering a user.
 * @author Jonathan Sadler
 *
 */
public class UserRegisterCommand implements ICommand 
{
	private static final long serialVersionUID = 2490805469832468054L;

	private int playerID;
	private String username;
	private String password;
	
	private String response;
	
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
			playerID = GameArcade.games().RegisterPlayer(username, password);
			Log.GetLog().finer("Registered: " + username);
			response = "Success";
			return true;
		} 
		catch (GameException e) 
		{
			Log.GetLog().finer("Unable to register: " + username);
			response = "Failed to register";
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
		return response;
	}

	@Override
	public String GetHeader() 
	{
		//Response isn't necessary as it will be followed by a login command.
		return null;
	}
	
	public ServerPlayer GetPlayer()
	{
		return new ServerPlayer(username, password, playerID);
	}
}

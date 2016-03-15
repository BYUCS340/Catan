package server.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kinda like a den of theives but for players
 * @author matthewcarlson
 *
 */
public class PlayerDen 
{
	private final int MIN_USERNAME_LENGTH = 3;
	private final int MIN_PASSWORD_LENGTH = 5;
	
	private Map<Integer,ServerPlayer> players;
	private List<String> playerNames;
	private Map<String,Integer> playerLogin;
	private int numberPlayers = 1;
	
	
	public PlayerDen()
	{
		players = new HashMap<>();
		playerLogin = new HashMap<>();
		playerNames = new ArrayList<>();
		
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return -1 if not found
	 */
	public int CheckLogin(String username, String password)
	{
		if (username == null || password == null)
			return -1;
		if (username.length() < MIN_USERNAME_LENGTH || password.length() < MIN_PASSWORD_LENGTH)
			return -1;
		
		String key = username + password;
		if (!playerLogin.containsKey(key))
			return -1;
		return playerLogin.get(key);
		
	}
	
	/**
	 * Registers a new player with a password
	 * @param username
	 * @param password
	 * @return the id of the new player
	 * @throws GameException if username is in use
	 */
	public int RegisterPlayer(String username, String password) throws GameException
	{
		if (username == null || password == null)
			throw new GameException("Invalid username/password");
		if (username.length() < MIN_USERNAME_LENGTH || password.length() < MIN_PASSWORD_LENGTH)
			throw new GameException("Username or password too short");
		
		//Check to make sure the player isn't already registered
		if (playerNames.contains(username))
			throw new GameException("This username is already in use");
		playerNames.add(username);
		String key = username + password;
		
		int index = numberPlayers++;
		ServerPlayer sp = new ServerPlayer(username, password, index);
		//Add the login credentials
		playerLogin.put(key, index);
		//add the player
		
		players.put(index, sp);
		return index;
	}
	
	/**
	 * Gets the player at the ID
	 * @param playerID
	 * @return
	 * @throws GameException 
	 */
	public ServerPlayer GetPlayerID(int playerID) throws GameException
	{
		if (!players.containsKey(playerID))
			throw new GameException("Player ID doesn't exist: "+playerID);
		return players.get(playerID);
	}
	
	
	
}

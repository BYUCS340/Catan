package server.model;

import java.util.ArrayList;
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
	private List<ServerPlayer> players;
	private List<String> playerNames;
	private Map<String,Integer> playerLogin;
	private int numberPlayers = 0;
	
	
	public PlayerDen()
	{
		players = new ArrayList<>();
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
		String key = username+password;
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
		//Check to make sure the player isn't already registered
		if (playerNames.contains(username))
			throw new GameException("This username is already in use");
		
		String key = username+password;
		
		int index = numberPlayers++;
		ServerPlayer sp = new ServerPlayer(username,password,index);
		playerLogin.put(key, index);
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
		if (playerID < 0 || playerID >= players.size())
			throw new GameException("Player ID doesn't exist: "+playerID);
		return players.get(playerID);
	}
	
	
	
}

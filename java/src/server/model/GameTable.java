package server.model;

import java.util.Map;

import server.cookie.CookieHouse;
import shared.definitions.CatanColor;
import shared.model.Player;

/**
 * This keeps traack of the different games in the game
 * @author matthewcarlson
 *
 */
public class GameTable 
{
	private Map<Integer, ServerGameManager> games;
	private CookieHouse cookieTreeHouse;
	//TODO thing that manages players objects
	
	
	/**
	 * Creates a new game on the server 
	 * @return the id of the new game created -1 if unable to create
	 */
	public int CreateGame(String name)
	{
		return -1;
	}
	
	
	/**
	 * Gets the cookieTreeHouse
	 * @return
	 */
	public CookieHouse GetCookies()
	{
		return this.cookieTreeHouse;
	}
	
	
	/**
	 * Gets a game object
	 * @param id the 
	 * @return
	 * @throws GameException if the game is not found
	 */
	public ServerGameManager GetGame (int id) throws GameException
	{
		throw new GameException("Game "+id+" not found");
		
	}
	
	/**
	 * Joins a player to the game specified
	 * @param playerID
	 * @param gameID
	 * @param color the color if they haven't already
	 */
	public void JoinPlayer(int playerID, int gameID, CatanColor color)
	{
		//Check to make sure that the player is 
		if (!IsPlayerJoined(playerID, gameID))
		{
			//TODO join the player to the game
		}
	}
	
	/**
	 * Checks whether a player has joined a specific game
	 * @param playerID the player id
	 * @param gameID the game id
	 * @return true or false if the player has joined a game
	 */
	public Boolean IsPlayerJoined(int playerID, int gameID)
	{
		return false;
	}
	
	/**
	 * Gets a player object in the game instead
	 * @param playerID
	 * @param gameID
	 * @return the player color and index
	 */
	public Player PlayerInGame(int playerID, int gameID)
	{
		return null;
	}
	
	/**
	 * Gets the player in the server by id
	 * @param playerID
	 * @return
	 */
	public Player PlayerInServer(int playerID)
	{
		return null;
	}
	
	/**
	 * Looks up a player in the server by name
	 * @param name
	 * @return
	 */
	public Player PlayerInServer(String name)
	{
		return null;
	}
	
	/**
	 * Sets the game to the index
	 * @param sgm
	 * @param id
	 */
	private void SetGame(ServerGameManager sgm, int id)
	{
		games.put(id, sgm);
	}
	
	/**
	 * Loads a game
	 * @param filePath 
	 * @return
	 */
	public boolean LoadGame(String filePath)
	{
		return false;
	}
	
	/**
	 * Saves the game at the id
	 * @param id
	 * @param filePath the file destination to write to
	 * @return true if succedded
	 */
	public boolean SaveGame(int id, String filePath)
	{
		return false;
	}
	
	
	
}

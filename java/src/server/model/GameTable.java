package server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import server.cookie.CookieHouse;
import server.cookie.ServerCookie;
import shared.definitions.CatanColor;
import shared.model.Player;
import shared.data.GameInfo;
import shared.data.DataTranslator;

/**
 * This keeps traack of the different games in the game
 * @author matthewcarlson
 *
 */
public class GameTable 
{
	private Map<Integer, ServerGameManager> games;
	private CookieHouse cookieTreeHouse;
	private PlayerDen playerTable;
	//TODO thing that manages players objects
	
	public GameTable()
	{
		games = new HashMap<>();
		cookieTreeHouse = new CookieHouse();
		playerTable = new PlayerDen();
	}
	/**
	 * Creates a new game on the server 
	 * @return the id of the new game created -1 if unable to create
	 */
	public int CreateGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{
		int index = games.size();
		ServerGameManager sgm = new ServerGameManager(name, randomTiles, randomNumbers, randomPorts, index);
		games.put(index,sgm);
		return index;
	}
	
	/**
	 * 
	 * @return
	 */
	public int GetNumberGames()
	{
		return games.size();
	}
	
	public List<GameInfo> GetAllGames()
	{
		List<GameInfo> gamelist = new ArrayList<>(); 
		Iterator<ServerGameManager> iter = games.values().iterator();
		while(iter.hasNext())
		{
			ServerGameManager sgm = iter.next();
			GameInfo gi = new GameInfo();
			gi.setId(sgm.GetGameID());
			gi.setTitle(sgm.GetGameTitle());
			gi.setPlayers(sgm.allCurrentPlayers());
			//DataTranslator.convertPlayerInfo(player);
		}
		return gamelist;
		 
	}
	
	/**
	 * Checks a cookie
	 * @param text
	 * @return the player if found
	 * @throws GameException if the cookie is invalid
	 */
	public ServerPlayer CookieCheck(String text) throws GameException
	{
		ServerCookie sc = cookieTreeHouse.checkCookie(text);
		if (sc.isExpired()) throw new GameException("Cookie is expired");
		return playerTable.GetPlayerID(sc.getPlayerID());
	}
	
	/**
	 * Logins a player
	 * @param username
	 * @param password
	 * @return
	 * @throws GameException if the player ID wasn't found
	 */
	public ServerCookie Login(String username, String password) throws GameException
	{
		int playerID = playerTable.CheckLogin(username, password);
		if (playerID == -1) throw new GameException("Player isn't registered");
		return this.cookieTreeHouse.bakeCookie(playerID);
	}
	
	
	/**
	 * Registers the user
	 * @param username
	 * @param password
	 * @return the server cookie
	 * @throws GameException if username is in use
	 */
	public ServerCookie RegisterPlayer(String username, String password) throws GameException
	{
		int playerID = playerTable.RegisterPlayer(username, password);
		return this.cookieTreeHouse.bakeCookie(playerID);
	}
	
	/**
	 * Gets a game object
	 * @param id the 
	 * @return
	 * @throws GameException if the game is not found
	 */
	public ServerGameManager GetGame (int id) throws GameException
	{
		if (!games.containsKey(id))
			throw new GameException("Game "+id+" not found");
		else
			return games.get(id);
		
	}
	
	/**
	 * Joins a player to the game specified
	 * @param playerID
	 * @param gameID
	 * @param color the color if they haven't already
	 */
	public void JoinGame(int playerID, int gameID, CatanColor color)
	{
		//Check to make sure that the player is 
		if (!IsPlayerJoined(playerID, gameID))
		{
			//TODO join the player to the game
		}
		
		//TODO check if game ID has opening
		//TODO add to cookie
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
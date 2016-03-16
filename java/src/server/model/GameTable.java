package server.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.Log;
import shared.definitions.CatanColor;
import shared.model.ModelException;
import shared.model.Player;
import shared.data.GameInfo;
import shared.data.PlayerInfo;

/**
 * This keeps track of the different games in the game
 * @author matthewcarlson
 *
 */
public class GameTable 
{
	private GameHandler games;
	private PlayerDen playerTable;
	//TODO thing that manages players objects
	
	public GameTable()
	{
		games = new GameHandler();
		playerTable = new PlayerDen();
	}
	
	/**
	 * Registers the user
	 * @param username
	 * @param password
	 * @return the player ID
	 * @throws GameException if username is in use
	 */
	public int RegisterPlayer(String username, String password) throws GameException
	{
		return playerTable.RegisterPlayer(username, password);
	}
	
	/**
	 * Logins a player
	 * @param username
	 * @param password
	 * @return The player ID of the user.
	 * @throws GameException if the player ID wasn't found
	 */
	public int Login(String username, String password) throws GameException
	{
		int playerID = playerTable.CheckLogin(username, password);
		if (playerID == -1) 
			throw new GameException("Player isn't registered");
		
		return playerID;
	}
	
	/**
	 * Gets the games that are on the server.
	 * @return A list of game info
	 */
	public List<GameInfo> GetAllGames()
	{
		List<GameInfo> gamelist = new ArrayList<>(); 
		Iterator<ServerGameManager> iter = games.GetAllGames().iterator();
		while(iter.hasNext())
		{
			ServerGameManager sgm = iter.next();
			GameInfo gi = new GameInfo();
			gi.setId(sgm.GetGameID());
			gi.setTitle(sgm.GetGameTitle());
			gi.setPlayers(sgm.allCurrentPlayers());
			gamelist.add(gi);
		}
		return gamelist;	 
	}
	
	/**
	 * Creates a new game on the server 
	 * @return the game info of the new game created. Null if unable to create.
	 */
	public GameInfo CreateGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{
		if (games.ContainsGame(name))
			return null;
		
		return games.AddGame(name, randomTiles, randomNumbers, randomPorts);
	}
	
	/**
	 * Joins a player to the game specified
	 * @param playerID
	 * @param gameID
	 * @param color the color if they haven't already
	 */
	public boolean JoinGame(int playerID, int gameID, CatanColor color)
	{
		try 
		{
			ServerGameManager manager = games.GetGame(gameID);
			if (IsPlayerJoined(playerID, manager))
			{
				return true;
			}
			else if (manager.getNumberPlayers() < 4)
			{
				ServerPlayer player = playerTable.GetPlayerID(playerID);
				manager.AddPlayer(player.GetName(), color, true, playerID);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (GameException | ModelException e) 
		{
			Log.GetLog().throwing("GameTable", "JoinGame", e);
			return false;
		}
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
	 * @return true if succeeded
	 */
	public boolean SaveGame(int id, String filePath)
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
	 * Checks whether a player has joined a specific game
	 * @param playerID the player id
	 * @param gameID the game id
	 * @return true or false if the player has joined a game
	 */
	private Boolean IsPlayerJoined(int playerID, ServerGameManager manager)
	{
		PlayerInfo[] info = manager.allCurrentPlayers();
		for (PlayerInfo player : info)
		{
			if (player.getId() == playerID)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Gets the player in the server by id
	 * @param playerID
	 * @return
	 */
	private Player PlayerInServer(int playerID)
	{
		return null;
	}
	
	/**
	 * Looks up a player in the server by name
	 * @param name
	 * @return
	 */
	private Player PlayerInServer(String name)
	{
		return null;
	}
}

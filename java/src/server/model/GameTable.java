package server.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import server.Log;
import server.ai.AIHandler;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.model.ModelException;
import shared.networking.SerializationUtils;
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
		
		Set<String> aiNames = AIHandler.GetHandler().GetNames();
		for (String name : aiNames)
		{
			int index = playerTable.RegisterAI(name);
			AIHandler.GetHandler().RegisterAI(name, index);
		}
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
	 * Registers the user
	 * @param player The player to add
	 * @return the player ID
	 * @throws GameException if username is in use
	 */
	public int RegisterPlayer(ServerPlayer player) throws GameException
	{
		return playerTable.RegisterPlayer(player);
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
	public GameInfo CreateGame(ServerGameManager sgm, boolean setID)
	{
		if (games.ContainsGame(sgm.GetGameTitle()))
			return null;
		
		return games.AddGame(sgm, setID);
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
	 * Gets a game
	 * @param gameID
	 * @return
	 * @throws GameException
	 */
	public ServerGameManager GetGame(int gameID) throws GameException
	{
		return games.GetGame(gameID);
	}
	
	/**
	 * Gets the available list of AIs.
	 * @return List of AIs.
	 */
	public List<String> ListAI()
	{
		return AIHandler.GetHandler().GetTypes();
	}
	
	/**
	 * Adds an AI to the game
	 * @param playerID The player ID performing the add.
	 * @param gameID The game ID to add to.
	 * @param type The type of player.
	 */
	public boolean AddAI(int playerID, int gameID, AIType type)
	{
		try
		{
			ServerGameManager manager = games.GetGame(gameID);
			if (!IsPlayerJoined(playerID, manager)){
				Log.GetLog().severe("User is not joined to this game!");
				return false;
			}
			
			Set<CatanColor> notAvailable = new HashSet<CatanColor>();
			List<Integer> ids = new ArrayList<Integer>();
			int num = manager.getNumberPlayers();
			
			for (int i = 0; i < num; i++)
			{
				CatanColor color = manager.getPlayerColorByIndex(i);
				int takenPlayerID = manager.allCurrentPlayers()[i].getId();
				notAvailable.add(color);
				ids.add(takenPlayerID);
			}
			
			int aiID = AIHandler.GetHandler().GetAI(type, ids);
			String name = AIHandler.GetHandler().GetName(aiID);
			CatanColor color = AIHandler.GetHandler().PickColor(aiID, notAvailable);
			manager.AddPlayer(name, color, false, aiID);
			
			return true;
		}
		catch (Exception e)
		{
			Log.GetLog().finest("We had an exception "+e.getClass().getName()+" adding an AI"+e.getMessage());
			Log.GetLog().throwing("GameTable", "AddAI", e);
			return false;
		}
	}
	
	/**
	 * Loads a game
	 * @param filePath 
	 * @return
	 */
	public boolean LoadGame(String name)
	{
		
		try 
		{
			String filePath = File.separator+"savedata"+File.separator+name+".json";
			byte[] encoded;
			encoded = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + filePath));
			String data = new String(encoded, "utf-8");
			ServerGameManager sgm = SerializationUtils.deserialize(data, ServerGameManager.class);
			System.out.println(sgm.toString());
			System.out.println(sgm.GetGameTitle());
			games.SetGame(sgm);
			return true;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return false;
		
	}
	
	/**
	 * Saves the game at the id
	 * @param id
	 * @param filePath the file destination to write to
	 * @return true if succeeded
	 */
	public boolean SaveGame(int id, String name)
	{
		try
		{
			ServerGameManager sgm = this.GetGame(id);
			String data = SerializationUtils.serialize((Serializable) sgm);
			
			String filename = File.separator+"savedata"+File.separator+name+".json";
			String filePath = System.getProperty("user.dir") + filename;
			Log.GetLog().finest("Saved to :"+filePath);
			File file = new File(filePath);
			
			FileOutputStream fop = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fop, "utf-8");
			Writer writer = new BufferedWriter(osw);
			writer.write(data);
			writer.close();
			return true;
		}
		
		catch (GameException e)
		{ //game not found
			Log.GetLog().finest(e.getMessage());
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			Log.GetLog().finest(e.getMessage());
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			Log.GetLog().finest(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			Log.GetLog().finest(e.getMessage());
			e.printStackTrace();
		}
		Log.GetLog().finest("Unable to save");
		return false;
	}
	
	/**
	 * Gets a player object in the game instead
	 * @param playerID
	 * @param gameID
	 * @return the player color and index
	 * @throws GameException 
	 */
	public int GetPlayerIndex(int playerID, int gameID) throws GameException
	{
		ServerGameManager manager = games.GetGame(gameID);
		if (!IsPlayerJoined(playerID, manager))
			throw new GameException("Player not in game");
		
		List<PlayerInfo> info = Arrays.asList(manager.allCurrentPlayers());
		for (PlayerInfo i : info)
		{
			if (i.getId() == playerID)
				return i.getPlayerIndex();
		}
		
		assert false;
		return -1;
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
}

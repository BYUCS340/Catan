package server.model;

import java.util.HashMap;
import java.util.Map;

import shared.definitions.CatanColor;
import shared.model.GameManager;
import shared.model.GameModel;
import shared.model.ModelException;
import shared.model.map.model.MapGenerator;
import shared.model.map.model.MapModel;

/**
 * Special formation of the game manager 
 * @author matthewcarlson
 *
 */
public class ServerGameManager extends GameManager {
	private	boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	
	private Map<Integer,Integer> playerIndexLookup;
	
	public ServerGameManager(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts, int index) 
	{
		super();
		this.gameTitle = name;
		this.gameID = index;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.randomTiles = randomTiles;
		this.playerIndexLookup = new HashMap<>();
		this.map = MapGenerator.GenerateMap(randomTiles, randomNumbers, randomPorts);
	}

	/**
	 * Updates the version when doing an action
	 */
	private void updateVersion()
	{
		this.version++;
	}
	
	/**
	 * Returns the player index by id
	 * @param playerID
	 * @return
	 */
	public int GetPlayerIndexByID(int playerID)
	{
		return playerIndexLookup.get(playerID);
	}
	
	@Override
	public int AddPlayer(String name, CatanColor color, boolean isHuman, int playerID) throws ModelException
	{
		int index = super.AddPlayer(name, color, isHuman, playerID);
		playerIndexLookup.put(playerID, index);
		
		return index;
	}
	
	/**
	 * Sends a chat for the user
	 * @param playerID by Player ID
	 * @param message to chat with
	 */
	public boolean SendChat(int playerID, String message)
	{
		int playerIndex = GetPlayerIndexByID(playerID);
		if (super.canChat(playerIndex))
		{
			super.PlayerChat(playerIndex, message);
			this.updateVersion();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param playerID
	 * @param number
	 * @return
	 */
	public boolean RollNumber(int playerID, int number)
	{
		int playerIndex = GetPlayerIndexByID(playerID);
		if (!super.CanRollNumber(playerIndex)) 
			return false;
		//Roll the dice
		super.DiceRoll(number);
		this.updateVersion();
		return true;
	}
	
	/**
	 * Rob a player
	 * @param playerID
	 * @param playerIndex
	 * @param hex
	 * @return
	 */
	public boolean RobPlayer(int playerID, int playerIndex, Coordinate hex)
	{
		return false;
	}
	
	/**
	 * Condenses the current game model into the game model object
	 * @return
	 */
	public GameModel condense()
	{
		GameModel gm = new GameModel();
		
		gm.gameBank = this.gameBank;
		gm.gameState = this.gameState;
		gm.log = this.log;
		gm.mapModel = this.map;
		gm.players = this.players;
		gm.version = this.version;
		gm.waterCooler = this.waterCooler;
		gm.victoryPointManager = this.victoryPointManager;
		
		return gm;
	}
	
}

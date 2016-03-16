package server.model;

import shared.model.GameManager;
import shared.model.GameModel;
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
	
	public ServerGameManager(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts, int index) 
	{
		super();
		this.gameTitle = name;
		this.gameID = index;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.randomTiles = randomTiles;
		
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

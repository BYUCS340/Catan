package shared.model;

import java.util.List;

import client.map.MapController;
import shared.definitions.ResourceType;
import shared.model.chat.ChatBox;
import shared.networking.transport.NetGameModel;

/**
 * The game manager class acts as a facade between the player/game objects and the ServerProxy/UI
 * @author matthewcarlson, garrettegan
 *
 */
public class GameManager
{
	private GameState gameState;
	private Bank gameBank;
	private List<Player> players;
	private VictoryPointManager victoryPointManager;
	private MapController mapController;
	private ChatBox waterCooler;
	
	/**
	 * @see <a href="https://imgs.xkcd.com/comics/random_number.png">Sauce</a>
	 * @return
	 */
	public int rollDice()
	{
		return 4; // chosen by fair dice roll
				  // guaranteed to be random
	}
	
	
	//--------------------------------------------------------------------------
	//Networking methods
	
	/**
	 * Loads in a game 
	 * @param model the model to be loaded in
	 * @throws ModelException if model is incorrect
	 */
	public void loadGame(NetGameModel model) throws ModelException
	{
		throw new ModelException();
	}
	
	/**
	 * What the poller pokes to refresh the game model from teh server
	 * @see client.networking.Poller
	 */
	public void refreshFromServer() throws ModelException
	{
		NetGameModel model = null;
		this.loadGame(model);
	}
	
	
	//--------------------------------------------------------------------------
	//Player Turn Game methods
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void buildRoad(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void buildSettlement(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void buildCity(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void buyDevCard(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Gets the trade ratio for a given resource for a player
	 * @param playerID
	 * @return never more than 4 or less than 2
	 */
	public int getTradeRatio(int playerID, ResourceType type)
	{
		//Check the Map to see if they're connected to a port
		//Otherwise return the default trade in value
		return 4;
	}
	
	
	//--------------------------------------------------------------------------
	//Chat methods
	/**
	 * When the player chats currently playing 
	 * @param message
	 */
	public void currentPlayerChat(String message)
	{
		this.playerChat(gameState.activePlayerIndex, message);
	}
	
	/**
	 * Chats for the specified player
	 * @param playerId 0-3
	 * @param message
	 */
	public void playerChat(int playerId, String message)
	{
		waterCooler.put(message, playerId);
	}
	
	//--------------------------------------------------------------------------
	//Game methods
	
	/**
	 * Starts the game
	 */
	public void startGame()
	{
		gameState.startGame();
	}
	
	/**
	 * Ends the current player's turn
	 */
	public void finishTurn()
	{
		gameState.activePlayerIndex++;
		if (gameState.activePlayerIndex > 3)
			gameState.activePlayerIndex = 0;
		gameState.gameState = GameStatus.ROLLING;
	}
	
	/**
	 * Gets the player index of the current player
	 * @return 0 to 3 or -1 if no player is playing
	 */
	public int currentPlayersTurn()
	{
		if (gameState.gameState == GameStatus.START)
		{
			return -1;
		}
		return gameState.activePlayerIndex;
	}
	
	/**
	 * Returns the current State of the game
	 * @return
	 */
	public GameStatus currentState()
	{
		return gameState.gameState;
	}
}

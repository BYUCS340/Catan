package shared.model;

import java.util.List;

import client.map.MapController;

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
	
	/**
	 * @see <a href="https://imgs.xkcd.com/comics/random_number.png">Sauce</a>
	 * @return
	 */
	public int rollDice()
	{
		return 4; // chosen by fair dice roll
				  // guaranteed to be random
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelIllegalAction if the player doesn't have the resources
	 */
	public void buildRoad(int playerID) throws ModelIllegalAction
	{
		
	}
	
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
		
	}
	
	/**
	 * Gets the player index of the current player
	 * @return 0 to 3 or -1 if no player is playing
	 */
	public int currentPlayersTurn(){
		return -1;
	}
	
	/**
	 * Returns the current State of the game
	 * @return
	 */
	public GameState currentState(){
		return 
	}
}

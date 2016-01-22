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
	 * 
	 * @see <a href="https://imgs.xkcd.com/comics/random_number.png">Sauce</a>
	 * @return
	 */
	public int rollDice()
	{
		return 4; // chosen by fair dice roll
				  // guaranteed to be random
	}
}

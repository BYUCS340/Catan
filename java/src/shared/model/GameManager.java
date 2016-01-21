package shared.model;

import java.util.List;

public class GameManager
{
	private GameState gameState;
	private Bank gameBank;
	private List<Player> players;
	private VictoryPointManager victoryPointManager;
	
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

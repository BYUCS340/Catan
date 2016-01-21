package shared.model;

import java.util.List;

public class GameManager
{
	private GameState gameState;
	private Bank gameBank;
	private List<Player> players;
	private VictoryPointManager victoryPointManager;
	
	public int rollDice()
	{
		//we rolled a die and it was 4 so that's pretty random
		return 4;
	}
}

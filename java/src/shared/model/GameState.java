package shared.model;

import shared.definitions.GameRound;
import shared.definitions.GameStatus;

/**
 * An object that keeps track of whose turn it is.
 * @author matthewcarlson, garrettegan
 * @todo 
 */
public class GameState
{
	public GameStatus gameState = GameStatus.START;;  //This shows which part of a player's turn it is
	public GameRound gameRound = GameRound.FIRSTROUND;
	public int activePlayerIndex = 0;  //This keeps track of which player's turn it is
	
	/**
	 * Sets the game state to the first player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startGame()
	{
		if (gameState != GameStatus.START) return false;
		gameRound = GameRound.FIRSTROUND;
		activePlayerIndex = 0;
		
		return true;
	}
	/**
	 * Sets the game state to the next player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean nextTurn()
	{
		if (gameState != GameStatus.BUILDING) return false;
		activePlayerIndex ++;
		if (activePlayerIndex > 3)
		{
			activePlayerIndex = 0;
			if (gameRound == GameRound.FIRSTROUND) gameRound = GameRound.SECONDROUND;
			else if (gameRound == GameRound.SECONDROUND) gameRound = GameRound.MAINROUND;
		}
		
		return true;
	}
	/**
	 * Sets the game state to the same player's turn (build phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startBuildPhase()
	{
		if (gameState != GameStatus.ROLLING) return false;

		return true;
	}
	/**
	 * Sets the game state to finished
	 * @return successful or not (almost always is true)
	 */
	public boolean IsEndGame()
	{
		//check if someone has 10 points or not
		return gameRound == GameRound.GAMEOVER;
	}
	
	public void endGame()
	{
		gameRound = GameRound.GAMEOVER;
	}
}

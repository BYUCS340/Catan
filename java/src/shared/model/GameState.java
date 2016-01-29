package shared.model;

import shared.definitions.GameStatus;

/**
 * An object that keeps track of whose turn it is.
 * @author matthewcarlson, garrettegan
 * @todo 
 */
public class GameState
{
	public GameStatus gameState;  //This shows which part of a player's turn it is
	public int activePlayerIndex;  //This keeps track of which player's turn it is
	
	/**
	 * Sets the game state to the first player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startGame()
	{
		if (gameState != GameStatus.START) return false;
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
	public boolean endGame()
	{
		//check if someone has 10 points or not
		return true;
	}
}

package shared.model;

import shared.model.State;

/**
 * An object that keeps track of whose turn it is.
 * @author matthewcarlson, garrettegan
 *
 */
public class GameState
{
	public State gameState;  //This shows which part of a player's turn it is
	public int playerIndex;  //This keeps track of which player's turn it is
	
	/**
	 * Sets the game state to the first player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startGame()
	{
		if (gameState != State.START) return false;
		
		return true;
	}
	/**
	 * Sets the game state to the next player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean nextTurn()
	{
		if (gameState != State.BUILDING) return false;

		return true;
	}
	/**
	 * Sets the game state to the same player's turn (build phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startBuildPhase()
	{
		if (gameState != State.ROLLING) return false;

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

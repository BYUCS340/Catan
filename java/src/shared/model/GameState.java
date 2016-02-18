package shared.model;

import shared.definitions.GameRound;

/**
 * An object that keeps track of whose turn it is.
 * @author matthewcarlson, garrettegan
 * @todo 
 */
public class GameState
{
	public GameRound state = GameRound.WAITING;
	public int activePlayerIndex = 0;  //This keeps track of which player's turn it is
	
	/**
	 * Sets the game state to the first player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startGame()
	{
		state = GameRound.FIRSTROUND;
		activePlayerIndex = 0;
		
		return true;
	}
	/**
	 * Sets the game state to the next player's turn (roll phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean nextTurn()
	{
		if (state == GameRound.ROLLING || state == GameRound.ROBBING) return false;
		activePlayerIndex ++;
		if (activePlayerIndex > 3)
		{
			activePlayerIndex = 0;
			if (state == GameRound.FIRSTROUND) state = GameRound.SECONDROUND;
			else if (state == GameRound.SECONDROUND ) state = GameRound.ROLLING;
		}
		if (state == GameRound.PLAYING) state = GameRound.ROLLING;
		
		return true;
	}
	/**
	 * Sets the game state to the same player's turn (build phase)
	 * @return successful or not (almost always is true)
	 */
	public boolean startBuildPhase()
	{
		if (state != GameRound.ROLLING) return false;
		state = GameRound.PLAYING;

		return true;
	}
	
	/**
	 * Start robbing
	 * @todo throw an excpetion here
	 * @return
	 */
	public boolean startRobbing()
	{
		if (state != GameRound.ROLLING && state != GameRound.PLAYING) return false;
		state = GameRound.ROBBING;
		
		return true;
	}
	
	/**
	 * Stop the robbing
	 * @return
	 */
	public boolean stopRobbing()
	{
		//SWIPER NOT SWIPING
		if (state != GameRound.ROBBING) return false;
		state = GameRound.PLAYING;
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean stopRolling()
	{
		if (state != GameRound.ROLLING) return false;
		state = GameRound.PLAYING;
		return true;
	}
	/**
	 * Sets the game state to finished
	 * @return successful or not (almost always is true)
	 */
	public boolean IsEndGame()
	{
		//check if someone has 10 points or not
		return state == GameRound.GAMEOVER;
	}
	
	public void endGame()
	{
		state = GameRound.GAMEOVER;
	}
}

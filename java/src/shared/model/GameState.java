package shared.model;

import java.io.Serializable;

import shared.definitions.GameRound;

/**
 * An object that keeps track of whose turn it is.
 * @author matthewcarlson, garrettegan
 * @todo 
 */
public class GameState implements Serializable
{
	private static final long serialVersionUID = 2016268655363017640L;

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
		
		if (state == GameRound.SECONDROUND)
			activePlayerIndex --;
		else
			activePlayerIndex ++;
		
		//if we go to the last player
		if (activePlayerIndex > 3)
		{
			activePlayerIndex = 0;
			//if it's the first round go to the second round
			if (state == GameRound.FIRSTROUND) 
			{
				state = GameRound.SECONDROUND;
				activePlayerIndex = 3;
			}
		}
		if (activePlayerIndex < 0 && state == GameRound.SECONDROUND)
		{
			activePlayerIndex = 0;
			state = GameRound.ROLLING;
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
	public boolean startRobbing(boolean requireDiscard)
	{
		if (state != GameRound.ROLLING && state != GameRound.PLAYING) 
			return false;
		
		if (requireDiscard)
			state = GameRound.DISCARDING;
		else
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
		if (state != GameRound.ROBBING) 
			return false;
		
		state = GameRound.PLAYING;
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean stopRolling()
	{
		if (state != GameRound.ROLLING) 
			return false;
		
		state = GameRound.PLAYING;
		return true;
	}
	/**
	 * checks if the game state is finished
	 * @return successful or not (almost always is true)
	 */
	public boolean IsEndGame()
	{
		//check if someone has 10 points or not
		return state == GameRound.GAMEOVER;
	}
	
	/**
	 * Whether the game is in setup or not
	 * @return true if firstround or second round
	 */
	public boolean IsSetup() {
		return state == GameRound.FIRSTROUND || state == GameRound.SECONDROUND;
	}
	
	public void endGame()
	{
		state = GameRound.GAMEOVER;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + activePlayerIndex;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GameState))
			return false;
		GameState other = (GameState) obj;
		if (activePlayerIndex != other.activePlayerIndex)
			return false;
		if (state != other.state)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameState [");
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		builder.append("activePlayerIndex=");
		builder.append(activePlayerIndex);
		builder.append("]");
		return builder.toString();
	}
	
}

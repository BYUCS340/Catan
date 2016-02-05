package shared.networking.transport;

import shared.definitions.GameRound;

public class NetTurnTracker
{
	GameRound round;
	int currentTurn;
	int longestRoad;
	int largestArmy;
	int largestArmySize;
	
	/**
	 * Default constructor, sets everything as if it were the first
	 * turn 
	 */
	public NetTurnTracker()
	{
		round = GameRound.FIRSTROUND;
		currentTurn = 0;
		longestRoad = -1;
		largestArmy = -1;
		largestArmySize = 0;
	}

	/**
	 * @return the round
	 */
	public GameRound getRound()
	{
		return round;
	}

	/**
	 * @param round sets which round the game is currently on
	 */
	public void setRound(GameRound round)
	{
		this.round = round;
	}

	/**
	 * @return the currentTurn
	 */
	public int getCurrentTurn()
	{
		return currentTurn;
	}

	/**
	 * @param currentTurn sets the ID of the player whose turn 
	 * it is
	 */
	public void setCurrentTurn(int currentTurn)
	{
		this.currentTurn = currentTurn;
	}

	/**
	 * @return the longestRoad's player ID
	 */
	public int getLongestRoad()
	{
		return longestRoad;
	}

	/**
	 * @param longestRoad sets the ID of the player who has
	 *  the longest road
	 */
	public void setLongestRoad(int longestRoad)
	{
		this.longestRoad = longestRoad;
	}

	/**
	 * @return the largestArmy's player ID
	 */
	public int getLargestArmy()
	{
		return largestArmy;
	}

	/**
	 * @param largestArmy sets the ID of the player who has
	 * the largest army
	 */
	public void setLargestArmy(int largestArmy)
	{
		this.largestArmy = largestArmy;
	}
	
	/**
	 * @return the largestArmy's player ID
	 */
	public int getLargestArmySize()
	{
		return largestArmySize;
	}

	/**
	 * @param largestArmy sets the ID of the player who has
	 * the largest army
	 */
	public void setLargestArmySize(int size)
	{
		this.largestArmySize = size;
	}
	
	
	
	
}

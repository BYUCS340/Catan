package shared.model;
/**
 * An object that keeps track of how many victory points each player has. 
 * This way we keep points centralized and they're easier to track/change (especially longest road & largest army).
 * @author matthewcarlson, garrettegan
 *
 */
public class VictoryPointManager
{
	private int currentLongestRoadPlayer = -1;
	private int currentLargestArmyPlayer = -1;
	private int[] victoryPoints = new int[4];
	
	
	//This class keeps track of each players' victory points.
	
	/**
	 * Gets the current number of victory points the current player has
	 * @param playerIndex 0 to 3
	 * @return 0-10
	 */
	public int getVictoryPoints(int playerIndex)
	{
		return 0;
	}
	
	/**
	 * Tells the victory point manager which player now has the longest road- determined by map?
	 * @param playerIndex 0 to 3
	 * @return if successful (almost always does)
	 */
	public boolean setPlayerToHaveLongestRoad(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex > 3) return false;
		
		return true;
	}
	
	/**
	 * Tells the VP manager which player now has the largest army - determined by @see map?
	 * @param playerIndex 0 to 3
	 * @see Map
	 * @return successful or not (almost always is true)
	 */
	public boolean setPlayerToHaveLargestArmy(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex > 3) return false;
		
		return true;
	}
	
	/**
	 * Gets the specified player's current number of victory points
	 * @param playerIndex
	 * @see Map
	 * @return an int between 0 and 10, -1 if invalid index
	 */
	public int currentPlayerScore(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex > 3) 
			return -1;
		return victoryPoints[playerIndex];
	}
}

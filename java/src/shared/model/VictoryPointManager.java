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
	
	private final int LongestRoadValue = 2;
	private final int LargestArmyValue = 2;
	private final int RoadValue = 1;
	private final int SettlementValue = 1;
	private final int CityValue = 2;
	
	
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
	
	private void adjustPlayersPoints(int playerID, int points)
	{
		
	}
	
	/**
	 * Tells the victory point manager which player now has the longest road- determined by map?
	 * @param playerIndex 0 to 3
	 * @return if successful (almost always does)
	 */
	public boolean setPlayerToHaveLongestRoad(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex > 3) return false;
		
		if (currentLongestRoadPlayer != -1)
		{
			//A player has the card already so deduct points from them
			adjustPlayersPoints(currentLongestRoadPlayer, -1 * LongestRoadValue);
		}
		
		currentLongestRoadPlayer = playerIndex;
		
		//give the new player the points
		adjustPlayersPoints(currentLongestRoadPlayer, LongestRoadValue);
		
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
		
		if (currentLargestArmyPlayer != -1)
		{
			//A player has the card already so deduct points from them
			adjustPlayersPoints(currentLargestArmyPlayer, -1 * LargestArmyValue);
		}
		
		currentLargestArmyPlayer = playerIndex;
		
		//give the new player the points
		adjustPlayersPoints(currentLargestArmyPlayer, LargestArmyValue);
		
		return true;
	}
	
	/**
	 * The player built a road, so give them the points
	 * @param playerIndex
	 */
	public void playerBuiltRoad(int playerIndex)
	{
		
	}
	
	/**
	 * The player built a settlement, so points to them
	 * @param playerIndex
	 */
	public void playerBuiltSettlement(int playerIndex)
	{
		
	}
	
	/**
	 * The player built a city, so points to them
	 * @param playerIndex
	 */
	public void playerBuiltCity(int playerIndex)
	{
		//take away the settlement points
		
		//add the city points
	}
	
	/**
	 * The player bought a dev card to give them the parts
	 * @param playerIndex
	 */
	public void playerGotDevCard(int playerIndex)
	{
		
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
	
	/**
	 * Checks if anyone has won yet
	 * @return true if there is a winner
	 */
	public boolean anyWinner()
	{
		return false;
	}
}

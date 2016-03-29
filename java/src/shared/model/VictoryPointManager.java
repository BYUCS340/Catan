package shared.model;

import java.io.Serializable;
import java.util.Arrays;

import shared.definitions.DevCardType;

/**
 * An object that keeps track of how many victory points each player has. 
 * This way we keep points centralized and they're easier to track/change (especially longest road & largest army).
 * @author matthewcarlson, garrettegan
 *
 */
public class VictoryPointManager implements Serializable
{
	private static final long serialVersionUID = -5622601186536231359L;

	private int currentLongestRoadPlayer = -1;
	private int currentLargestArmyPlayer = -1;
	private int[] victoryPoints = new int[4];
	private int currentLargestArmySize = 0;
	
	private final int LongestRoadValue = 2;
	private final int LargestArmyValue = 2;
	private final int RoadValue = 0;
	private final int SettlementValue = 1;
	private final int CityValue = 2;
	
	
	//This class keeps track of each players' victory points.
	
	/**
	 * 
	 * @param p1Score
	 * @param p2Score
	 * @param p3Score
	 * @param p4Score
	 * @param currRoadOwner
	 * @param currArmyOWner
	 * @param armySize
	 */
	public VictoryPointManager(int p1Score, int p2Score, int p3Score, int p4Score, int currRoadOwner, int currArmyOwner, int armySize)
	{
		victoryPoints[0] = p1Score;
		victoryPoints[1] = p2Score;
		victoryPoints[2] = p3Score;
		victoryPoints[3] = p4Score;
		
		this.currentLargestArmyPlayer = currArmyOwner;
		this.currentLargestArmySize = armySize;
		this.currentLongestRoadPlayer = currRoadOwner;
	}
	
	/**
	 * Generic default contructor
	 */
	public VictoryPointManager() 
	{
		
	}
	
	/**
	 * Gets the current number of victory points the current player has
	 * @param playerIndex 0 to 3
	 * @return 0-10
	 */
	public int getVictoryPoints(int playerID)
	{
		if (playerID < 0 || playerID > 3) return 0;
		return victoryPoints[playerID];
	}
	
	private void adjustPlayersPoints(int playerID, int points)
	{
		if (playerID < 0 || playerID > 3) return;
		victoryPoints[playerID] += points;
	}
	
	public void playedMonument(int playerIndex)
	{
		this.adjustPlayersPoints(playerIndex, 1);
	}
	
	/**
	 * Tells the victory point manager which player now has the longest road- determined by map?
	 * @param playerIndex 0 to 3
	 * @return if successful (almost always does)
	 */
	public boolean setPlayerToHaveLongestRoad(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex > 3) 
			return false;
		
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
	private boolean setPlayerToHaveLargestArmy(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex > 3) return false;
		
		if (currentLargestArmyPlayer != -1)
		{
			//A player has the card already so deduct points from them
			adjustPlayersPoints(currentLargestArmyPlayer, -1*LargestArmyValue);
		}
		
		currentLargestArmyPlayer = playerIndex;
		
		//give the new player the points
		adjustPlayersPoints(currentLargestArmyPlayer, LargestArmyValue);
		
		return true;
	}
	
	/**
	 * Checks to see a player's army size, if so update their points accordingly
	 * @param playerIndex 0-3
	 * @param armySize 0-10?
	 */
	public void checkPlayerArmySize(int playerIndex, int armySize)
	{
		if (this.currentLargestArmySize < armySize && armySize > 2)
		{
			this.currentLargestArmySize = armySize;
			this.setPlayerToHaveLargestArmy(playerIndex);
		}
	}
	
	/**
	 * The player built a road, so give them the points
	 * @param playerIndex
	 */
	public void playerBuiltRoad(int playerIndex)
	{
		adjustPlayersPoints(playerIndex, this.RoadValue);
	}
	
	/**
	 * The player built a settlement, so points to them
	 * @param playerIndex
	 */
	public void playerBuiltSettlement(int playerIndex)
	{
		adjustPlayersPoints(playerIndex, this.SettlementValue);
	}
	
	/**
	 * The player built a city, so points to them
	 * @param playerIndex
	 */
	public void playerBuiltCity(int playerIndex)
	{
		//take away the settlement points
		adjustPlayersPoints(playerIndex, -1 * this.SettlementValue);
		//add the city points
		adjustPlayersPoints(playerIndex, this.CityValue);
	}
	
	/**
	 * The player bought a dev card to give them the parts
	 * @param playerIndex
	 * @param card the card they received (for determining worth)
	 */
	public void playerGotDevCard(int playerIndex,DevCardType card)
	{
		//Soliders won't be worried about here since that's handled in game manager
		//We don't have a current count of the player's army
	}
	
	/**
	 * Checks if anyone has won yet
	 * @return true if there is a winner
	 */
	public boolean anyWinner()
	{
		for (int i=0;i<=3; i++)
		{
			if (victoryPoints[i] >= 10) return true;
		}
		return false;
	}
	
	/**
	 * Returns the winner, if there is any
	 * @return the index of the winner
	 */
	public int winner()
	{
		for (int i=0;i<=3; i++)
		{
			if (victoryPoints[i] >= 10) return i;
		}
		return -1;
	}

	/**
	 * @return the current Longest Road Player's index
	 */
	public int getCurrentLongestRoadPlayer()
	{
		return currentLongestRoadPlayer;
	}

	/**
	 * @return the current Largest Army Player's index
	 */
	public int getCurrentLargestArmyPlayer()
	{
		return currentLargestArmyPlayer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentLargestArmyPlayer;
		result = prime * result + currentLargestArmySize;
		result = prime * result + currentLongestRoadPlayer;
		result = prime * result + Arrays.hashCode(victoryPoints);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VictoryPointManager))
			return false;
		VictoryPointManager other = (VictoryPointManager) obj;
		if (currentLargestArmyPlayer != other.currentLargestArmyPlayer)
			return false;
		if (currentLargestArmySize != other.currentLargestArmySize)
			return false;
		if (currentLongestRoadPlayer != other.currentLongestRoadPlayer)
			return false;
		if (!Arrays.equals(victoryPoints, other.victoryPoints))
			return false;
		return true;
	}
}

package shared.networking.transport;

public class NetDevCardList
{
	int numMonopoly;
	int numMonument;
	int numRoadBuilding;
	int numSoldier;
	int numYearOfPlenty;
	
	/**
	 * Default constructor, initializes all values
	 * to 0
	 */
	public NetDevCardList(){
		numMonopoly = 0;
		numMonument = 0;
		numRoadBuilding = 0;
		numSoldier = 0;
		numYearOfPlenty = 0;
	}

	/**
	 * @return the numMonopoly
	 */
	public int getNumMonopoly()
	{
		return numMonopoly;
	}

	/**
	 * @param numMonopoly the numMonopoly to set
	 */
	public void setNumMonopoly(int numMonopoly)
	{
		this.numMonopoly = numMonopoly;
	}

	/**
	 * @return the numMonument
	 */
	public int getNumMonument()
	{
		return numMonument;
	}

	/**
	 * @param numMonument the numMonument to set
	 */
	public void setNumMonument(int numMonument)
	{
		this.numMonument = numMonument;
	}

	/**
	 * @return the numRoadBuilding
	 */
	public int getNumRoadBuilding()
	{
		return numRoadBuilding;
	}

	/**
	 * @param numRoadBuilding the numRoadBuilding to set
	 */
	public void setNumRoadBuilding(int numRoadBuilding)
	{
		this.numRoadBuilding = numRoadBuilding;
	}

	/**
	 * @return the numSoldier
	 */
	public int getNumSoldier()
	{
		return numSoldier;
	}

	/**
	 * @param numSoldier the numSoldier to set
	 */
	public void setNumSoldier(int numSoldier)
	{
		this.numSoldier = numSoldier;
	}

	/**
	 * @return the numYearOfPlenty
	 */
	public int getNumYearOfPlenty()
	{
		return numYearOfPlenty;
	}

	/**
	 * @param numYearOfPlenty the numYearOfPlenty to set
	 */
	public void setNumYearOfPlenty(int numYearOfPlenty)
	{
		this.numYearOfPlenty = numYearOfPlenty;
	}
	
}

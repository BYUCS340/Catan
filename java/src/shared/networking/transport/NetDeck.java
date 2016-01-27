package shared.networking.transport;

public class NetDeck
{
	int yearOfPlenty;
	int monopoly;
	int soldier;
	int roadBuilding;
	int monument;
	
	/**
	 * Default constructor, initializes the deck with 0 of every
	 * card
	 */
	public NetDeck(){
		yearOfPlenty = 0;
		monopoly = 0;
		soldier = 0;
		roadBuilding = 0;
		monument = 0;
	}

	/**
	 * @return the yearOfPlenty
	 */
	public int getYearOfPlenty()
	{
		return yearOfPlenty;
	}

	/**
	 * @param yearOfPlenty the yearOfPlenty to set
	 */
	public void setYearOfPlenty(int yearOfPlenty)
	{
		this.yearOfPlenty = yearOfPlenty;
	}

	/**
	 * @return the monopoly
	 */
	public int getMonopoly()
	{
		return monopoly;
	}

	/**
	 * @param monopoly the monopoly to set
	 */
	public void setMonopoly(int monopoly)
	{
		this.monopoly = monopoly;
	}

	/**
	 * @return the soldier
	 */
	public int getSoldier()
	{
		return soldier;
	}

	/**
	 * @param soldier the soldier to set
	 */
	public void setSoldier(int soldier)
	{
		this.soldier = soldier;
	}

	/**
	 * @return the roadBuilding
	 */
	public int getRoadBuilding()
	{
		return roadBuilding;
	}

	/**
	 * @param roadBuilding the roadBuilding to set
	 */
	public void setRoadBuilding(int roadBuilding)
	{
		this.roadBuilding = roadBuilding;
	}

	/**
	 * @return the monument
	 */
	public int getMonument()
	{
		return monument;
	}

	/**
	 * @param monument the monument to set
	 */
	public void setMonument(int monument)
	{
		this.monument = monument;
	}
	
	
}

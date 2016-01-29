package shared.networking.transport;

public class NetBank
{
	int numBrick;
	int numWood;
	int numSheep;
	int numWheat;
	int numOre;
	
	/**
	 * Default constructor, sets all values to 0
	 */
	public NetBank(){
		numBrick = 0;
		numWood = 0;
		numSheep = 0;
		numWheat = 0;
		numOre = 0;
	}

	/**
	 * @return the numBrick
	 */
	public int getNumBrick()
	{
		return numBrick;
	}

	/**
	 * @param numBrick the numBrick to set
	 */
	public void setNumBrick(int numBrick)
	{
		this.numBrick = numBrick;
	}

	/**
	 * @return numWood
	 */
	public int getNumWood()
	{
		return numWood;
	}

	/**
	 * @param numWood the numWood to set
	 */
	public void setNumWood(int numWood)
	{
		this.numWood = numWood;
	}

	/**
	 * @return numSheep
	 */
	public int getNumSheep()
	{
		return numSheep;
	}

	/**
	 * @param numSheep the numSheep to set
	 */
	public void setNumSheep(int numSheep)
	{
		this.numSheep = numSheep;
	}

	/**
	 * @return numWheat
	 */
	public int getNumWheat()
	{
		return numWheat;
	}

	/**
	 * @param numWheat the numWheat to set
	 */
	public void setNumWheat(int numWheat)
	{
		this.numWheat = numWheat;
	}

	/**
	 * @return numOre
	 */
	public int getNumOre()
	{
		return numOre;
	}

	/**
	 * @param numOre the numOre to set
	 */
	public void setNumOre(int numOre)
	{
		this.numOre = numOre;
	}
	
	
}

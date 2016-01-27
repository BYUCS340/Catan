package shared.networking.transport;

public class NetResourceList
{
	int numBrick;
	int numOre;
	int numSheep;
	int numWheat;
	int numWood;
	
	/**
	 * Default Constructor. Initializes all resources to 0.
	 */
	public NetResourceList()
	{
		numBrick=0;
		numOre=0;
		numSheep=0;
		numWheat=0;
		numWood=0;
	}

	/**
	 * @return numBrick
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
	
	
	
}

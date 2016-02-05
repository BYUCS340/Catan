package shared.networking.transport;

import shared.definitions.CatanColor;

public class NetPlayer
{
	int numCities;
	CatanColor color;
	boolean hasDiscarded;
	int numMonuments;
	String name;
	NetDevCardList oldNetDevCardList;
	NetDevCardList newNetDevCardList;
	int playerIndex;
	boolean playedDevCard;
	int playerID;
	NetResourceList netResourceList;
	int numRoads;
	int numSettlements;
	int numSoldiers;
	int numVictoryPoints;
	
	
	/**
	 * Default constructor, sets game pieces to 0, player index to -1,
	 * and everything else to some default value that should not be 
	 * trusted
	 */
	public NetPlayer()
	{
		numCities = 0;
		color = null;
		hasDiscarded = false;
		numMonuments = 0;
		hasDiscarded = false;
		name = null;
		oldNetDevCardList = new NetDevCardList();
		newNetDevCardList = new NetDevCardList();
		playerIndex = -1;
		playerID = 0;
		netResourceList = new NetResourceList();
		numRoads = 0;
		numSettlements = 0;
		numSoldiers = 0;
		numVictoryPoints = 0;
		
	}


	/**
	 * @return the numCities
	 */
	public int getNumCities()
	{
		return numCities;
	}


	/**
	 * @param numCities the numCities to set
	 */
	public void setNumCities(int numCities)
	{
		this.numCities = numCities;
	}


	/**
	 * @return the color
	 */
	public CatanColor getColor()
	{
		return color;
	}


	/**
	 * @param color the color to set
	 */
	public void setColor(CatanColor color)
	{
		this.color = color;
	}


	/**
	 * @return the hasDiscarded
	 */
	public boolean isHasDiscarded()
	{
		return hasDiscarded;
	}


	/**
	 * @param hasDiscarded the hasDiscarded to set
	 */
	public void setHasDiscarded(boolean hasDiscarded)
	{
		this.hasDiscarded = hasDiscarded;
	}


	/**
	 * @return the numMonuments
	 */
	public int getNumMonuments()
	{
		return numMonuments;
	}


	/**
	 * @param numMonuments the numMonuments to set
	 */
	public void setNumMonuments(int numMonuments)
	{
		this.numMonuments = numMonuments;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @return the oldNetDevCardList
	 */
	public NetDevCardList getOldNetDevCardList()
	{
		return oldNetDevCardList;
	}


	/**
	 * @param oldNetDevCardList the oldNetDevCardList to set
	 */
	public void setOldNetDevCardList(NetDevCardList oldNetDevCardList)
	{
		this.oldNetDevCardList = oldNetDevCardList;
	}


	/**
	 * @return the newNetDevCardList
	 */
	public NetDevCardList getNewNetDevCardList()
	{
		return newNetDevCardList;
	}


	/**
	 * @param newNetDevCardList the newNetDevCardList to set
	 */
	public void setNewNetDevCardList(NetDevCardList newNetDevCardList)
	{
		this.newNetDevCardList = newNetDevCardList;
	}


	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex()
	{
		return playerIndex;
	}


	/**
	 * @param playerIndex the playerIndex to set
	 */
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}


	/**
	 * @return the playedDevCard
	 */
	public boolean hasPlayedDevCard()
	{
		return playedDevCard;
	}


	/**
	 * @param playedDevCard2 the playedDevCard to set
	 */
	public void setPlayedDevCard(boolean playedDevCard2)
	{
		this.playedDevCard = playedDevCard2;
	}


	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}


	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}


	/**
	 * @return the netResourceList
	 */
	public NetResourceList getNetResourceList()
	{
		return netResourceList;
	}


	/**
	 * @param netResourceList the netResourceList to set
	 */
	public void setNetResourceList(NetResourceList netResourceList)
	{
		this.netResourceList = netResourceList;
	}


	/**
	 * @return the numRoads
	 */
	public int getNumRoads()
	{
		return numRoads;
	}


	/**
	 * @param numRoads the numRoads to set
	 */
	public void setNumRoads(int numRoads)
	{
		this.numRoads = numRoads;
	}


	/**
	 * @return the numSoldiers
	 */
	public int getNumSoldiers()
	{
		return numSoldiers;
	}


	/**
	 * @param numSoldiers the numSoldiers to set
	 */
	public void setNumSoldiers(int numSoldiers)
	{
		this.numSoldiers = numSoldiers;
	}


	/**
	 * @return the numVictoryPoints
	 */
	public int getNumVictoryPoints()
	{
		return numVictoryPoints;
	}


	/**
	 * @param numVictoryPoints the numVictoryPoints to set
	 */
	public void setNumVictoryPoints(int numVictoryPoints)
	{
		this.numVictoryPoints = numVictoryPoints;
	}


	/**
	 * @return the numSettlements
	 */
	public int getNumSettlements()
	{
		return numSettlements;
	}


	/**
	 * @param numSettlements the numSettlements to set
	 */
	public void setNumSettlements(int numSettlements)
	{
		this.numSettlements = numSettlements;
	}
}

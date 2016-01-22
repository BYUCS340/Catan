package shared.model;

import java.util.Arrays;

import shared.definitions.*;

/**
 * The bank class the holds cards, pieces, and dev cards
 * @author matthewcarlson, garrettegan
 *
 */
public class Bank
{
	private int[] resources;
	private int[] devCards;
	private int[] pieces;
	
	/**
	 * Creates a bank
	 */
	public Bank() {
		super();
		this.resources = new int[5];
		this.devCards = new int[6];
		this.pieces = new int[3];
	}
	
	/**
	 * Attempts to get a resource from the bank
	 * @param type the type requested
	 * @throws if there don't have the resource
	 * @return true or false depending on whether it was successful or not
	 */
	public boolean getResource(ResourceType type) throws ModelException
	{
		int resourceIndex = type.ordinal();
		if (this.resources[resourceIndex] > 0)
		{
			this.resources[resourceIndex]--;
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Attempts to get a piece (road, settlement, city)
	 * @param type it can be anything but the robber
	 * @throws if there don't have the resource
	 */
	public boolean getPiece(PieceType type) throws ModelException
	{
		if (type == PieceType.ROBBER) return false;
		return false;
	}
	/**
	 * This gets a dev card which by default are shuffled when given. 
	 * @throws if there don't have the resource
	 * @return the type of the card you received or UNKNOWN if there are no more cards left
	 */
	public DevCardType getDevCard() throws ModelException
	{
		return DevCardType.UNKNOWN;
	}
	
	/**
	 * Gives the bank one resource of type
	 * @throws if there don't have the resource
	 * @param type the resource given
	 */
	public void giveResource(ResourceType type)
	{
		
	}
	
	/**
	 * Gives the bank one piece
	 * @param type
	 */
	public void givePiece(PieceType type)
	{
		
	}
	
	/**
	 * Gives the bank one dev card- it will be shuffled into pile
	 * @param type
	 */
	public void giveDevCard(DevCardType type)
	{
		
	}
	
	/**
	 * Gets the current number of all held resources
	 * @return the number of resources
	 */
	public int getResourceCount()
	{
		return 0;
	}
	
	/**
	 * returns the number of resources for a specified type
	 * @param type
	 * @return
	 */
	public int getResourceCount(ResourceType type)
	{
		return 0;
	}
	
	/**
	 * Gets the number of pieces available for a type held
	 * @param type
	 * @return
	 */
	public int getPiecesCount(PieceType type)
	{
		return 0;
	}
	
	/**
	 * Gets the number of dev cards of a type held
	 * @param type
	 * @return
	 */
	public int getDevCardCount(DevCardType type)
	{
		return 0;
	}
	
	/**
	 * Checks if has enough cards to build a road (one wood, one brick, one road)
	 * @return true if allowed, false otherwise
	 * @throws if there don't have the resource
	 */
	public boolean canBuildRoad()
	{
		int woodIndex = ResourceType.WOOD.ordinal();
		int brickIndex = ResourceType.BRICK.ordinal();
		int roadIndex = PieceType.ROAD.ordinal();
		
		if (this.resources[woodIndex] > 0 && this.resources[brickIndex] > 0
				&& this.pieces[roadIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to build a road (one wood, one brick, one road)
	 * @throws if there don't have the resource
	 */
	public boolean buildRoad() throws ModelException
	{
		if (canBuildRoad())
		{
			int woodIndex = ResourceType.WOOD.ordinal();
			int brickIndex = ResourceType.BRICK.ordinal();
			int roadIndex = PieceType.ROAD.ordinal();
			
			this.resources[woodIndex]--;
			this.resources[brickIndex]--;
			this.pieces[roadIndex]--;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if has enough cards to build a settlement (one wood, one brick, one sheep, one wheat, one settlement)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuildSettlement()
	{
		int woodIndex = ResourceType.WOOD.ordinal();
		int brickIndex = ResourceType.BRICK.ordinal();
		int sheepIndex = ResourceType.SHEEP.ordinal();
		int wheatIndex = ResourceType.WHEAT.ordinal();
		int settlementIndex = PieceType.SETTLEMENT.ordinal();
		
		if (this.resources[woodIndex] > 0 && this.resources[brickIndex] > 0
				&& this.resources[sheepIndex] > 0 && this.resources[wheatIndex] > 0
				&& this.pieces[settlementIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to build a settlement (one wood, one brick, one sheep, one wheat, one settlement)
	 * @return true if successful, false otherwise
	 * @throws if there don't have the resource
	 */
	public boolean buildSettlement() throws ModelException
	{
		if (canBuildSettlement())
		{
			int woodIndex = ResourceType.WOOD.ordinal();
			int brickIndex = ResourceType.BRICK.ordinal();
			int sheepIndex = ResourceType.SHEEP.ordinal();
			int wheatIndex = ResourceType.WHEAT.ordinal();
			int settlementIndex = PieceType.SETTLEMENT.ordinal();
			
			this.resources[woodIndex]--;
			this.resources[brickIndex]--;
			this.resources[sheepIndex]--;
			this.resources[wheatIndex]--;
			this.pieces[settlementIndex]--;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if has enough cards to build a city (two wheat, three ore, one city)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuildCity()
	{
		int wheatIndex = ResourceType.WHEAT.ordinal();
		int oreIndex = ResourceType.ORE.ordinal();
		int cityIndex = PieceType.CITY.ordinal();
		
		if (this.resources[wheatIndex] > 1 && this.resources[oreIndex] > 2
				&& this.pieces[cityIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to build a city (two wheat, three ore, one city)
	 * @throws if there don't have the resource
	 */
	public boolean buildCity() throws ModelException
	{
		if (canBuildCity())
		{
			int wheatIndex = ResourceType.WHEAT.ordinal();
			int oreIndex = ResourceType.ORE.ordinal();
			int cityIndex = PieceType.CITY.ordinal();
			int settlementIndex = PieceType.SETTLEMENT.ordinal();
			
			this.resources[wheatIndex] -= 2;
			this.resources[oreIndex] -= 3;
			this.pieces[cityIndex]--;
			this.pieces[settlementIndex]++;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if has enough cards to buy a development card (one wheat, one sheep, one ore)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuyDevCard()
	{
		int wheatIndex = ResourceType.WHEAT.ordinal();
		int sheepIndex = ResourceType.SHEEP.ordinal();
		int oreIndex = ResourceType.ORE.ordinal();
		
		if (this.resources[wheatIndex] > 0 && this.resources[sheepIndex] > 0
				&& this.resources[oreIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to buy a development card (one wheat, one sheep, one ore)
	 * @throws if there don't have the resource
	 */
	public boolean buyDevCard() throws ModelException
	{
		if (canBuyDevCard())
		{
			int wheatIndex = ResourceType.WHEAT.ordinal();
			int sheepIndex = ResourceType.SHEEP.ordinal();
			int oreIndex = ResourceType.ORE.ordinal();
			
			this.resources[wheatIndex]--;
			this.resources[sheepIndex]--;
			this.resources[oreIndex]--;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(devCards);
		result = prime * result + Arrays.hashCode(pieces);
		result = prime * result + Arrays.hashCode(resources);
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
		if (!(obj instanceof Bank))
			return false;
		Bank other = (Bank) obj;
		if (!Arrays.equals(devCards, other.devCards))
			return false;
		if (!Arrays.equals(pieces, other.pieces))
			return false;
		if (!Arrays.equals(resources, other.resources))
			return false;
		return true;
	}
}

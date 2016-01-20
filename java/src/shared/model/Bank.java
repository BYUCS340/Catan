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
	 * @return true or false depending on whether it was successful or not
	 */
	public boolean getResource(ResourceType type)
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
	 * @return true or false depending on success
	 */
	public boolean getPiece(PieceType type)
	{
		if (type == PieceType.ROBBER) return false;
		return false;
	}
	/**
	 * This gets a dev card which by default are shuffled when given. 
	 * @return the type of the card you received or UNKNOWN if there are no more cards left
	 */
	public DevCardType getDevCard()
	{
		return DevCardType.UNKNOWN;
	}
	
	/**
	 * Gives the bank one resource of type
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

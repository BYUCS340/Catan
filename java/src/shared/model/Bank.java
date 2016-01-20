package shared.model;

import java.util.Arrays;

import shared.definitions.*;

public class Bank
{
	private int[] resources;
	private int[] devCards;
	private int[] pieces;
	
	/**
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
	public boolean getPiece(PieceType type)
	{
		return false;
	}
	public DevCardType getDevCard()
	{
		return DevCardType.UNKNOWN;
	}
	public void giveResource(ResourceType type)
	{
		
	}
	public void givePiece(PieceType type)
	{
		
	}
	public void giveDevCard(DevCardType type)
	{
		
	}
	public int getResourceCount()
	{
		return 0;
	}
	public int getResourceCount(ResourceType type)
	{
		return 0;
	}
	public int getPiecesCount(PieceType type)
	{
		return 0;
	}
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

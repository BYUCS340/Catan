package shared.model;

import shared.definitions.*;

public class Bank
{
	private int[] resources;
	private int[] devCards;
	private int[] pieces;
	
	
	public boolean getResource(ResourceType type)
	{
		return false;
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
}

package shared.model;

import shared.definitions.CatanColor;

public class Player
{
	public String name;
	public CatanColor color;
	public Bank playerBank;
	private int playerIndex;
	
	public boolean buildRoad()
	{
		return false;
	}
	public boolean buildSettlement()
	{
		return false;
	}
	public boolean buildCity()
	{
		return false;
	}
}

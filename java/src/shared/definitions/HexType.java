package shared.definitions;

import java.io.Serializable;

public enum HexType implements Serializable
{
	
	WOOD, BRICK, SHEEP, WHEAT, ORE, DESERT, WATER();
	
	public static HexType GetFromResource(ResourceType type)
	{
		switch (type)
		{
			case WOOD: return WOOD;
			case BRICK: return BRICK;
			case SHEEP: return SHEEP;
			case WHEAT: return WHEAT;
			case ORE: return ORE;
			default: return null;
		}
		
	}
}


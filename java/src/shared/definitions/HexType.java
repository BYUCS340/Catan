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

	public ResourceType toResource() {
		if (this == WOOD) return ResourceType.WOOD;
		if (this == ORE) return ResourceType.ORE;
		if (this == BRICK) return ResourceType.BRICK;
		if (this == WHEAT) return ResourceType.WHEAT;
		if (this == SHEEP) return ResourceType.SHEEP;
		// TODO Auto-generated method stub
		return null;
	}
}


package shared.definitions;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE();
	
	public static String toString(ResourceType type)
	{
		switch(type)
		{
			case WOOD: return "wood";
			case BRICK: return "brick";
			case WHEAT: return "wheat";
			case SHEEP: return "sheep";
			case ORE: return "ore";
		}
		return "unknown";
	}

	public static ResourceType fromString(String s)
	{
		switch(s.toLowerCase())
		{
			case "wood": return ResourceType.WOOD;
			case "brick": return ResourceType.BRICK;
			case "wheat": return ResourceType.WHEAT;
			case "sheep": return ResourceType.SHEEP;
			case "ore": return ResourceType.ORE;
		}
		return null;
	}
	
	public static ResourceType fromHex(HexType hex){
		switch (hex)
		{
			case ORE:   return ResourceType.ORE;
			case BRICK: return ResourceType.BRICK;
			case SHEEP: return ResourceType.SHEEP;
			case WHEAT: return ResourceType.WHEAT;
			case WOOD:  return ResourceType.WOOD;
		
			case WATER:
			case DESERT:
			default:
				return null;
		}
	}
}





package shared.definitions;

public enum PortType
{
	
	WOOD, BRICK, SHEEP, WHEAT, ORE, THREE, NONE();
	
	public static PortType GetFromResource(ResourceType type)
	{
		switch (type)
		{
		case WOOD:
			return WOOD;
		case BRICK:
			return BRICK;
		case SHEEP:
			return SHEEP;
		case WHEAT:
			return WHEAT;
		case ORE:
			return ORE;
		}
		
		return null;
	}
}


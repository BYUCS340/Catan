package shared.definitions;

import java.io.Serializable;

public enum PortType implements Serializable
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


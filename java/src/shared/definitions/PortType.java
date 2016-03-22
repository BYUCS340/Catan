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
	
	public static boolean MatchedResource(PortType port, ResourceType type){
		if (port == WOOD && type == ResourceType.WOOD) return true;
		if (port == BRICK && type == ResourceType.BRICK) return true;
		if (port == SHEEP && type == ResourceType.SHEEP) return true;
		if (port == WHEAT && type == ResourceType.WHEAT) return true;
		if (port == ORE && type == ResourceType.ORE) return true;
		
		return false;
	}

}


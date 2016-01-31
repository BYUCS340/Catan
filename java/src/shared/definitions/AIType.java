package shared.definitions;

public enum AIType
{
	LARGEST_ARMY;
	
	public static String toString(AIType type)
	{
		switch(type)
		{
			case LARGEST_ARMY : return "largest_army";
		}
		return "unknown";
	}
	
	public static AIType fromString(String type)
	{
		switch(type.toLowerCase())
		{
			case "largest_army": return AIType.LARGEST_ARMY;
		}
		
		return null;
	}
}

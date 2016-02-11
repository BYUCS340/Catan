package shared.definitions;

public enum AIType
{
	LARGEST_ARMY;
	
	public static String toString(AIType type)
	{
		switch(type)
		{
			case LARGEST_ARMY : return "LARGEST_ARMY";
		}
		return "unknown";
	}
	
	public static AIType fromString(String type)
	{
		switch(type.toLowerCase())
		{
			case "LARGEST_ARMY": return AIType.LARGEST_ARMY;
		}
		
		return null;
	}
	
	
}

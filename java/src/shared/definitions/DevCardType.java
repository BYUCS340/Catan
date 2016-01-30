package shared.definitions;

public enum DevCardType
{
	
	SOLDIER, YEAR_OF_PLENTY, MONOPOLY, ROAD_BUILD, MONUMENT();
	
	public static DevCardType fromInt(int i)
	{
		switch(i)
		{
			case 0: return DevCardType.SOLDIER;
			case 1: return DevCardType.YEAR_OF_PLENTY;
			case 2: return DevCardType.MONOPOLY;
			case 3: return DevCardType.ROAD_BUILD;
			case 4: return DevCardType.MONUMENT;
		}
		return DevCardType.MONOPOLY;
	}
	
	public static String toString(DevCardType type)
	{
		switch(type)
		{
			case SOLDIER: return "Solider";
			case YEAR_OF_PLENTY: return "Year of Plenty";
			case MONOPOLY: return "Monopoly";
			case ROAD_BUILD: return "Road Builder";
			case MONUMENT: return "Monument";
		}
		return "Unknown";
	}
	
	public static DevCardType fromString(String s)
	{
		switch(s)
		{
			case "Solider": return DevCardType.SOLDIER;
			case "Year of Plenty": return DevCardType.YEAR_OF_PLENTY;
			case "Monopoly": return DevCardType.MONOPOLY;
			case "Road Builder": return DevCardType.ROAD_BUILD;
			case "Monument": return DevCardType.MONUMENT;
		}
		return DevCardType.MONOPOLY;
	}
}


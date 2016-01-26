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
}


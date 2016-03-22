package shared.definitions;

public enum AIType
{
	BEGINNER, /*MODERATE, EXPERT,*/ RANDOM, OBNOXIOUS;
	
	public static String toString(AIType type)
	{
		switch(type)
		{
		case BEGINNER: return "BEGINNER";
//		case MODERATE: return "MODERATE";
//		case EXPERT: return "EXPERT";

		case OBNOXIOUS: return "OBNOXIOUS";
		case RANDOM: 
		default:
			return "RANDOM";
		
		}
	}
	
	public static AIType fromString(String type)
	{
		switch(type.toUpperCase())
		{
		case "BEGINNER": return BEGINNER;
//		case "MODERATE": return MODERATE;
//		case "EXPERT": return EXPERT;		
		case "OBNOXIOUS": return OBNOXIOUS;
		case "RANDOM": 
		default:
			return RANDOM;
		}
		
	}
	
	
}

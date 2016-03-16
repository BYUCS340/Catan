package shared.definitions;

public enum AIType
{
	BEGINNER, MODERATE, EXPERT, RANDOM, OBNOXIOUS;
	
	public static String toString(AIType type)
	{
		switch(type)
		{
		case BEGINNER: return "BEGINNER";
		case MODERATE: return "MODERATE";
		case EXPERT: return "EXPERT";
		case RANDOM: return "RANDOM";
		case OBNOXIOUS: return "OBNOXIOUS";
		}
		
		assert false;
		return "unknown";
	}
	
	public static AIType fromString(String type)
	{
		switch(type.toUpperCase())
		{
		case "BEGINNER": return BEGINNER;
		case "MODERATE": return MODERATE;
		case "EXPERT": return EXPERT;
		case "RANDOM": return RANDOM;
		case "OBNOXIOUS": return OBNOXIOUS;
		}
		
		assert false;
		return null;
	}
	
	
}

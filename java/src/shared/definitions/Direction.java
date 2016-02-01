package shared.definitions;

public enum Direction
{
	NW, N, NE, SW, S, SE();
	
	public static String toString(Direction type)
	{
		switch(type)
		{
			case NW: return "nw";
			case N: return "n";
			case NE: return "ne";
			case SW: return "sw";
			case S: return "s";
			case SE: return "se";
		}
		return "unknown";
	}
	
	public static Direction fromString(String s)
	{
		switch(s.toLowerCase())
		{
			case "nw": return Direction.NW;
			case "n" : return Direction.N;
			case "ne": return Direction.NE;
			case "s" : return Direction.S;
			case "se": return Direction.SE;
		}
		return null;
	}
}

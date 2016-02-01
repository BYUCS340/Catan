package shared.locations;

public enum VertexDirection
{
	West, NorthWest, NorthEast, East, SouthEast, SouthWest;
	
	private VertexDirection opposite;
	
	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}
	
	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}
	
	public static String toString(VertexDirection e)
	{
		switch(e)
		{
			case NorthWest : return "nw";
			case West : return "w";
			case NorthEast : return "ne";
			case SouthEast : return "se";
			case SouthWest : return "sw";
			case East : return "e";
		}
		return "unknown";
	}
}


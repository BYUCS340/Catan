package shared.locations;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
	
	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
	}
	
	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
	
	public static String toString(EdgeDirection e)
	{
		switch(e)
		{
			case NorthWest : return "nw";
			case North : return "n";
			case NorthEast : return "ne";
			case SouthEast : return "se";
			case SouthWest : return "sw";
			case South : return "s";
		}
		return "unknown";
	}
}


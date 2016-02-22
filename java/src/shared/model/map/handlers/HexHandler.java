package shared.model.map.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import shared.model.map.*;
import shared.model.map.objects.Hex;

/**
 * Stores and manages the hexes contained in a map.
 * @author Jonathan Sadler
 *
 */
public class HexHandler {

	private static final int MAX_SIZE = 37;
	private static final int Y_SHIFT = 6;
	
	private Map<Integer, Hex> hexes;
	
	/**
	 * Creates a HexHandler object.
	 */
	public HexHandler()
	{
		hexes = new HashMap<Integer, Hex>(MAX_SIZE);
	}
	
	/**
	 * Adds a hex object to the handler.
	 * @param hex The hex to add.
	 * @throws MapException Thrown if attempting to add to many hexes or duplicate hexes.
	 */
	public void AddHex(Hex hex) throws MapException
	{
		if (hexes.size() == MAX_SIZE)
			throw new MapException("Attempt to add too many hexes to board.");
		
		int key = GetKey(hex);
		
		if (hexes.containsKey(key))
			throw new MapException("Attempt to overwrite existing hex.");
		
		hexes.put(key, hex);
	}
	
	/**
	 * Determines if a hex is contained at the location.
	 * @param x The coordinate of the hex.
	 * @return True if yes, else false.
	 */
	public boolean ContainsHex(Coordinate point)
	{
		return hexes.containsKey(GetKey(point));
	}
	
	/**
	 * Gets the hex at the given coordinate.
	 * @param x The coordinate of the hex.
	 * @return The assocaited hex.
	 * @throws MapException 
	 */
	public Hex GetHex(Coordinate point) throws MapException
	{
		if (ContainsHex(point))
			return hexes.get(GetKey(point));
		else
			throw new MapException("Like Genosis, the requested hex could not be found. "
					+ "It must not exist.");
	}
	
	/**
	 * Gets all the hexes in the map.
	 * @return A iterator to all the hexes.
	 */
	public Iterator<Hex> GetAllHexes()
	{
		return java.util.Collections.unmodifiableCollection(hexes.values()).iterator();
	}
	
	public static Coordinate GetTopLeft(Coordinate point)
	{
		return point.GetNorth();
	}
	
	public static Coordinate GetTopRight(Coordinate point)
	{
		return point.GetNorthEast();
	}
	
	public static Coordinate GetRight(Coordinate point)
	{
		return point.GetEast();
	}
	
	public static Coordinate GetBottomRight(Coordinate point)
	{
		return point.GetSouthEast();
	}
	
	public static Coordinate GetBottomLeft(Coordinate point)
	{
		return point.GetSouth();
	}
	
	public static Coordinate GetLeft(Coordinate point)
	{
		return new Coordinate(point.getX(), point.getY());
	}
	
	private int GetKey(Hex hex)
	{
		return GetKey(hex.getPoint());
	}
	
	private int GetKey(Coordinate point)
	{
		return 100 * point.getX() + point.getY() + Y_SHIFT;
	}
}

package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.objects.Hex;

/**
 * Stores and manages the hexes contained in a map.
 * @author Jonathan Sadler
 *
 */
public class HexHandler {

	private static final int INITIAL_CAPACITY = 37;
	private static final int Y_SHIFT = 6;
	
	private Map<Integer, Hex> hexes;
	
	/**
	 * Creates a HexHandler object.
	 */
	public HexHandler()
	{
		hexes = new HashMap<Integer, Hex>(INITIAL_CAPACITY);
	}
	
	/**
	 * Adds a hex object to the handler.
	 * @param hex The hex to add.
	 */
	public void AddHex(Hex hex)
	{
		//Todo Add exception for too many hexes
		int key = GetKey(hex);
		hexes.put(key, hex);
	}
	
	/**
	 * Determines if a hex is contained at the location.
	 * @param x X coordinate.
	 * @param y Y Coordinate.
	 * @return True if yes, else false.
	 */
	public boolean ContainsHex(int x, int y)
	{
		return hexes.containsKey(GetKey(x, y));
	}
	
	/**
	 * Gets the hex at the given Y coordinate.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return The assocaited hex.
	 */
	public Hex GetHex(int x, int y)
	{
		return hexes.get(GetKey(x, y));
	}
	
	private int GetKey(Hex hex)
	{
		int x = hex.getxLocation();
		int y = hex.getyLocation();
		
		return GetKey(x, y);
	}
	
	private int GetKey(int x, int y)
	{
		assert x >= 0;
		assert y + Y_SHIFT >= 0;
		
		return 100 * x + y + Y_SHIFT;
	}
}

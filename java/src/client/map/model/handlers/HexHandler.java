package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.Coordinate;
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
	 */
	public Hex GetHex(Coordinate point)
	{
		return hexes.get(GetKey(point));
	}
	
	private int GetKey(Hex hex)
	{
		return GetKey(hex.getPoint());
	}
	
	private int GetKey(Coordinate point)
	{
//		assert x >= 0;
//		assert y + Y_SHIFT >= 0;
		
		return 100 * point.getX() + point.getY() + Y_SHIFT;
	}
}

package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.objects.Hex;

public class HexHandler {

	private static final int INITIAL_CAPACITY = 37;
	private static final int Y_SHIFT = 6;
	
	private Map<Integer, Hex> hexes;
	
	public HexHandler()
	{
		hexes = new HashMap<Integer, Hex>(INITIAL_CAPACITY);
	}
	
	public void AddHex(Hex hex)
	{
		//Todo Add exception for too many hexes
		int key = GetKey(hex);
		hexes.put(key, hex);
	}
	
	public boolean ContainsHex(int x, int y)
	{
		return hexes.containsKey(GetKey(x, y));
	}
	
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

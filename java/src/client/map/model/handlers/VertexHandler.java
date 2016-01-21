package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.objects.Vertex;

/**
 * Stores and manages the vertices found in a map.
 * @author Jonathan Sadler
 *
 */
public class VertexHandler {

	private static final int INITIAL_CAPACITY = 54;
	private static final int Y_SHIFT = 5;
	
	private Map<Integer, Vertex> verticies;
	
	/**
	 * Creates a VertexHandler object.
	 */
	public VertexHandler()
	{
		verticies = new HashMap<Integer, Vertex>(INITIAL_CAPACITY);
		
		for (int x = 1; x <= 6; x++)
		{
			int yLimit = (int) (-Math.abs(x - 3.5) + 5.5);
			for (int y = -yLimit; y <= yLimit; y++)
				verticies.put(GetKey(x, y), new Vertex(x, y));
		}
	}
	
	/**
	 * Gets the vertex at the specified coordinate.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return The assocatied vertex.
	 */
	public Vertex GetVertex(int x, int y)
	{
		return verticies.get(GetKey(x, y));
	}
	
	private int GetKey(int x, int y)
	{
		assert x >= 0;
		assert y + Y_SHIFT >= 0;
		
		return x * 100 + y + Y_SHIFT;
	}
}

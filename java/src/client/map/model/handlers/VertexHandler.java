package client.map.model.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import client.map.MapException;
import client.map.model.Coordinate;
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
			{
				Coordinate point = new Coordinate(x, y);
				verticies.put(GetKey(point), new Vertex(point));
			}
		}
	}
	
	public boolean ContainsVertex(Coordinate point)
	{
		return verticies.containsKey(GetKey(point));
	}
	
	/**
	 * Gets all the registered verticies.
	 * @return The vertex list.
	 */
	public Iterator<Vertex> GetVerticies()
	{
		return java.util.Collections.unmodifiableCollection(verticies.values()).iterator();
	}
	
	/**
	 * Gets the vertex at the specified coordinate.
	 * @param point The coordinate of the vertex.
	 * @return The assocatied vertex.
	 * @throws MapException Thrown if the vertex doesn't exist.
	 */
	public Vertex GetVertex(Coordinate point) throws MapException
	{
		if (ContainsVertex(point))
			return verticies.get(GetKey(point));
		else
			throw new MapException("The requested vertex doesn't exist.");
	}
	
	private int GetKey(Coordinate point)
	{
		return point.getX() * 100 + point.getY() + Y_SHIFT;
	}
}

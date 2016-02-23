package shared.model.map.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import shared.definitions.*;
import shared.model.map.*;
import shared.model.map.objects.Vertex;

/**
 * Stores and manages the vertices found in a map.
 * @author Jonathan Sadler
 *
 */
public class VertexHandler
{
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
	
	/**
	 * Determines if there is a vertex associated with the coordinate.
	 * @param point The coordinate to check.
	 * @return True if one exists, else false.
	 */
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
	
	/**
	 * Removes a city or settlement from the map.
	 * @param point The coordinate to remove it from.
	 * @throws MapException Why the village couldn't be removed.
	 */
	public void ClearVillage(Coordinate point) throws MapException
	{
		GetVertex(point).ClearType();
	}
	
	/**
	 * Adds a settlement to the map.
	 * @param point The point to add the settlement to.
	 * @param color The color of the settlement (this sounds a little racist ... don't take it
	 * 				out of context).
	 * @throws MapException Why the settlement couldn't be added.
	 */
	public void SetSettlement(Coordinate point, CatanColor color) throws MapException
	{
		GetVertex(point).SetType(PieceType.SETTLEMENT, color);
	}
	
	/**
	 * Adds a city to the map.
	 * @param point The point to add the city to.
	 * @param color The color of the city.
	 * @throws MapException Why the city couldn't be added.
	 */
	public void SetCity(Coordinate point, CatanColor color) throws MapException
	{
		GetVertex(point).SetType(PieceType.CITY, color);
	}
	
	private int GetKey(Coordinate point)
	{
		return point.getX() * 100 + point.getY() + Y_SHIFT;
	}
}

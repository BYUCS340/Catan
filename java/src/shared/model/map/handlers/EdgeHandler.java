package shared.model.map.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.objects.Edge;

/**
 * Stores and manages the edges contained in a map.
 * @author Jonathan Sadler
 *
 */
public class EdgeHandler {

	private static final int INITIAL_CAPACITY = 72;
	private static final int Y_SHIFT = 6;
	
	private Map<Integer, Edge> edges;
	
	/**
	 * Creates a EdgeHandler object.
	 */
	public EdgeHandler()
	{
		edges = new HashMap<Integer, Edge>(INITIAL_CAPACITY);
		
		Coordinate p1;
		Coordinate p2;
		
		//Place Vertical Edges
		for (int x = 1; x <= 6; x++)
		{
			int yLimit = (int) (-Math.abs(x - 3.5) + 5.5);
			
			for (int y = 0; y < yLimit; y++)
			{
				p1 = new Coordinate(x, y);
				p2 = new Coordinate(x, y+1);
				edges.put(GetKey(p1, p2), new Edge(p1, p2));
				
				p1 = new Coordinate(x, -y);
				p2 = new Coordinate(x, -y - 1);
				edges.put(GetKey(p1, p2), new Edge(p1, p2));
			}
		}
		
		//Place Horizontal Edges
		for (int x = 1; x < 6; x++)
		{
			int yLimit = (int) (-Math.abs(x - 3.5) + 5.5);
			
			for (int y = 0; y <= yLimit; y++)
			{
				if ((x + y) % 2 == 1)
					continue;
				
				p1 = new Coordinate(x, y);
				p2 = new Coordinate(x + 1, y);
				edges.put(GetKey(p1, p2), new Edge(p1, p2));
				
				if (y != 0)
				{
					p1 = new Coordinate(x, -y);
					p2 = new Coordinate(x + 1, -y);
					edges.put(GetKey(p1, p2), new Edge(p1, p2));
				}
			}
		}
	}
	
	/**
	 * Determines if an edge exists. Point order doesn't matter.
	 * @param p1 The coordinate of the first end point.
	 * @param p2 The coordinate of the second end point.
	 * @return True if the edge exists, else false.
	 */
	public Boolean ContainsEdge(Coordinate p1, Coordinate p2)
	{
		return edges.containsKey(GetKey(p1, p2));
	}
	
	/**
	 * Returns the edge between the provided end points. Point order doesn't matter.
	 * @param p1 The coordinate of the first end point.
	 * @param p2 The coordinate of the second end point.
	 * @return The associated edge.
	 * @throws MapException Thrown if the edge isn't found
	 */
	public Edge GetEdge(Coordinate p1, Coordinate p2) throws MapException
	{
		if (ContainsEdge(p1, p2))
			return edges.get(GetKey(p1, p2));
		else
			throw new MapException("The requested edge doesn't exist.");
	}
	
	/**
	 * Returns all the edges 
	 * @return The edges. 'Nuff said.
	 */
	public Iterator<Edge> GetAllEdges()
	{
		return java.util.Collections.unmodifiableCollection(edges.values()).iterator();
	}
	
	/**
	 * Adds a road to the map.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @param color The color of the road.
	 * @throws MapException Why the road couldn't be built.
	 */
	public void AddRoad(Coordinate p1, Coordinate p2, CatanColor color) throws MapException
	{
		GetEdge(p1, p2).SetRoad(color);
	}
	
	/**
	 * Removes a road from the map.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @throws MapException Why the road couldn't be removed.
	 */
	public void ClearRoad(Coordinate p1, Coordinate p2) throws MapException
	{
		GetEdge(p1, p2).ClearRoad();
	}
	
	private int GetKey(Coordinate p1, Coordinate p2)
	{
		if (ReverseOrder(p1, p2))
			return ComputeKey(p2, p1);
		else
			return ComputeKey(p1, p2);
	}
	
	private boolean ReverseOrder(Coordinate p1, Coordinate p2)
	{
		if (p1.getX() == p2.getX())
			return p1.getY() > p2.getY();
		else
			return p1.getX() > p2.getX();
	}
	
	private int ComputeKey(Coordinate p1, Coordinate p2)
	{
		return 1000000 * p1.getX() + 10000 * (p1.getY() + Y_SHIFT)
				+ 100 * p2.getX() + (p2.getY() + Y_SHIFT);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EdgeHandler))
			return false;
		EdgeHandler other = (EdgeHandler) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		return true;
	}
}

package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.Coordinate;
import client.map.model.objects.Edge;

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
				edges.put(GetKey(p1, p2), new Edge());
				
				p1 = new Coordinate(x, -y);
				p2 = new Coordinate(x, -y - 1);
				edges.put(GetKey(p1, p2), new Edge());
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
				edges.put(GetKey(p1, p2), new Edge());
				
				if (y != 0)
				{
					p1 = new Coordinate(x, -y);
					p2 = new Coordinate(x + 1, -y);
					edges.put(GetKey(p1, p2), new Edge());
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
	 */
	public Edge GetEdge(Coordinate p1, Coordinate p2)
	{
		return edges.get(GetKey(p1, p2));
	}
	
	private int GetKey(Coordinate p1, Coordinate p2)
	{
//		assert x1 >= 0;
//		assert y1 + Y_SHIFT >= 0;
//		assert x2 >= 0;
//		assert y2 + Y_SHIFT >= 0;
		
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
}

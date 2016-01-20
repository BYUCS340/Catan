package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.objects.Edge;

public class EdgeHandler {

	private static final int INITIAL_CAPACITY = 72;
	private static final int Y_SHIFT = 6;
	
	private Map<Integer, Edge> edges;
	
	public EdgeHandler()
	{
		edges = new HashMap<Integer, Edge>(INITIAL_CAPACITY);
		
		//Place Vertical Edges
		for (int x = 1; x <= 6; x++)
		{
			int yLimit = (int) (-Math.abs(x - 3.5) + 5.5);
			
			for (int y = 0; y < yLimit; y++)
			{
				edges.put(GetKey(x, y, x, y + 1), new Edge());
				edges.put(GetKey(x, -y, x, -y - 1), new Edge());
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
				
				edges.put(GetKey(x, y, x + 1, y), new Edge());
				
				if (y != 0)
					edges.put(GetKey(x, -y, x + 1, -y), new Edge());
			}
		}
	}
	
	public Boolean ContainsEdge(int x1, int y1, int x2, int y2)
	{
		return edges.containsKey(GetKey(x1, y1, x2, y2));
	}
	
	public Edge GetEdge(int x1, int y1, int x2, int y2)
	{
		return edges.get(GetKey(x1, y1, x2, y2));
	}
	
	private int GetKey(int x1, int y1, int x2, int y2)
	{
		assert x1 >= 0;
		assert y1 + Y_SHIFT >= 0;
		assert x2 >= 0;
		assert y2 + Y_SHIFT >= 0;
		
		if (ReverseOrder(x1, y1, x2, y2))
			return ComputeKey(x2, y2, x1, y1);
		else
			return ComputeKey(x1, y1, x2, y2);
	}
	
	private boolean ReverseOrder(int x1, int y1, int x2, int y2)
	{
		if (x1 == x2)
			return y1 > y2;
		else
			return x1 > x2;
	}
	
	private int ComputeKey(int x1, int y1, int x2, int y2)
	{
		return 1000000 * x1 + 10000 * (y1 + Y_SHIFT) + 100 * x2 + (y2 + Y_SHIFT);
	}
}

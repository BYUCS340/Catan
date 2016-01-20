package client.map.model.handlers;

import java.util.HashMap;
import java.util.Map;

import client.map.model.objects.Vertex;

public class VertexHandler {

	private static final int INITIAL_CAPACITY = 54;
	private static final int Y_SHIFT = 5;
	
	private Map<Integer, Vertex> verticies;
	
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
	
	private int GetKey(int x, int y)
	{
		assert x >= 0;
		assert y + Y_SHIFT >= 0;
		
		return x * 100 + y + Y_SHIFT;
	}
}

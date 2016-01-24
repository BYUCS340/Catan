package client.map.model.objects;

import client.map.model.Coordinate;
import shared.definitions.CatanColor;

/**
 * Used to store information about an edge.
 * @author Jonathan Sadler
 *
 */
public class Edge {

	private Coordinate start;
	private Coordinate end;
	
	private boolean roadExists;
	private CatanColor color;
	
	/**
	 * Creates an edge object.
	 */
	public Edge(Coordinate start, Coordinate end)
	{
		this.start = start;
		this.end = end;
		
		roadExists = false;
		color = null;
	}
	
	/**
	 * Removes any road associated with an edge.
	 */
	public void ClearRoad()
	{
		roadExists = false;
		color = null;
	}
	
	/**
	 * Places a road on the edge.
	 * @param color The color of the road.
	 */
	public void SetRoad(CatanColor color)
	{
		roadExists = true;
		this.color = color;
	}

	/**
	 * @return the roadExists
	 */
	public boolean doesRoadExists() {
		return roadExists;
	}

	/**
	 * @return the start
	 */
	public Coordinate getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Coordinate getEnd() {
		return end;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}

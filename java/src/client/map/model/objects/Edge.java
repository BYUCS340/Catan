package client.map.model.objects;

import shared.definitions.CatanColor;

/**
 * Used to store information about an edge.
 * @author Jonathan Sadler
 *
 */
public class Edge {

	private boolean roadExists;
	private CatanColor color;
	
	/**
	 * Creates an edge object.
	 */
	public Edge()
	{
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
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}

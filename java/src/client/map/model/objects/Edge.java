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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + (roadExists ? 1231 : 1237);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		
		if (color != other.color)
			return false;
		
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		
		if (roadExists != other.roadExists)
			return false;
		
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		
		return true;
	}
}

package shared.model.map.objects;

import java.io.Serializable;

import shared.definitions.CatanColor;
import shared.model.map.Coordinate;

/**
 * Used to store information about an edge.
 * @author Jonathan Sadler
 *
 */
public class Edge implements Serializable
{
	private static final long serialVersionUID = -1801636975705953331L;
	
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
	public boolean doesRoadExists()
	{
		return roadExists;
	}

	/**
	 * @return the start
	 */
	public Coordinate getStart()
	{
		return start;
	}

	/**
	 * @return the end
	 */
	public Coordinate getEnd()
	{
		return end;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() 
	{
		return color;
	}
	
	public int GetRotation(Hex hex)
	{
		Coordinate hexPoint = hex.getPoint();
		
		return GetRotation(hexPoint, start, end);
	}
	
	public static int GetRotation(Coordinate hex, Coordinate start, Coordinate end)
	{
		//Edge is horizontal
		if (start.getY() == end.getY())
		{
			if (start.getY() > hex.getY())
				return 180;
			else
				return 0;
		}
		//Edge is diagonal
		else
		{
			Coordinate primary = null;
			Coordinate secondary = null;
			if (start.getY() == hex.getY())
			{
				primary = start;
				secondary = end;
			}
			else
			{
				primary = end;
				secondary = start;
			}
			
			if (primary.getX() == hex.getX())
			{
				if (primary.getY() < secondary.getY())
					return 240;
				else
					return 300;
			}
			else
			{
				if (primary.getY() < secondary.getY())
					return 120;
				else
					return 60;
			}
		}
	}
	
	@Override
	public String toString() 
	{
		return "Edge" + start.toString() + end.toString();
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + (roadExists ? 1231 : 1237);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		
		if (color != other.color)
			return false;
		
		if (end == null) 
		{
			if (other.end != null)
				return false;
		} 
		else if (!end.equals(other.end))
			return false;
		
		if (roadExists != other.roadExists)
			return false;
		
		if (start == null) 
		{
			if (other.start != null)
				return false;
		} 
		else if (!start.equals(other.start))
			return false;
		
		return true;
	}
}

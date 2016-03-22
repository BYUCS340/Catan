package shared.model.map.objects;

import java.io.Serializable;

import shared.definitions.*;
import shared.model.map.Coordinate;

/**
 * Use to store information about a vertex.
 * @author Jonathan Sadler
 *
 */
public class Vertex implements Serializable
{
	private static final long serialVersionUID = 1806486130564577939L;

	private Coordinate point;
	
	private PieceType type;
	private CatanColor color;
	
	/**
	 * Creates a vertex object.
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Vertex(Coordinate point)
	{
		this.type = PieceType.NONE;
		this.color = null;
		
		this.point = point;
	}
	
	/**
	 * Place a piece on the vertex.
	 * @param type The type of piece being placed.
	 * @param color The color being placed.
	 */
	public void SetType(PieceType type, CatanColor color)
	{
		this.type = type;
		this.color = color;
	}
	
	/**
	 * Removes a piece from the vertex.
	 */
	public void ClearType()
	{
		this.type = PieceType.NONE;
		this.color = null;
	}

	/**
	 * Gets the point for the vertex.
	 * @return The point.
	 */
	public Coordinate getPoint()
	{
		return point;
	}

	/**
	 * @return the type
	 */
	public PieceType getType()
	{
		return type;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() 
	{
		return color;
	}

	@Override
	public int hashCode() 
	{
		return point.hashCode();
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
		
		Vertex other = (Vertex) obj;
		
		if (color != other.color)
			return false;
		if (point == null) 
		{
			if (other.point != null)
				return false;
		} 
		else if (!point.equals(other.point))
			return false;
		if (type != other.type)
			return false;
		
		return true;
	}

	@Override
	public String toString()
	{
		return "Vertex-" + point.toString();
	}	
}

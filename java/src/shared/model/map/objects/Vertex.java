package shared.model.map.objects;

import shared.definitions.*;
import shared.model.map.Coordinate;

/**
 * Use to store information about a vertex.
 * @author Jonathan Sadler
 *
 */
public class Vertex {

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
	public String toString()
	{
		return "Vertex-" + point.toString();
	}	
}

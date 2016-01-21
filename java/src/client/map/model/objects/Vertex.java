package client.map.model.objects;

import shared.definitions.*;

/**
 * Use to store information about a vertex.
 * @author Jonathan Sadler
 *
 */
public class Vertex {

	private int xLocation;
	private int yLocation;
	
	private PieceType type;
	private CatanColor color;
	
	/**
	 * Creates a vertex object.
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Vertex(int x, int y)
	{
		type = PieceType.NONE;
		this.xLocation = x;
		this.yLocation = y;
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
	 * @return the xLocation
	 */
	public int getxLocation() {
		return xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public int getyLocation() {
		return yLocation;
	}

	/**
	 * @return the type
	 */
	public PieceType getType() {
		return type;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}

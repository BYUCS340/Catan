package client.map.model.objects;

import client.map.model.Coordinate;
import shared.definitions.*;

/**
 * Use to store information about a vertex.
 * @author Jonathan Sadler
 *
 */
public class Vertex {

	private Coordinate point;
	
	private PieceType type;
	private CatanColor color;
	
	private PortType portType;
	
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
		
		portType = PortType.NONE;
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

	public Coordinate getPoint() {
		return point;
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

	/**
	 * @return the portType
	 */
	public PortType getPortType() {
		return portType;
	}

	/**
	 * @param portType the portType to set
	 */
	public void setPortType(PortType portType) {
		this.portType = portType;
	}
}

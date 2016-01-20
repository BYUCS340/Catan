package client.map.model.objects;

import shared.definitions.*;

public class Vertex {

	private int xLocation;
	private int yLocation;
	
	private Type type;
	private CatanColor color;
	
	public Vertex(int x, int y)
	{
		type = Type.none;
		this.xLocation = x;
		this.yLocation = y;
	}
	
	public void SetType(Type type, CatanColor color)
	{
		this.type = type;
		this.color = color;
	}
	
	public void ClearType()
	{
		this.type = Type.none;
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
	public Type getType() {
		return type;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}

	public enum Type
	{
		none, settlement, city
	}
}

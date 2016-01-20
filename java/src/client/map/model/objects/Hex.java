package client.map.model.objects;

import shared.definitions.HexType;

public class Hex {

	private HexType type;
	private int xLocation;
	private int yLocation;
	
	public Hex(HexType type, int x, int y)
	{
		this.type = type;
		this.xLocation = x;
		this.yLocation = y;
	}

	/**
	 * @return the type
	 */
	public HexType getType() {
		return type;
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
}

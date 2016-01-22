package client.map.model.objects;

import shared.definitions.HexType;

/**
 * Used to store information about a Hex.
 * @author Jonathan Sadler
 *
 */
public class Hex {

	private HexType type;
	private int xLocation;
	private int yLocation;
	
	/**
	 * Creates a hex object.
	 * @param type The resource type of the hex.
	 * @param x The X coordinate of the hex.
	 * @param y The Y coordinate of the hex.
	 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + xLocation;
		result = prime * result + yLocation;
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
		Hex other = (Hex) obj;
		if (type != other.type)
			return false;
		if (xLocation != other.xLocation)
			return false;
		if (yLocation != other.yLocation)
			return false;
		
		return true;
	}
}

package client.map.model.objects;

import client.map.model.Coordinate;
import shared.definitions.HexType;

/**
 * Used to store information about a Hex.
 * @author Jonathan Sadler
 *
 */
public class Hex {

	private HexType type;
	private Coordinate point;
	
	/**
	 * Creates a hex object.
	 * @param type The resource type of the hex.
	 * @param point The coordinate of the hex.
	 */
	public Hex(HexType type, Coordinate point)
	{
		this.type = type;
		this.point = point;
	}

	/**
	 * @return the type
	 */
	public HexType getType() {
		return type;
	}
	
	/**
	 * @return the point
	 */
	public Coordinate getPoint() {
		return point;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + point.hashCode();
		result = prime * result + type.hashCode();
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
		if (!point.equals(other.point))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}

package client.map.model.objects;

import client.map.MapException;
import client.map.model.Coordinate;
import shared.definitions.HexType;
import shared.definitions.PortType;

/**
 * Used to store information about a Hex.
 * @author Jonathan Sadler
 *
 */
public class Hex {

	private HexType type;
	private Coordinate point;
	private PortType port;
	
	/**
	 * Creates a hex object.
	 * @param type The resource type of the hex.
	 * @param point The coordinate of the hex.
	 */
	public Hex(HexType type, Coordinate point)
	{
		this.type = type;
		this.point = point;
		this.port = PortType.NONE;
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

	/**
	 * @return the port
	 */
	public PortType getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 * @throws MapException Thrown when a port can't be added to the hex.
	 */
	public void setPort(PortType port) throws MapException {
		if (this.type != HexType.WATER)
			throw new MapException("Whoa there. You can't add a port to dry land. Who do you think you are? Noah?");
		
		this.port = port;
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

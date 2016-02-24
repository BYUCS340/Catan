package shared.model.map.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import shared.definitions.PortType;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.objects.*;

/**
 * Handles information regarding ports.
 * @author Jonathan Sadler
 *
 */
public class PortHandler
{
	private static final int INITIAL_CAPACITY = 9;
	
	private Map<Edge, Hex> ePorts;
	private Map<Coordinate, Hex> cPorts;
	
	/**
	 * Creates a PortHandler object.
	 */
	public PortHandler()
	{
		ePorts = new HashMap<Edge, Hex>(INITIAL_CAPACITY);
		cPorts = new HashMap<Coordinate, Hex>(2 * INITIAL_CAPACITY);
	}
	
	/**
	 * Adds a port to a hex. The hex must be a water tile.
	 * @param type The type of port being added.
	 * @param edge The edge the port is on.
	 * @param hex The hex the port is on.
	 * @throws MapException Don't add ports to dry land unless you wan't this exception.
	 */
	public void AddPort(PortType type, Edge edge, Hex hex) throws MapException
	{
		hex.setPort(type);
		
		ePorts.put(edge, hex);
		cPorts.put(edge.getStart(), hex);
		cPorts.put(edge.getEnd(), hex);
	}
	
	/**
	 * Determines if there is a port at the requested vertex.
	 * @param vertex The vertex coordinate.
	 * @return True if yes, else false.
	 */
	public boolean ContainsPort(Coordinate vertex)
	{
		return cPorts.containsKey(vertex);
	}
	
	/**
	 * Gets the port at the specified vertex. 
	 * @param vertex The desired vertex.
	 * @return The port type of the vertex.
	 * @throws MapException Thrown if there isn't a port at the vertex.
	 */
	public PortType GetPort(Coordinate vertex) throws MapException
	{
		if (ContainsPort(vertex))
			return cPorts.get(vertex).getPort();
		else
			throw new MapException("Port doesn't exist at specified vertex");
	}
	
	/**
	 * Gets all the available ports.
	 * @return An iterator to the ports.
	 */
	public Iterator<Entry<Edge, Hex>> GetPorts()
	{
		return java.util.Collections.unmodifiableSet(ePorts.entrySet()).iterator();
	}
}

package shared.model.map.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import client.map.MapException;
import shared.definitions.PortType;
import shared.model.map.objects.*;

/**
 * Handles information regarding ports.
 * @author Jonathan Sadler
 *
 */
public class PortHandler {

	private static final int INITIAL_CAPACITY = 9;
	
	private Map<Edge, Hex> ports;
	
	/**
	 * Creates a PortHandler object.
	 */
	public PortHandler()
	{
		ports = new HashMap<Edge, Hex>(INITIAL_CAPACITY);
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
		
		ports.put(edge, hex);
	}
	
	/**
	 * Gets all the available ports.
	 * @return An iterator to the ports.
	 */
	public Iterator<Entry<Edge, Hex>> GetAllPorts()
	{
		return java.util.Collections.unmodifiableSet(ports.entrySet()).iterator();
	}
}

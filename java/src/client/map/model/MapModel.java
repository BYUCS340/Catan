package client.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import client.map.MapException;
import client.map.model.handlers.*;
import client.map.model.objects.*;
import shared.definitions.*;

/**
 * The Map Model stores all information about the map. This data includes information
 * about hexes, edges, vertices, and the robber. Location data is stored in a X, Y grid
 * format.
 * @author Jonathan Sadler
 *
 */
public class MapModel {
	
	private Map<Integer, List<Hex>> values;
	
	private HexHandler hexes;
	private EdgeHandler edges;
	private VertexHandler verticies;	
	
	private int longestRoadLength;
	private Vertex longestRoad;
	private Map<CatanColor, List<PortType>> availablePorts;
	
	private Robber robber;
	
	/**
	 * Creates a new Map Model object.
	 */
	public MapModel()
	{
		this(Method.beginner);
	}
	
	/**
	 * This creates a new Map Model object. The method parameter allows a particular
	 * map style to be set up. This will eventually be located on the server.
	 * @param method The map style to set up.
	 */
	public MapModel(Method method)
	{
		values = new HashMap<Integer, List<Hex>>();
		
		hexes = new HexHandler();
		edges = new EdgeHandler();
		verticies = new VertexHandler();
		
		longestRoadLength = 2;
		longestRoad = null;
		availablePorts = new HashMap<CatanColor, List<PortType>>();
		
		if (method == Method.random)
			RandomSetup();
		else
			BeginnerSetup();
		
		PlaceWater();
		PlacePips();
	}
	
	/**
	 * Returns if an edge is on the board.
	 * @param p1 The first end point.
	 * @param p2 The second end point.
	 * @return True if the edge exists, else false.
	 */
	public boolean ContainsEdge(Coordinate p1, Coordinate p2)
	{
		return edges.ContainsEdge(p1, p2);
	}
	
	/**
	 * Returns if a hex is contained on the board.
	 * @param point The coordinate.
	 * @return True if the hex exists, else false.
	 */
	public boolean ContainsHex(Coordinate point)
	{
		return hexes.ContainsHex(point);
	}
	
	/**
	 * Returns if a vertex is on the board.
	 * @param point The coordinate.
	 * @return True if the vertex is on the board, else false.
	 */
	public boolean ContainsVertex(Coordinate point)
	{
		return verticies.ContainsVertex(point);
	}
	
	/**
	 * Gets the edge associated with the two end points. The order of the points
	 * does not matter.
	 * @param p1 The coordinate of the first point.
	 * @param p2 The coordinate of the second point.
	 * @return The associated edge.
	 * @throws MapException Thrown if the edge doesn't exist
	 */
	public Edge GetEdge(Coordinate p1, Coordinate p2) throws MapException
	{
		return edges.GetEdge(p1, p2);
	}
	
	/**
	 * Returns the hex associated with the coordinate.
	 * @param point The coordinate of the hex.
	 * @return The associated hex
	 * @throws MapException Thrown if the hex doesn't exist.
	 */
	public Hex GetHex(Coordinate point) throws MapException
	{
		return hexes.GetHex(point);
	}
	
	/**
	 * Gets all the hexes associated with the dice role
	 * @param role The combined value of the dice
	 * @return The associated hex
	 */
	public List<Hex> GetHex(int role)
	{
		return java.util.Collections.unmodifiableList(values.get(role));
	}
	
	/**
	 * Gets all the hexes in the map.
	 * @return A iterator to all the hexes.
	 */
	public Iterator<Hex> GetAllHexes()
	{
		return hexes.GetAllHexes();
	}
	
	/**
	 * Gets the list of all the pips on the playing board.
	 * @return The pip list.
	 */
	public Iterator<Map.Entry<Integer, List<Hex>>> GetPips()
	{
		return java.util.Collections.unmodifiableSet(values.entrySet()).iterator();
	}
	
	/**
	 * Gets the vertex associated with the coordinate.
	 * @param point The coordinate of the vertex.
	 * @return The associated vertex.
	 * @throws MapException Thrown if the vertex doesn't exist.
	 */
	public Vertex GetVertex(Coordinate point) throws MapException
	{
		return verticies.GetVertex(point);
	}
	
	public Iterator<Vertex> GetAllVerticies()
	{
		return verticies.GetVerticies();
	}
	
	/**
	 * Gets the vertices surrounding a hex.
	 * @param hex The hex being requested.
	 * @return A list of the surrounding vertices.
	 */
	public List<Vertex> GetOccupiedVerticies(Hex hex)
	{
		Coordinate point = hex.getPoint();
		
		List<Vertex> verticies = new ArrayList<Vertex>();
		
		HandleAddingOccupiedVertex(point, verticies);
		HandleAddingOccupiedVertex(point.GetNorth(), verticies);
		HandleAddingOccupiedVertex(point.GetSouth(), verticies);
		HandleAddingOccupiedVertex(point.GetEast(), verticies);
		HandleAddingOccupiedVertex(point.GetNorthEast(), verticies);
		HandleAddingOccupiedVertex(point.GetSouthEast(), verticies);
		
		return java.util.Collections.unmodifiableList(verticies);
	}
	
	public Hex GetRobberPlacement()
	{
		return robber.GetHex();
	}
	
	/**
	 * Creates a hex at the specified location.
	 * @param type The resource type associated with the hex.
	 * @param point The coordinate of the hex.
	 * @throws MapException Thrown if there is an issue adding the hex.
	 */
	public void SetHex(HexType type, Coordinate point) throws MapException
	{
		hexes.AddHex(new Hex(type, point));
	}
	
	/**
	 * Sets a vertex as a port
	 * @param type The type of port to set.
	 * @param point The coordinate of the port.
	 * @throws MapException Thrown if the port is added to a vertex that doesn't exist.
	 */
	public void SetPort(PortType type, Coordinate point) throws MapException
	{
		try {
			verticies.GetVertex(point).setPortType(type);
		} 
		catch (MapException e) {
			throw new MapException("Attempt to add port to non-existent vertex", e);
		}
	}
	
	private void RandomSetup()
	{
		//Todo Setup
	}
	
	private void BeginnerSetup()
	{
		try
		{
			hexes.AddHex(new Hex(HexType.ORE, new Coordinate(1, -2)));
			hexes.AddHex(new Hex(HexType.SHEEP, new Coordinate(1, 0)));
			hexes.AddHex(new Hex(HexType.WOOD, new Coordinate(1, 2)));
			
			hexes.AddHex(new Hex(HexType.WHEAT, new Coordinate(2, -3)));
			hexes.AddHex(new Hex(HexType.BRICK, new Coordinate(2, -1)));
			hexes.AddHex(new Hex(HexType.SHEEP, new Coordinate(2, 1)));
			hexes.AddHex(new Hex(HexType.BRICK, new Coordinate(2, 3)));
			
			hexes.AddHex(new Hex(HexType.WHEAT, new Coordinate(3, -4)));
			hexes.AddHex(new Hex(HexType.WOOD, new Coordinate(3, -2)));
			hexes.AddHex(new Hex(HexType.DESERT, new Coordinate(3, 0)));
			hexes.AddHex(new Hex(HexType.WOOD, new Coordinate(3, 2)));
			hexes.AddHex(new Hex(HexType.ORE, new Coordinate(3, 4)));
			
			hexes.AddHex(new Hex(HexType.WOOD, new Coordinate(4, -3)));
			hexes.AddHex(new Hex(HexType.ORE, new Coordinate(4, -1)));
			hexes.AddHex(new Hex(HexType.WHEAT, new Coordinate(4, 1)));
			hexes.AddHex(new Hex(HexType.SHEEP, new Coordinate(4, 3)));
			
			hexes.AddHex(new Hex(HexType.BRICK, new Coordinate(5, -2)));
			hexes.AddHex(new Hex(HexType.WHEAT, new Coordinate(5, 0)));
			hexes.AddHex(new Hex(HexType.SHEEP, new Coordinate(5, 2)));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//This shouldn't happen. Otherwise, we just suck.
		}
		
		try {
			robber = new Robber(hexes.GetHex(new Coordinate(3, 0)));
		}
		catch (MapException e) {
			e.printStackTrace();
			//This shouldn't happen either.
		}
	}
	
	private void PlaceWater()
	{
		try
		{
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(0, -1)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(0, -3)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(1, -4)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(2, -5)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(3, -6)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(4, -5)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(5, -4)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(6, -3)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(6, -1)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(6, 1)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(6, 3)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(5, 4)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(4, 5)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(3, 6)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(2, 5)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(1, 4)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(0, 3)));
			hexes.AddHex(new Hex(HexType.WATER, new Coordinate(0, 1)));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//This shouldn't happen. If it does, then you suck.
		}
	}

	private void PlacePips()
	{
		List<Integer> pipList = GetPipList();
		List<Hex> hexList = GetHexList();
		
		Iterator<Integer> pipIterator = pipList.iterator();
		Iterator<Hex> hexIterator = hexList.iterator();
		
		while (pipIterator.hasNext())
		{
			int pip = pipIterator.next();
			
			Hex hex = hexIterator.next();
			if (hex.getType() == HexType.DESERT)
				hex = hexIterator.next();
			
			AddPip(pip, hex);
		}
	}
	
	private List<Integer> GetPipList()
	{
		List<Integer> pipList = new ArrayList<Integer>(18);
		
		pipList.add(5);
		pipList.add(2);
		pipList.add(6);
		pipList.add(3);
		pipList.add(8);
		pipList.add(10);
		pipList.add(9);
		pipList.add(12);
		pipList.add(11);
		pipList.add(4);
		pipList.add(8);
		pipList.add(10);
		pipList.add(9);
		pipList.add(4);
		pipList.add(5);
		pipList.add(6);
		pipList.add(3);
		pipList.add(11);
		
		return pipList;
	}

	private List<Hex> GetHexList()
	{
		List<Hex> hexList = new ArrayList<Hex>(19);
		
		try
		{
			hexList.add(hexes.GetHex(new Coordinate(1, -2)));
			hexList.add(hexes.GetHex(new Coordinate(2, -3)));
			hexList.add(hexes.GetHex(new Coordinate(3, -4)));
			hexList.add(hexes.GetHex(new Coordinate(4, -3)));
			hexList.add(hexes.GetHex(new Coordinate(5, -2)));
			hexList.add(hexes.GetHex(new Coordinate(5, 0)));
			hexList.add(hexes.GetHex(new Coordinate(5, 2)));
			hexList.add(hexes.GetHex(new Coordinate(4, 3)));
			hexList.add(hexes.GetHex(new Coordinate(3, 4)));
			hexList.add(hexes.GetHex(new Coordinate(2, 3)));
			hexList.add(hexes.GetHex(new Coordinate(1, 2)));
			hexList.add(hexes.GetHex(new Coordinate(1, 0)));
			hexList.add(hexes.GetHex(new Coordinate(2, -1)));
			hexList.add(hexes.GetHex(new Coordinate(3, -2)));
			hexList.add(hexes.GetHex(new Coordinate(4, -1)));
			hexList.add(hexes.GetHex(new Coordinate(4, 1)));
			hexList.add(hexes.GetHex(new Coordinate(3, 2)));
			hexList.add(hexes.GetHex(new Coordinate(2, 1)));
			hexList.add(hexes.GetHex(new Coordinate(3, 0)));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//These are all standard coordinates. The only reason these wouldn't
			//exist is if you haven't created the map yet.
		}
		
		return hexList;
	}
	
	private void AddPip(int value, Hex hex)
	{
		if (values.containsKey(value))
		{
			values.get(value).add(hex);
		}
		else
		{
			List<Hex> tempList = new ArrayList<Hex>();
			tempList.add(hex);
			values.put(value, tempList);
		}
	}
	
	private void HandleAddingOccupiedVertex(Coordinate point, List<Vertex> vertexList)
	{
		try
		{
			if (verticies.ContainsVertex(point))
				AddOccupiedVertex(GetVertex(point), vertexList);
		}
		catch (MapException ex)
		{
			ex.printStackTrace();
			//Yeah, so this code shouldn't ever execute. If it does, somebody messed with
			//the methods that are being called.
		}
	}
	
	private void AddOccupiedVertex(Vertex vertex, List<Vertex> vertexList)
	{
		if (vertex.getType() != PieceType.NONE)
			vertexList.add(vertex);
	}
	
	public enum Method
	{
		beginner, random
	}
}

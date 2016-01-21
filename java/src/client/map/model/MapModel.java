package client.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.map.model.handlers.*;
import client.map.model.objects.*;
import shared.definitions.HexType;
import shared.definitions.PieceType;

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
		
		if (method == Method.random)
			RandomSetup();
		else
			BeginnerSetup();
		
		PlaceWater();
		PlacePips();
	}
	
	/**
	 * Returns the hex associated with the coordinate.
	 * @param x The x coordinate of the hex.
	 * @param y The y coordinate of the hex.
	 * @return The associated hex
	 */
	public Hex GetHex(int x, int y)
	{
		return hexes.GetHex(x, y);
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
	 * Gets the edge associated with the two end points. The order of the points
	 * does not matter.
	 * @param x1 The first x coordinate.
	 * @param y1 The first y coordinate.
	 * @param x2 The second x coordinate.
	 * @param y2 The second y coordinate.
	 * @return The associated edge.
	 */
	public Edge GetEdge(int x1, int y1, int x2, int y2)
	{
		return edges.GetEdge(x1, y1, x2, y2);
	}
	
	/**
	 * Gets the vertex associated with the coordinate.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The associated vertex.
	 */
	public Vertex GetVertex(int x, int y)
	{
		return verticies.GetVertex(x, y);
	}
	
	/**
	 * Gets the vertices surrounding a hex.
	 * @param hex The hex being requested.
	 * @return A list of the surrounding vertices.
	 */
	public List<Vertex> GetOccupiedVerticies(Hex hex)
	{
		int x = hex.getxLocation();
		int y = hex.getyLocation();
		
		List<Vertex> verticies = new ArrayList<Vertex>();
		
		AddOccupiedVertex(GetVertex(x, y + 1), verticies);
		AddOccupiedVertex(GetVertex(x, y), verticies);
		AddOccupiedVertex(GetVertex(x, y - 1), verticies);
		AddOccupiedVertex(GetVertex(x + 1, y + 1), verticies);
		AddOccupiedVertex(GetVertex(x + 1, y), verticies);
		AddOccupiedVertex(GetVertex(x + 1, y - 1), verticies);
		
		return java.util.Collections.unmodifiableList(verticies);
	}
	
	private void RandomSetup()
	{
		//Todo Setup
	}
	
	private void BeginnerSetup()
	{
		hexes.AddHex(new Hex(HexType.ORE, 1, -2));
		hexes.AddHex(new Hex(HexType.SHEEP, 1, 0));
		hexes.AddHex(new Hex(HexType.WOOD, 1, 2));
		
		hexes.AddHex(new Hex(HexType.WHEAT, 2, -3));
		hexes.AddHex(new Hex(HexType.BRICK, 2, -1));
		hexes.AddHex(new Hex(HexType.SHEEP, 2, 1));
		hexes.AddHex(new Hex(HexType.BRICK, 2, 3));
		
		hexes.AddHex(new Hex(HexType.WHEAT, 3, -4));
		hexes.AddHex(new Hex(HexType.WOOD, 3, -2));
		hexes.AddHex(new Hex(HexType.DESERT, 3, 0));
		hexes.AddHex(new Hex(HexType.WOOD, 3, 2));
		hexes.AddHex(new Hex(HexType.ORE, 3, 4));
		
		hexes.AddHex(new Hex(HexType.WOOD, 4, -3));
		hexes.AddHex(new Hex(HexType.ORE, 4, -1));
		hexes.AddHex(new Hex(HexType.WHEAT, 4, 1));
		hexes.AddHex(new Hex(HexType.SHEEP, 4, 3));
		
		hexes.AddHex(new Hex(HexType.BRICK, 5, -2));
		hexes.AddHex(new Hex(HexType.WHEAT, 5, 0));
		hexes.AddHex(new Hex(HexType.SHEEP, 5, 2));
		
		robber = new Robber(hexes.GetHex(3, 0));
	}
	
	private void PlaceWater()
	{
		hexes.AddHex(new Hex(HexType.WATER, 0, -1));
		hexes.AddHex(new Hex(HexType.WATER, 0, -3));
		hexes.AddHex(new Hex(HexType.WATER, 1, -4));
		hexes.AddHex(new Hex(HexType.WATER, 2, -5));
		hexes.AddHex(new Hex(HexType.WATER, 3, -6));
		hexes.AddHex(new Hex(HexType.WATER, 4, -5));
		hexes.AddHex(new Hex(HexType.WATER, 5, -4));
		hexes.AddHex(new Hex(HexType.WATER, 6, -3));
		hexes.AddHex(new Hex(HexType.WATER, 6, -1));
		hexes.AddHex(new Hex(HexType.WATER, 6, 1));
		hexes.AddHex(new Hex(HexType.WATER, 6, 3));
		hexes.AddHex(new Hex(HexType.WATER, 5, 4));
		hexes.AddHex(new Hex(HexType.WATER, 4, 5));
		hexes.AddHex(new Hex(HexType.WATER, 3, 6));
		hexes.AddHex(new Hex(HexType.WATER, 2, 5));
		hexes.AddHex(new Hex(HexType.WATER, 1, 4));
		hexes.AddHex(new Hex(HexType.WATER, 0, 3));
		hexes.AddHex(new Hex(HexType.WATER, 0, 1));
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
		
		hexList.add(hexes.GetHex(1, -2));
		hexList.add(hexes.GetHex(2, -3));
		hexList.add(hexes.GetHex(3, -4));
		hexList.add(hexes.GetHex(4, -3));
		hexList.add(hexes.GetHex(5, -2));
		hexList.add(hexes.GetHex(5, 0));
		hexList.add(hexes.GetHex(5, 2));
		hexList.add(hexes.GetHex(4, 3));
		hexList.add(hexes.GetHex(3, 4));
		hexList.add(hexes.GetHex(2, 3));
		hexList.add(hexes.GetHex(1, 2));
		hexList.add(hexes.GetHex(1, 0));
		hexList.add(hexes.GetHex(2, -1));
		hexList.add(hexes.GetHex(3, -2));
		hexList.add(hexes.GetHex(4, -1));
		hexList.add(hexes.GetHex(4, 1));
		hexList.add(hexes.GetHex(3, 2));
		hexList.add(hexes.GetHex(2, 1));
		hexList.add(hexes.GetHex(3, 0));
		
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

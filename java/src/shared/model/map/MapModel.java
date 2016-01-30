package shared.model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shared.definitions.*;
import shared.model.map.handlers.*;
import shared.model.map.objects.*;

/**
 * The Map Model stores all information about the map. This data includes information
 * about hexes, edges, vertices, and the robber. Location data is stored in a X, Y grid
 * format.
 * @author Jonathan Sadler
 *
 */
public class MapModel {
	
	private static final int LONGEST_ROAD_INITIAL_VALUE = 2;
	
	private Map<Integer, List<Hex>> values;
	
	private HexHandler hexes;
	private EdgeHandler edges;
	private VertexHandler verticies;	
	private PortHandler ports;
	
	private int longestRoadLength;
	private CatanColor longestRoadColor;
	private Map<CatanColor, List<PortType>> availablePorts;
	
	private Robber robber;
	
	/**
	 * Creates a new Map Model object.
	 */
	public MapModel()
	{
		values = new HashMap<Integer, List<Hex>>();
		
		hexes = new HexHandler();
		edges = new EdgeHandler();
		verticies = new VertexHandler();
		ports = new PortHandler();
		
		longestRoadLength = LONGEST_ROAD_INITIAL_VALUE;
		
		//TODO Move this to the server eventually. This shouldn't be shared.
		MapGenerator.BeginnerMap(this);
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
	 * Gets all the edges.
	 * @return An iterator of edges.
	 */
	public Iterator<Edge> GetAllEdges()
	{
		return edges.GetAllEdges();
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
	public Iterator<Hex> GetHex(int role)
	{
		return java.util.Collections.unmodifiableList(values.get(role)).iterator();
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
	
	/**
	 * Gets the neighbors (surrounding) vertices of a vertex.
	 * @param vertex The vertex which the neighbors are being requested.
	 * @return An iterator the the neighbors.
	 */
	public Iterator<Vertex> GetVerticies(Vertex vertex)
	{
		List<Vertex> neighbors = new ArrayList<Vertex>(3);
		
		try
		{
			if (verticies.ContainsVertex(vertex.getPoint().GetNorth()))
				neighbors.add(verticies.GetVertex(vertex.getPoint().GetNorth()));
			if (verticies.ContainsVertex(vertex.getPoint().GetSouth()))
				neighbors.add(verticies.GetVertex(vertex.getPoint().GetSouth()));
			
			Coordinate sideNeighbor;
			if (vertex.getPoint().isRightHandCoordinate())
				sideNeighbor = vertex.getPoint().GetEast();
			else
				sideNeighbor = vertex.getPoint().GetWest();
			
			if (verticies.ContainsVertex(sideNeighbor))
				neighbors.add(verticies.GetVertex(sideNeighbor));
		}
		catch (MapException e)
		{
			//This shouldn't occur since we are checking.
			e.printStackTrace();
		}
		
		return java.util.Collections.unmodifiableList(neighbors).iterator();
	}
	
	/**
	 * Gets the verticies that are associated with a hex.
	 * @param hex The hex.
	 * @return The associated verticies.
	 */
	public Iterator<Vertex> GetVerticies(Hex hex)
	{
		List<Vertex> verticiesAlongHex = new ArrayList<Vertex>(6);
		
		try
		{
			verticiesAlongHex.add(verticies.GetVertex(hex.getTopLeftCoordinate()));
			verticiesAlongHex.add(verticies.GetVertex(hex.getLeftCoordinate()));
			verticiesAlongHex.add(verticies.GetVertex(hex.getBottomLeftCoordinate()));
			verticiesAlongHex.add(verticies.GetVertex(hex.getTopRightCoordinate()));
			verticiesAlongHex.add(verticies.GetVertex(hex.getRightCoordinate()));
			verticiesAlongHex.add(verticies.GetVertex(hex.getBottomRightCoordinate()));
		}
		catch (MapException e) {
			//This would only trigger if we pass in a piece that is water.
			//Otherwise, it implies the map hasn't been initialized.
			e.printStackTrace();
		}
		
		return java.util.Collections.unmodifiableList(verticiesAlongHex).iterator();
	}
	
	/**
	 * Gets all the verticies on the map.
	 * @return A iterator to all the verticies.
	 */
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
	
	/**
	 * Returns all the ports.
	 * @return An iterator to all the ports.
	 */
	public Iterator<Entry<Edge, Hex>> GetAllPorts()
	{
		return ports.GetAllPorts();
	}
	
	/**
	 * Gets the hex the robber is placed on.
	 * @return The robber's hex.
	 */
	public Hex GetRobberPlacement()
	{
		return robber.GetHex();
	}
	
	public boolean LongestRoadExists()
	{
		return longestRoadLength > LONGEST_ROAD_INITIAL_VALUE;
	}
	
	public CatanColor GetLongestRoadColor() throws MapException
	{
		if (LongestRoadExists())
			return longestRoadColor;
		else
			throw new MapException("Longest road doesn't exist.");
	}
	
	/**
	 * Adds a road to the map.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @param color The color of the road.
	 * @throws MapException What made you think you could add a road?
	 */
	public void SetRoad(Coordinate p1, Coordinate p2, CatanColor color) throws MapException
	{
		edges.AddRoad(p1, p2, color);
		
		Set<Edge> handledEdges = new HashSet<Edge>();
		Set<Edge> allHandledEdges = new HashSet<Edge>();
		
		try
		{
			handledEdges.add(edges.GetEdge(p1, p2));
			
			Vertex v1 = verticies.GetVertex(p1);
			Vertex v2 = verticies.GetVertex(p2);
			
			//All handled edges accounts for loops. That is why it can be passed in
			//for the right. If the road connects a loop, then the left alread counted
			//it.
			int left = GetRoadCount(v1, color, handledEdges, allHandledEdges);
			int right = GetRoadCount(v2, color, allHandledEdges, allHandledEdges);
			
			int roadLength = left + right + 1;
			if (roadLength > longestRoadLength)
			{
				longestRoadLength = roadLength;
				longestRoadColor = color;
			}
		}
		catch (MapException e)
		{
			//This shouldn't occur, else the edge couldn't exist
			e.printStackTrace();
		}
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
	 * Adds a pip to a hex
	 * @param value The value of the pip
	 * @param hex The hex to which it is added.
	 */
	public void SetPip(int value, Hex hex)
	{
		//TODO Data validation needed to ensure a hex doesn't have multiple pips
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
	
	/**
	 * Adds a settlement to the map.
	 * @param point The coordinate of the settlement.
	 * @param color The color of the settlement.
	 * @throws MapException The government vetod your settlement.
	 */
	public void SetSettlement(Coordinate point, CatanColor color) throws MapException
	{
		verticies.SetSettlement(point, color);
	}
	
	/**
	 * Adds a city to the board.
	 * @param point The coordinate of the city.
	 * @param color The color of the city.
	 * @throws MapException If you pay 15% tithing, this won't happen (just kidding, your
	 * 						city couldn't be added).
	 */
	public void SetCity(Coordinate point, CatanColor color) throws MapException
	{
		verticies.SetCity(point, color);
	}
	
	/**
	 * Sets a vertex as a port
	 * @param type The type of port to set.
	 * @param point The coordinate of the port.
	 * @throws MapException Thrown if the port is added to a vertex that doesn't exist.
	 */
	public void SetPort(PortType type, Edge edge, Hex hex) throws MapException
	{
		try {
			ports.AddPort(type, edge, hex);
		} 
		catch (MapException e) {
			throw new MapException("Attempt to add port to non-existent vertex", e);
		}
	}
	
	/**
	 * Sets which hex the robber is on.
	 * @param hex The hex to place the robber on.
	 */
	public void SetRobber(Hex hex)
	{
		if (robber == null)
			robber = new Robber(hex);
		else
			robber.setRobber(hex);
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
	
	private int GetRoadCount(Vertex vertex, CatanColor color, 
			Set<Edge> handledEdges, Set<Edge> allHandledEdges)
	{
		int totalCount = 0;
		
		Iterator<Edge> associatedEdges = GetAssociatedEdges(vertex);
		while(associatedEdges.hasNext())
		{
			Edge edge = associatedEdges.next();
			
			if (edge.getColor() == color && !handledEdges.contains(edge))
			{
				try
				{
					handledEdges.add(edge);
					allHandledEdges.add(edge);
					
					Vertex newVertex = null;
					if (edge.getStart() == vertex.getPoint())
						newVertex = verticies.GetVertex(edge.getEnd());
					else
						newVertex = verticies.GetVertex(edge.getStart());
					
					int branchCount = 1 + GetRoadCount(newVertex, color, handledEdges, allHandledEdges);
					
					handledEdges.remove(edge);
					
					if (branchCount > totalCount)
						totalCount = branchCount;
				}
				catch (MapException e)
				{
					//This shouldn't occur as the edge can't exist without the vertices.
					e.printStackTrace();
				}
			}
		}
		
		return totalCount;
	}
	
	private Iterator<Edge> GetAssociatedEdges(Vertex vertex)
	{
		List<Edge> associatedEdges = new ArrayList<Edge>(3);
		
		Iterator<Vertex> vertices = GetVerticies(vertex);
		while(vertices.hasNext())
		{
			Vertex neighbor = vertices.next();
			
			Coordinate mainPoint = vertex.getPoint();
			Coordinate neighborPoint = neighbor.getPoint();
			try
			{
				if (edges.ContainsEdge(mainPoint, neighborPoint))
					associatedEdges.add(edges.GetEdge(mainPoint, neighborPoint));
			}
			catch (MapException e)
			{
				//Shouldn't happen
				e.printStackTrace();
			}
		}
		
		return java.util.Collections.unmodifiableList(associatedEdges).iterator();
	}
}

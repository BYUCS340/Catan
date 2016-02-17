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
public class MapModel implements IMapModel {
	
	private static final int LONGEST_ROAD_INITIAL_VALUE = 2;
	
	private Map<Integer, List<Hex>> values;
	
	private HexHandler hexes;
	private EdgeHandler edges;
	private VertexHandler vertices;	
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
		vertices = new VertexHandler();
		ports = new PortHandler();
		
		longestRoadLength = LONGEST_ROAD_INITIAL_VALUE;
	}
	
	/**
	 * Returns if the robber is initialized.
	 * @return True if yes, else false.
	 */
	public boolean IsRobberInitialized()
	{
		return robber != null;
	}
	
	/**
	 * Gets if the longest road exists.
	 * @return True if yes, else false.
	 */
	public boolean LongestRoadExists()
	{
		return longestRoadLength > LONGEST_ROAD_INITIAL_VALUE;
	}
	
	/**
	 * Returns if a hex exists.
	 * @param point The hex to check.
	 * @return True if yes, else false.
	 */
	public boolean HexExists(Coordinate point)
	{
		return hexes.ContainsHex(point);
	}
	
	/**
	 * Returns if a road can be placed.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @param color The color of the road.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color)
	{	
		if (!edges.ContainsEdge(p1, p2))
			return false;
		
		try
		{
			Edge edge = edges.GetEdge(p1, p2);
			
			if (edge.doesRoadExists())
				return false;
			
			if (VillagesSatisfyRoadPlacement(edge, color))
				return true;
			
			return RoadsSatisfyRoadPlacement(edge, color);
		} 
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returns if a Settlement can be placed at the specified location.
	 * @param point The point of placement.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceSettlement(Coordinate point)
	{
		if (!vertices.ContainsVertex(point))
			return false;
		
		try
		{
			Vertex vertex = vertices.GetVertex(point);
			
			if (vertex.getType() != PieceType.NONE)
				return false;
			
			Iterator<Vertex> neighbors = GetVertices(vertex);
			
			while(neighbors.hasNext())
			{
				Vertex neighbor = neighbors.next();
				
				if (neighbor.getType() != PieceType.NONE)
					return false;
			}
			
			return true;
		} 
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returns if a city can be placed.
	 * @param point The coordinate of the city.
	 * @param color The color of the city.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceCity(Coordinate point, CatanColor color)
	{	
		if (!vertices.ContainsVertex(point))
			return false;
		
		try
		{
			Vertex vertex = vertices.GetVertex(point);
			
			return vertex.getType() == PieceType.SETTLEMENT && 
					vertex.getColor() == color;
		}
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returns if the robber can be placed at the specified location.
	 * @param point The point to place the robber.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceRobber(Coordinate point)
	{	
		if (!hexes.ContainsHex(point))
			return false;
		
		try
		{
			Hex hex = hexes.GetHex(point);
			
			return hex.getType() != HexType.WATER;
		}
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean CanPlacePip(Coordinate point)
	{
		if(!hexes.ContainsHex(point))
			return false;
		
		try
		{
			Hex hex = hexes.GetHex(point);
			
			return hex.getType() != HexType.WATER && hex.getType() != HexType.DESERT;
		}
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Creates a hex at the specified location.
	 * @param type The resource type associated with the hex.
	 * @param point The coordinate of the hex.
	 * @throws MapException Thrown if there is an issue adding the hex.
	 */
	public void PlaceHex(HexType type, Coordinate point) throws MapException
	{
		hexes.AddHex(new Hex(type, point));
	}
	
	/**
	 * Puts a road on the map.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @param color The color of the road.
	 * @throws MapException Thrown if issues occur adding road.
	 */
	public void PlaceRoad(Coordinate p1, Coordinate p2, CatanColor color) throws MapException
	{	
		edges.AddRoad(p1, p2, color);
		
		Set<Edge> handledEdges = new HashSet<Edge>();
		Set<Edge> allHandledEdges = new HashSet<Edge>();
		
		try
		{
			handledEdges.add(edges.GetEdge(p1, p2));
			
			Vertex v1 = vertices.GetVertex(p1);
			Vertex v2 = vertices.GetVertex(p2);
			
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
	 * Adds a settlement to the map.
	 * @param point The coordinate of the settlement.
	 * @param color The color of the settlement.
	 * @throws MapException The government vetod your settlement.
	 */
	public void PlaceSettlement(Coordinate point, CatanColor color) throws MapException
	{
		vertices.SetSettlement(point, color);
	}
	
	/**
	 * Adds a city to the board.
	 * @param point The coordinate of the city.
	 * @param color The color of the city.
	 * @throws MapException If you pay 15% tithing, this won't happen (just kidding, your
	 * 						city couldn't be added).
	 */
	public void PlaceCity(Coordinate point, CatanColor color) throws MapException
	{
		vertices.SetCity(point, color);
	}
	
	/**
	 * Sets a vertex as a port
	 * @param type The type of port to set.
	 * @param point The coordinate of the port.
	 * @throws MapException Thrown if the port is added to a vertex that doesn't exist.
	 */
	public void PlacePort(PortType type, Coordinate hexCoordinate, 
			Coordinate edgeStart, Coordinate edgeEnd) throws MapException
	{
		try 
		{
			Hex hex = hexes.GetHex(hexCoordinate);
			Edge edge = edges.GetEdge(edgeStart, edgeEnd);
			
			ports.AddPort(type, edge, hex);
		} 
		catch (MapException e)
		{
			throw new MapException("Attempt to add port to non-existent vertex", e);
		}
	}
	
	/**
	 * Sets which hex the robber is on.
	 * @param hex The hex to place the robber on.
	 * @throws MapException  Thrown if the hex is invalid.
	 */
	public void PlaceRobber(Coordinate point) throws MapException
	{
		Hex hex = hexes.GetHex(point);
		
		if (robber == null)
			robber = new Robber(hex);
		else
			robber.setRobber(hex);
	}
	
	/**
	 * Adds a pip to a hex.
	 * @param value The value of the pip.
	 * @param hex The hex to which it is added.
	 * @throws MapException Thrown if the hex doesn't exist.
	 */
	public void PlacePip(int value, Coordinate point) throws MapException
	{
		Hex hex = hexes.GetHex(point);
		
		if (values.containsKey(value))
		{
			//If a hex contains a value, we are simply changing the value.
			if (values.get(value).contains(hex))
			{
				values.get(value).remove(hex);
				PlacePip(value, point);
			}
			else
			{
				values.get(value).add(hex);
			}
		}
		else
		{
			List<Hex> tempList = new ArrayList<Hex>();
			tempList.add(hex);
			values.put(value, tempList);
		}
	}
	
	/**
	 * Gets a hex at the specified location.
	 * @param point The desired coordinate.
	 * @return The hex.
	 * @throws MapException Thrown if the hex doesn't exist.
	 */
	public Hex GetHex(Coordinate point) throws MapException
	{
		return hexes.GetHex(point);
	}
	
	/**
	 * Gets all the hexes.
	 * @return All the hexes.
	 */
	public Iterator<Hex> GetHexes()
	{
		return hexes.GetAllHexes();
	}
	
	public Edge GetEdge(Coordinate p1, Coordinate p2) throws MapException
	{
		return edges.GetEdge(p1, p2);
	}
	
	/**
	 * Gets all the edges.
	 * @return All the edges.
	 */
	public Iterator<Edge> GetEdges()
	{
		return edges.GetAllEdges();
	}
	
	/**
	 * Gets the vertex at a specified location.
	 * @param point The specified location.
	 * @return The vertex.
	 * @throws MapException Thrown if the vertex doesn't exist.
	 */
	public Vertex GetVertex(Coordinate point) throws MapException
	{
		return vertices.GetVertex(point);
	}
	
	/**
	 * Gets all the vertices.
	 * @return All the vertices.
	 */
	public Iterator<Vertex> GetVertices()
	{
		return vertices.GetVerticies();
	}
	
	/**
	 * Gets the vertices that are associated with a hex.
	 * @param hex The hex.
	 * @return The associated vertices.
	 */
	public Iterator<Vertex> GetVertices(Hex hex)
	{
		List<Vertex> verticiesAlongHex = new ArrayList<Vertex>(6);
		
		try
		{
			verticiesAlongHex.add(vertices.GetVertex(hex.getTopLeftCoordinate()));
			verticiesAlongHex.add(vertices.GetVertex(hex.getLeftCoordinate()));
			verticiesAlongHex.add(vertices.GetVertex(hex.getBottomLeftCoordinate()));
			verticiesAlongHex.add(vertices.GetVertex(hex.getTopRightCoordinate()));
			verticiesAlongHex.add(vertices.GetVertex(hex.getRightCoordinate()));
			verticiesAlongHex.add(vertices.GetVertex(hex.getBottomRightCoordinate()));
		}
		catch (MapException e) {
			//This would only trigger if we pass in a piece that is water.
			//Otherwise, it implies the map hasn't been initialized.
			e.printStackTrace();
		}
		
		return java.util.Collections.unmodifiableList(verticiesAlongHex).iterator();
	}
	
	/**
	 * Gets the neighbors (surrounding) vertices of a vertex.
	 * @param vertex The vertex which the neighbors are being requested.
	 * @return An iterator the the neighbors.
	 */
	public Iterator<Vertex> GetVertices(Vertex vertex)
	{
		List<Vertex> neighbors = new ArrayList<Vertex>(3);
		
		try
		{
			if (vertices.ContainsVertex(vertex.getPoint().GetNorth()))
				neighbors.add(vertices.GetVertex(vertex.getPoint().GetNorth()));
			if (vertices.ContainsVertex(vertex.getPoint().GetSouth()))
				neighbors.add(vertices.GetVertex(vertex.getPoint().GetSouth()));
			
			Coordinate sideNeighbor;
			if (vertex.getPoint().isRightHandCoordinate())
				sideNeighbor = vertex.getPoint().GetEast();
			else
				sideNeighbor = vertex.getPoint().GetWest();
			
			if (vertices.ContainsVertex(sideNeighbor))
				neighbors.add(vertices.GetVertex(sideNeighbor));
		}
		catch (MapException e)
		{
			//This shouldn't occur since we are checking.
			e.printStackTrace();
		}
		
		return java.util.Collections.unmodifiableList(neighbors).iterator();
	}
	
	/**
	 * Gets all the ports.
	 * @return All the ports.
	 */
	public Iterator<Entry<Edge, Hex>> GetPorts()
	{
		return ports.GetAllPorts();
	}
	
	/**
	 * Gets the location of the robber.
	 * @return The robber location.
	 */
	public Hex GetRobberLocation()
	{
		return robber.GetHex();
	}
	
	/**
	 * Gets all the pips.
	 * @return All the pips.
	 */
	public Iterator<Entry<Integer, List<Hex>>> GetPips()
	{
		return java.util.Collections.unmodifiableSet(values.entrySet()).iterator();
	}
	
	/**
	 * Gets the longest road.
	 * @return The longest road.
	 * @throws MapException Thrown if the road doesn't exist.
	 */
	public CatanColor GetLongestRoadColor() throws MapException
	{
		if (LongestRoadExists())
			return longestRoadColor;
		else
			throw new MapException("Longest road doesn't exist.");
	}
	
	/**
	 * Gets the transactions associated with a role.
	 * @param role The role value.
	 * @return Transaction objects associated with role.
	 */
	public Iterator<Transaction> GetTransactions(int role)
	{
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try
		{
			Iterator<Hex> hexes = GetHex(role);
			while (hexes.hasNext())
			{
				Hex hex = hexes.next();
				
				Iterator<Vertex> vertices = GetVertices(hex);
				while (vertices.hasNext())
				{
					Vertex vertex = vertices.next();
					
					if (vertex.getType() == PieceType.NONE)
						continue;
					
					HexType hexType = hex.getType();
					PieceType pieceType = vertex.getType();
					CatanColor color = vertex.getColor();
					Transaction transaction = new Transaction(hexType, pieceType, color);
					
					transactions.add(transaction);
				}
			}
		}
		catch (MapException e)
		{
			//Don't need to do anything.
			//Simply means the role didn't exist, so we don't form any
			//transactions.
		}
		
		return java.util.Collections.unmodifiableList(transactions).iterator();
	}
	
	/**
	 * Gets all the hexes associated with the dice role.
	 * @param role The combined value of the dice.
	 * @return The associated hex.
	 * @throws MapException Thrown if the value doesn't exist.
	 */
	private Iterator<Hex> GetHex(int role) throws MapException
	{
		if (!values.containsKey(role))
			throw new MapException("Role value does not exist.");
		else
			return java.util.Collections.unmodifiableList(values.get(role)).iterator();
	}
	
	/**
	 * Gets the edges surrounding a vertex.
	 * @param vertex The vertex.
	 * @return The surrounding edges.
	 */
	private Iterator<Edge> GetEdges(Vertex vertex)
	{
		List<Edge> associatedEdges = new ArrayList<Edge>(3);
		
		Iterator<Vertex> vertices = GetVertices(vertex);
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
	
	private int GetRoadCount(Vertex vertex, CatanColor color, 
			Set<Edge> handledEdges, Set<Edge> allHandledEdges)
	{
		int totalCount = 0;
		
		Iterator<Edge> associatedEdges = GetEdges(vertex);
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
						newVertex = vertices.GetVertex(edge.getEnd());
					else
						newVertex = vertices.GetVertex(edge.getStart());
					
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
	
	private boolean RoadsSatisfyRoadPlacement(Edge edge, CatanColor color) throws MapException
	{
		Vertex vStart = vertices.GetVertex(edge.getStart());
		Iterator<Edge> startEdges = GetEdges(vStart);
		while(startEdges.hasNext())
		{
			Edge edgeToCheck = startEdges.next();
			if (edgeToCheck.doesRoadExists() && edgeToCheck.getColor() == color)
				return true;
		}
		
		Vertex vEnd = vertices.GetVertex(edge.getEnd());
		Iterator<Edge> endEdges = GetEdges(vEnd);
		while(endEdges.hasNext())
		{
			Edge edgeToCheck = endEdges.next();
			if (edgeToCheck.doesRoadExists() && edgeToCheck.getColor() == color)
				return true;
		}
		
		return false;
	}
	
	private boolean VillagesSatisfyRoadPlacement(Edge edge, CatanColor color) throws MapException
	{
		Vertex vStart = vertices.GetVertex(edge.getStart());
		if (vStart.getType() != PieceType.NONE && vStart.getColor() == color)
			return true;
		
		Vertex vEnd = vertices.GetVertex(edge.getEnd());
		if (vEnd.getType() != PieceType.NONE && vEnd.getColor() == color)
			return true;
		
		return false;
	}
}

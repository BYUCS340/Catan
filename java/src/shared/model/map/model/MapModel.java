package shared.model.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shared.definitions.*;
import shared.model.map.*;
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
	
	@Override
	public boolean IsRobberInitialized()
	{
		return robber != null;
	}
	
	@Override
	public boolean LongestRoadExists()
	{
		return longestRoadLength > LONGEST_ROAD_INITIAL_VALUE;
	}
	
	@Override
	public boolean ContainsEdge(Coordinate p1, Coordinate p2)
	{
		return edges.ContainsEdge(p1, p2);
	}

	@Override
	public boolean ContainsVertex(Coordinate point)
	{
		return vertices.ContainsVertex(point);
	}
	
	@Override
	public boolean ContainsHex(Coordinate point)
	{
		return hexes.ContainsHex(point);
	}
	
	@Override
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color, boolean setup)
	{	
		try
		{
			//Edge doesn't exist
			if (!edges.ContainsEdge(p1, p2))
				return false;
			
			Edge edge = edges.GetEdge(p1, p2);
			
			//Road already placed
			if (edge.doesRoadExists())
				return false;
			
			//Village satisfies end
			if (VillagesSatisfyRoadPlacement(edge, color))
				return true;
			
			if (setup)
				return false;
			
			//Road satisfies end
			return RoadsSatisfyRoadPlacement(edge, color);
		} 
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean CanPlaceSettlement(Coordinate point, CatanColor color, boolean setup)
	{
		try
		{
			//Invalid vertex
			if (!vertices.ContainsVertex(point))
				return false;
			
			Vertex vertex = vertices.GetVertex(point);
			
			//Vertex contains a piece already
			if (vertex.getType() != PieceType.NONE)
				return false;
			
			Iterator<Vertex> neighbors = GetVertices(vertex);
			
			boolean roadSatisfied = false;
			while(neighbors.hasNext())
			{
				Vertex neighbor = neighbors.next();
				
				//Vertex has a neighbor
				if (neighbor.getType() != PieceType.NONE)
					return false;
				
				Edge edge = edges.GetEdge(point, neighbor.getPoint());
				
				//Marks if the settlement is on a road.
				if (!setup && edge.doesRoadExists() && edge.getColor() == color)
					roadSatisfied = true;
				//The settlement is not supposed to be on a road, yet it is.
				else if (setup && edge.doesRoadExists())
					return false;
			}
			
			//The method won't get to this point if other conditions aren't satisfied.
			//The final factor is if the road is satisfied.
			return roadSatisfied || setup;
		} 
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	public void PlaceHex(HexType type, Coordinate point) throws MapException
	{
		hexes.AddHex(new Hex(type, point));
	}
	
	@Override
	public void PlaceRoad(Coordinate p1, Coordinate p2, CatanColor color) throws MapException
	{	
		if (CanPlaceRoad(p1, p2, color, false))
			edges.AddRoad(p1, p2, color);
		else
			throw new MapException("Attempt to place road where not allowed");
		
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
	
	@Override
	public void PlaceSettlement(Coordinate point, CatanColor color) throws MapException
	{
		if (CanPlaceSettlement(point, color))
			vertices.SetSettlement(point, color);
		else
			throw new MapException("Attempt to place settlement where not allowed");
	}
	
	@Override
	public void PlaceCity(Coordinate point, CatanColor color) throws MapException
	{
		if (CanPlaceCity(point, color))
			vertices.SetCity(point, color);
		else
			throw new MapException("Attempt to place city where not allowed");
	}
	
	@Override
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
	
	@Override
	public void PlaceRobber(Coordinate point) throws MapException
	{
		Hex hex = hexes.GetHex(point);
		
		if (hex.getType() == HexType.WATER)
			throw new MapException("Don't drown Trogdor!");
		
		if (robber == null)
			robber = new Robber(hex);
		else
			robber.setRobber(hex);
	}
	
	@Override
	public void PlacePip(int value, Coordinate point) throws MapException
	{
		//Ignore any invalid roles. The provided server gives us -1 for the
		//desert hex.
		if (value < 2 || value > 12)
			return;
		
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
	
	@Override
	public Hex GetHex(Coordinate point) throws MapException
	{
		return hexes.GetHex(point);
	}
	
	@Override
	public Iterator<Hex> GetHexes()
	{
		return hexes.GetAllHexes();
	}
	
	@Override
	public Edge GetEdge(Coordinate p1, Coordinate p2) throws MapException
	{
		return edges.GetEdge(p1, p2);
	}
	
	@Override
	public Iterator<Edge> GetEdges()
	{
		return edges.GetAllEdges();
	}
	
	@Override
	public Vertex GetVertex(Coordinate point) throws MapException
	{
		return vertices.GetVertex(point);
	}
	
	@Override
	public Iterator<Vertex> GetVertices()
	{
		return vertices.GetVerticies();
	}
	
	@Override
	public Iterator<Vertex> GetVertices(Hex hex)
	{
		List<Vertex> verticiesAlongHex = new ArrayList<Vertex>(6);
		
		try
		{
			if (vertices.ContainsVertex(hex.getTopLeftCoordinate()))
				verticiesAlongHex.add(vertices.GetVertex(hex.getTopLeftCoordinate()));
			if (vertices.ContainsVertex(hex.getLeftCoordinate()))
				verticiesAlongHex.add(vertices.GetVertex(hex.getLeftCoordinate()));
			if (vertices.ContainsVertex(hex.getBottomLeftCoordinate()))
				verticiesAlongHex.add(vertices.GetVertex(hex.getBottomLeftCoordinate()));
			if (vertices.ContainsVertex(hex.getBottomRightCoordinate()))
				verticiesAlongHex.add(vertices.GetVertex(hex.getBottomRightCoordinate()));
			if (vertices.ContainsVertex(hex.getRightCoordinate()))
				verticiesAlongHex.add(vertices.GetVertex(hex.getRightCoordinate()));
			if (vertices.ContainsVertex(hex.getTopRightCoordinate()))
				verticiesAlongHex.add(vertices.GetVertex(hex.getTopRightCoordinate()));
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
		
		return java.util.Collections.unmodifiableList(verticiesAlongHex).iterator();
	}
	
	@Override
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
	
	@Override
	public Iterator<Entry<Edge, Hex>> GetPorts()
	{
		return ports.GetPorts();
	}
	
	@Override
	public Iterator<PortType> GetPorts(CatanColor color)
	{
		List<PortType> portTypes = new ArrayList<PortType>(4);
		
		try
		{
			Iterator<Entry<Edge, Hex>> portList = ports.GetPorts();
			
			while (portList.hasNext())
			{
				Entry<Edge, Hex> port = portList.next();
				
				Edge edge = port.getKey();
				Vertex v1 = vertices.GetVertex(edge.getStart());
				Vertex v2 = vertices.GetVertex(edge.getEnd());
				
				if (v1.getType() != PieceType.NONE && v1.getColor() == color)
					portTypes.add(port.getValue().getPort());
				else if (v2.getType() != PieceType.NONE && v2.getColor() == color)
					portTypes.add(port.getValue().getPort());
			}
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//Shouldn't occur
		}
		
		return java.util.Collections.unmodifiableList(portTypes).iterator();
	}
	
	@Override
	public Hex GetRobberLocation()
	{
		return robber.GetHex();
	}
	
	@Override
	public Iterator<Entry<Integer, List<Hex>>> GetPips()
	{
		return java.util.Collections.unmodifiableSet(values.entrySet()).iterator();
	}
	
	@Override
	public CatanColor GetLongestRoadColor() throws MapException
	{
		if (LongestRoadExists())
			return longestRoadColor;
		else
			throw new MapException("Longest road doesn't exist.");
	}
	
	@Override
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
	
	/**
	 * Used for settlement to check the things that don't rely on state.
	 * @param point The coordinate of the vertex.
	 * @param color The color of the piece to be placed.
	 * @return True if valid, else false.
	 */
	private boolean CanPlaceSettlement(Coordinate point, CatanColor color)
	{
		try
		{
			//Invalid vertex
			if (!vertices.ContainsVertex(point))
				return false;
			
			Vertex vertex = vertices.GetVertex(point);
			
			//Vertex contains a piece already
			if (vertex.getType() != PieceType.NONE)
				return false;
			
			Iterator<Vertex> neighbors = GetVertices(vertex);
			
			while(neighbors.hasNext())
			{
				Vertex neighbor = neighbors.next();
				
				//Vertex has a neighbor
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + ((hexes == null) ? 0 : hexes.hashCode());
		result = prime * result + ((longestRoadColor == null) ? 0 : longestRoadColor.hashCode());
		result = prime * result + longestRoadLength;
		result = prime * result + ((ports == null) ? 0 : ports.hashCode());
		result = prime * result + ((robber == null) ? 0 : robber.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MapModel))
			return false;
		MapModel other = (MapModel) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (hexes == null) {
			if (other.hexes != null)
				return false;
		} else if (!hexes.equals(other.hexes))
			return false;
		if (longestRoadColor != other.longestRoadColor)
			return false;
		if (longestRoadLength != other.longestRoadLength)
			return false;
		if (ports == null) {
			if (other.ports != null)
				return false;
		} else if (!ports.equals(other.ports))
			return false;
		if (robber == null) {
			if (other.robber != null)
				return false;
		} else if (!robber.equals(other.robber))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}
	
	
}

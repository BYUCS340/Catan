package shared.model.map.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import shared.definitions.*;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.Transaction;
import shared.model.map.objects.*;

public class UnmodifiableMapModel implements IMapModel
{
	private IMapModel model;
	
	public UnmodifiableMapModel(IMapModel model)
	{
		this.model = model;
	}
	
	@Override
	public void ForceUpdate(boolean force)
	{
		model.ForceUpdate(force);
	}
	
	@Override
	public void SetupPhase(boolean setup)
	{
		model.SetupPhase(setup);
	}

	@Override
	public boolean IsRobberInitialized()
	{
		return model.IsRobberInitialized();
	}

	@Override
	public boolean LongestRoadExists()
	{
		return model.LongestRoadExists();
	}

	@Override
	public boolean ContainsEdge(Coordinate p1, Coordinate p2)
	{
		return model.ContainsEdge(p1, p2);
	}

	@Override
	public boolean ContainsVertex(Coordinate point)
	{
		return model.ContainsVertex(point);
	}

	@Override
	public boolean ContainsHex(Coordinate point)
	{
		return model.ContainsHex(point);
	}
	
	@Override
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color)
	{
		return model.CanPlaceRoad(p1, p2, color);
	}

	@Override
	public boolean CanPlaceSettlement(Coordinate point, CatanColor color)
	{
		return model.CanPlaceSettlement(point, color);
	}

	@Override
	public boolean CanPlaceCity(Coordinate point, CatanColor color)
	{
		return model.CanPlaceCity(point, color);
	}

	@Override
	public boolean CanPlaceRobber(Coordinate point)
	{
		return model.CanPlaceRobber(point);
	}

	@Override
	public boolean CanPlacePip(Coordinate point)
	{
		return model.CanPlacePip(point);
	}

	@Override
	public void PlaceHex(HexType type, Coordinate point) throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public void PlaceRoad(Coordinate p1, Coordinate p2, CatanColor color) throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public void PlaceSettlement(Coordinate point, CatanColor color) throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public void PlaceCity(Coordinate point, CatanColor color) throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public void PlacePort(PortType type, Coordinate hexCoordinate, Coordinate edgeStart, Coordinate edgeEnd)
			throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public void PlaceRobber(Coordinate point) throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public void PlacePip(int value, Coordinate point) throws MapException
	{
		throw new MapException("Map model is unmodifiable.");
	}

	@Override
	public Hex GetHex(Coordinate point) throws MapException
	{
		return model.GetHex(point);
	}

	@Override
	public Iterator<Hex> GetHexes()
	{
		return model.GetHexes();
	}

	@Override
	public Edge GetEdge(Coordinate p1, Coordinate p2) throws MapException 
	{
		return model.GetEdge(p1, p2);
	}

	@Override
	public Iterator<Edge> GetEdges() 
	{
		return model.GetEdges();
	}

	@Override
	public Vertex GetVertex(Coordinate point) throws MapException
	{
		return model.GetVertex(point);
	}

	@Override
	public Iterator<Vertex> GetVertices() 
	{
		return model.GetVertices();
	}

	@Override
	public Iterator<Vertex> GetVertices(Hex hex)
	{
		return model.GetVertices(hex);
	}

	@Override
	public Iterator<Vertex> GetVertices(Vertex vertex)
	{
		return model.GetVertices(vertex);
	}
	
	@Override
	public Iterator<CatanColor> GetOccupiedVertices(Coordinate hexPoint)
	{
		return model.GetOccupiedVertices(hexPoint);
	}

	@Override
	public Iterator<Entry<Edge, Hex>> GetPorts()
	{
		return model.GetPorts();
	}
	
	@Override
	public Iterator<PortType> GetPorts(CatanColor color)
	{
		return model.GetPorts(color);
	}

	@Override
	public Hex GetRobberLocation()
	{
		return model.GetRobberLocation();
	}

	@Override
	public Iterator<Entry<Integer, List<Hex>>> GetPips()
	{
		return model.GetPips();
	}

	@Override
	public CatanColor GetLongestRoadColor() throws MapException
	{
		return model.GetLongestRoadColor();
	}

	@Override
	public Iterator<Transaction> GetTransactions(int role)
	{
		return model.GetTransactions(role);
	}
}

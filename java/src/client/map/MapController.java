package client.map;

import shared.definitions.*;
import shared.model.map.*;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import client.base.*;
import client.data.*;
import client.map.view.IMapView;


/**
 * Implementation for the map controller. Used to make updates or changes to
 * any object on the map.
 */
public class MapController extends Controller implements IMapController
{	
	private MapModel model;
	private IRobView robView;
	
	/**
	 * Creates a MapController object.
	 * @param view The MapView object.
	 * @param robView The RobberView object.
	 */
	public MapController(IMapView view, IRobView robView, MapModel model)
	{
		super(view);
		
		this.model = model;
		view.SetModel(model);
		
		setRobView(robView);
	}
	
	public IMapView getView()
	{	
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView()
	{
		return robView;
	}
	private void setRobView(IRobView robView)
	{
		this.robView = robView;
	}

	@Override
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color)
	{
		return model.CanPlaceRoad(p1, p2, color);
	}

	@Override
	public boolean CanPlaceSettlement(Coordinate point)
	{
		return model.CanPlaceSettlement(point);
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
	public Iterator<Hex> GetHexes()
	{
		return model.GetHexes();
	}

	@Override
	public Iterator<Edge> GetEdges()
	{
		return model.GetEdges();
	}

	@Override
	public Iterator<Vertex> GetVertices()
	{
		return model.GetVertices();
	}

	@Override
	public Iterator<Entry<Edge, Hex>> GetPorts()
	{
		return model.GetPorts();
	}

	@Override
	public Iterator<Entry<Integer, List<Hex>>> GetPips()
	{
		return model.GetPips();
	}

	@Override
	public Hex GetRobberPlacement() throws MapException
	{
		return model.GetRobberLocation();
	}

	@Override
	public void StartMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CancelMove() 
	{
		// TODO Auto-generated method stub
		
	}
}


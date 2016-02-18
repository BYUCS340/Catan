package client.map;

import shared.definitions.*;
import shared.model.map.*;
import shared.model.map.objects.*;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import client.base.*;
import client.map.view.IMapView;
import client.map.view.dropObject.*;
import client.model.ClientGame;


/**
 * Implementation for the map controller. Used to make updates or changes to
 * any object on the map.
 */
public class MapController extends Controller implements IMapController
{	
	private MapModel model;
	private DropObject dropObject;
	
	/**
	 * Creates a MapController object.
	 * @param view The MapView object.
	 * @param robView The RobberView object.
	 */
	public MapController(IMapView view, MapModel model)
	{
		super(view);
		
		this.model = model;
		
		this.dropObject = new NoDrop();
	}
	
	public IMapView getView()
	{	
		return (IMapView)super.getView();
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
	public boolean IsRobberInitialized()
	{
		return model.IsRobberInitialized();
	}
	
	@Override
	public DropObject GetDropObject()
	{
		return dropObject;
	}
	
	@Override
	public void PlaceRoad(Coordinate p1, Coordinate p2)
	{
		ClientGame.getGame().BuildRoad(p1, p2);
	}

	@Override
	public void PlaceSettlement(Coordinate point)
	{
		//TODO Add appropriate call		
	}

	@Override
	public void PlaceCity(Coordinate point)
	{
		//TODO Add appropriate call
	}

	@Override
	public void PlaceRobber(Coordinate point)
	{
		//TODO Add appropriate call
	}

	@Override
	public void StartMove(PieceType pieceType, CatanColor color, boolean allowDisconnected)
	{
		switch(pieceType)
		{
		case ROAD:
			dropObject = new RoadDropObject(this, color);
			break;
		case SETTLEMENT:
			dropObject = new SettlementDropObject(this, color);
			break;
		case CITY:
			dropObject = new CityDropObject(this, color);
			break;
		case ROBBER:
			dropObject = new RobberDropObject(this, color);
			break;
		default:
			dropObject = new NoDrop();
			break;
		}
	}

	@Override
	public void CancelMove() 
	{
		dropObject = new NoDrop();
	}

	@Override
	public void MouseMove(Point2D worldPoint)
	{
		dropObject.Handle(worldPoint);
	}

	@Override
	public void MouseClick()
	{
		if (dropObject.IsAllowed())
		{
			dropObject.Click();
			dropObject = new NoDrop();
		}
	}	

	@Override
	public IMapModel GetModel()
	{
		return model;
	}
}


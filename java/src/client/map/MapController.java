package client.map;

import shared.definitions.*;
import shared.model.ModelObserver;
import shared.model.map.*;
import shared.model.map.objects.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
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
	private List<MapObserver> observers;
	private DropObject dropObject;
	private boolean setup;
	
	/**
	 * Creates a MapController object.
	 * @param view The MapView object.
	 * @param robView The RobberView object.
	 */
	public MapController(IMapView view)
	{
		super(view);
		
		this.observers = new ArrayList<MapObserver>(3);
		this.dropObject = new NoDrop();
		this.setup = false;
		
		ClientGame.getGame().startListening(modelObserver, ModelNotification.MAP);
	}
	
	public IMapView getView()
	{	
		return (IMapView)super.getView();
	}

	@Override
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color)
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.CanPlaceRoad(p1, p2, color, setup);
	}

	@Override
	public boolean CanPlaceSettlement(Coordinate point, CatanColor color)
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.CanPlaceSettlement(point, color, setup);
	}

	@Override
	public boolean CanPlaceCity(Coordinate point, CatanColor color)
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.CanPlaceCity(point, color);
	}

	@Override
	public boolean CanPlaceRobber(Coordinate point)
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.CanPlaceRobber(point);
	}

	@Override
	public Iterator<Hex> GetHexes()
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.GetHexes();
	}

	@Override
	public Iterator<Edge> GetEdges()
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.GetEdges();
	}

	@Override
	public Iterator<Vertex> GetVertices()
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.GetVertices();
	}

	@Override
	public Iterator<Entry<Edge, Hex>> GetPorts()
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.GetPorts();
	}

	@Override
	public Iterator<Entry<Integer, List<Hex>>> GetPips()
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.GetPips();
	}

	@Override
	public Hex GetRobberPlacement() throws MapException
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.GetRobberLocation();
	}
	
	@Override
	public boolean IsRobberInitialized()
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
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
		return ClientGame.getGame().GetMapModel();
	}
	
	@Override 
	public void AddMapObserver(MapObserver listener)
	{
		observers.add(listener);
	}
	
	@Override
	public void CancelMove() 
	{
		dropObject = new NoDrop();
	}
	
	private void Refresh()
	{
		for (MapObserver observer : observers)
			observer.Refresh();
	}
	
	private void StartMove(PieceType pieceType, CatanColor color, boolean initialPlacement)
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

	private ModelObserver modelObserver = new ModelObserver()
	{
		@Override
		public void alert()
		{
			TurnState state = ClientGame.getGame().getTurnState();
			CatanColor color = ClientGame.getGame().myPlayerColor();
			
			switch (state)
			{
			case PLACING_PIECE:
				break;
			case FIRST_ROUND_MY_TURN:
				setup = true;
				break;
			case SECOND_ROUND_MY_TURN:
				setup = true;
				break;
			default:
				setup = false;
				Refresh();	
			}
		}
	};
}


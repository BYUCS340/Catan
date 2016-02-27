package client.map;

import shared.definitions.*;
import shared.model.ModelObserver;
import shared.model.map.*;
import shared.model.map.model.IMapModel;
import shared.model.map.objects.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import client.base.*;
import client.map.view.IMapView;
import client.map.view.dropObject.*;
import client.map.view.mapState.*;
import client.model.ClientGame;


/**
 * Implementation for the map controller. Used to make updates or changes to
 * any object on the map.
 */
public class MapController extends Controller implements IMapController
{
	private List<MapObserver> observers;
	private DropObject dropObject;
	private IMapState state;
	
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
		this.state = new NormalState(PieceType.NONE);
		
		ClientGame.getGame().startListening(modelObserver, ModelNotification.MAP);
		ClientGame.getGame().startListening(stateObserver, ModelNotification.STATE);
	}
	
	public IMapView getView()
	{	
		return (IMapView)super.getView();
	}

	@Override
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color)
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.CanPlaceRoad(p1, p2, color, state.IsSetup());
	}

	@Override
	public boolean CanPlaceSettlement(Coordinate point, CatanColor color)
	{
		IMapModel model = ClientGame.getGame().GetMapModel();
		return model.CanPlaceSettlement(point, color, state.IsSetup());
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
		ClientGame.getGame().BuildSettlement(point);
	}

	@Override
	public void PlaceCity(Coordinate point)
	{
		ClientGame.getGame().BuildCity(point);
	}

	@Override
	public void PlaceRobber(Coordinate point)
	{
		//TODO figure out the victim
//		/this.GetModel().
		ClientGame.getGame().PlaceRobber(1,point);
		
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
			
			boolean wasSetup = state.IsSetup();
			state = state.GetNextMapState();
			boolean isSetup = state.IsSetup();
			
			StartMove(state.GetPieceType());
			
			if (wasSetup && !isSetup)
				ClientGame.getGame().endTurn();
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
		
		EndDrag();
	}
	
	private void StartDrag(boolean allowCancel)
	{
		for (MapObserver observer : observers)
			observer.StartDrag(allowCancel);
	}
	
	private void EndDrag()
	{
		for (MapObserver observer : observers)
			observer.EndDrag();
	}
	
	private void StartMove(PieceType pieceType)
	{
		CatanColor color = ClientGame.getGame().myPlayerColor();
		
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
			
		}
	};
	
	private ModelObserver stateObserver = new ModelObserver()
	{
		@Override
		public void alert()
		{
			TurnState gameState = ClientGame.getGame().getTurnState();
			
			if (gameState == null)
				gameState = TurnState.WAITING;
			
			switch (gameState)
			{
				case PLACING_PIECE:
					//TODO Need way of figuring out what piece is being placed.
					state = new NormalState(ClientGame.getGame().myPlayerLastPiece());
					break;
				case FIRST_ROUND_MY_TURN:
				case SECOND_ROUND_MY_TURN:
					if (!state.IsSetup())
						state = new SettlementSetupState();
					break;
				case ROBBING:
					System.out.println(">>>We robbing");
					state = new RobbingState();
					break;
				default:
					state = new NormalState(PieceType.NONE);
					break;
			}
			
			StartMove(state.GetPieceType());
			StartDrag(!state.IsSetup());
		}
	};
}


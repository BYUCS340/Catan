package client.map;

import shared.definitions.*;
import shared.model.IMapController;
import shared.model.map.*;
import shared.model.map.objects.*;
import shared.model.map.MapException;
import java.util.Iterator;

import client.base.*;
import client.data.*;
import client.map.view.IMapView;


/**
 * Implementation for the map controller. Used to make updates or changes to
 * any object on the map.
 */
public class MapController extends Controller implements IMapController {
	
	private MapModel model;
	private IRobView robView;
	
	/**
	 * Creates a MapController object.
	 * @param view The MapView object.
	 * @param robView The RobberView object.
	 */
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		model = new MapModel();
		
		view.SetModel(model);
		
		setRobView(robView);
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}

	public boolean canPlaceRoad(Coordinate p1, Coordinate p2) {
		
		if (!model.ContainsEdge(p1, p2))
			return false;
		
		try {
			Edge edge = model.GetEdge(p1, p2);
			
			return !edge.doesRoadExists();
		} 
		catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean canPlaceSettlement(Coordinate point)
	{
		//TODO Needs to be like the lawyer and figure out who its neighbors are.
		
		if (!model.ContainsVertex(point))
			return false;
		
		try {
			Vertex vertex = model.GetVertex(point);
			
			return vertex.getType() == PieceType.NONE;
		} 
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean canPlaceCity(Coordinate point, CatanColor color) {
		
		if (!model.ContainsVertex(point))
			return false;
		
		try {
			Vertex vertex = model.GetVertex(point);
			
			return vertex.getType() == PieceType.SETTLEMENT && 
					vertex.getColor() == color;
		}
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean canPlaceRobber(Coordinate point) {
		
		if (!model.ContainsHex(point))
			return false;
		
		try {
			Hex hex = model.GetHex(point);
			
			return hex.getType() != HexType.WATER;
		}
		catch (MapException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets the settlements or villages that are associated with a role.
	 * @param role The role of the dice.
	 * @return The associated villages.
	 */
	public Iterator<Transaction> GetVillages(int role)
	{
		return null;
	}

	public void placeRoad(Coordinate p1, Coordinate p2, CatanColor color)
	{	
		try {
			model.SetRoad(p1, p2, color);
			getView().RefreshView();
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}

	public void placeSettlement(Coordinate point, CatanColor color)
	{
		try {
			model.SetSettlement(point, color);
			getView().RefreshView();
		} 
		catch (MapException e) 
		{
			e.printStackTrace();
		}
	}

	public void placeCity(Coordinate point, CatanColor color)
	{
		try {
			model.SetCity(point, color);
			getView().RefreshView();
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}

	public void placeRobber(Coordinate point)
	{
		try 
		{
			Hex hex = model.GetHex(point);
			model.SetRobber(hex);
		} 
		catch (MapException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}
	
}


package client.map;

import shared.definitions.*;
import shared.model.IMapController;
import shared.model.map.*;
import shared.model.map.objects.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public boolean canPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color) {
		
		if (!model.ContainsEdge(p1, p2))
			return false;
		
		try {
			Edge edge = model.GetEdge(p1, p2);
			
			if (edge.doesRoadExists())
				return false;
			
			if (VillagesSatisfyRoadPlacement(edge, color))
				return true;
			
			return RoadsSatisfyRoadPlacement(edge, color);
		} 
		catch (MapException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean canPlaceSettlement(Coordinate point)
	{
		if (!model.ContainsVertex(point))
			return false;
		
		try {
			Vertex vertex = model.GetVertex(point);
			
			if (vertex.getType() != PieceType.NONE)
				return false;
			
			Iterator<Vertex> neighbors = model.GetVerticies(vertex);
			
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
		
		try
		{
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
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		Iterator<Hex> hexes = model.GetHex(role);
		while (hexes.hasNext())
		{
			Hex hex = hexes.next();
			
			Iterator<Vertex> vertices = model.GetVerticies(hex);
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
		
		return java.util.Collections.unmodifiableList(transactions).iterator();
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
		//TODO Implement
	}
	
	public void playSoldierCard() {	
		//TODO Implement		
	}
	
	public void playRoadBuildingCard() {	
		//TODO Implement		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		//TODO Implement		
	}
	
	private boolean VillagesSatisfyRoadPlacement(Edge edge, CatanColor color) throws MapException
	{
		Vertex vStart = model.GetVertex(edge.getStart());
		if (vStart.getType() != PieceType.NONE && vStart.getColor() == color)
			return true;
		
		Vertex vEnd = model.GetVertex(edge.getEnd());
		if (vEnd.getType() != PieceType.NONE && vEnd.getColor() == color)
			return true;
		
		return false;
	}
	
	private boolean RoadsSatisfyRoadPlacement(Edge edge, CatanColor color) throws MapException
	{
		Vertex vStart = model.GetVertex(edge.getStart());
		Iterator<Edge> startEdges = model.GetEdges(vStart);
		while(startEdges.hasNext())
		{
			Edge edgeToCheck = startEdges.next();
			if (edgeToCheck.doesRoadExists() && edgeToCheck.getColor() == color)
				return true;
		}
		
		Vertex vEnd = model.GetVertex(edge.getEnd());
		Iterator<Edge> endEdges = model.GetEdges(vEnd);
		while(endEdges.hasNext())
		{
			Edge edgeToCheck = endEdges.next();
			if (edgeToCheck.doesRoadExists() && edgeToCheck.getColor() == color)
				return true;
		}
		
		return false;
	}
}


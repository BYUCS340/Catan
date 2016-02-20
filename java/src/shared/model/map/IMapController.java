package shared.model.map;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import client.base.*;
import shared.definitions.*;
import shared.model.map.objects.*;

/**
 * Interface for the map controller
 */
public interface IMapController extends IController
{
	/**
	 * This method is called whenever the user is trying to place a road on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the road to be placed at the
	 * specified location.
	 * 
	 * @param p1 The beginning of the road piece
	 * @param p2 The end of the road piece (it's the end of the road, get it? Never mind...)
	 * @return true if the road can be placed at edgeLoc, false otherwise
	 */
	boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color);
	
	/**
	 * This method is called whenever the user is trying to place a settlement
	 * on the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the settlement to
	 * be placed at the specified location.
	 * 
	 * @param point the location of the vertex
	 * @param color the color of the settlement being placed
	 * @param onRoad if the settlement should be placed on the road or not
	 * @return true if the settlement can be placed at vertLoc, false otherwise
	 */
	boolean CanPlaceSettlement(Coordinate point, CatanColor color, boolean onRoad);
	
	/**
	 * This method is called whenever the user is trying to place a city on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the city to be placed at the
	 * specified location.
	 * 
	 * @param point the location of the vertex
	 * @param color the color of the piece being placed
	 * @return true if the city can be placed at vertLoc, false otherwise
	 */
	boolean CanPlaceCity(Coordinate point, CatanColor color);
	
	/**
	 * This method is called whenever the user is trying to place the robber on
	 * the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the robber to be
	 * placed at the specified location.
	 * 
	 * @param point the coordinate of the hex
	 * @return true if the robber can be placed at hexLoc, false otherwise
	 */
	boolean CanPlaceRobber(Coordinate point);
	
	/**
	 * Gets all the hexes in the map.
	 * @return All the hexes.
	 */
	Iterator<Hex> GetHexes();
	
	/**
	 * Gets all the edges in the map.
	 * @return All the edges.
	 */
	Iterator<Edge> GetEdges();
	
	/**
	 * Gets all the vertices in the map.
	 * @return All the vertices.
	 */
	Iterator<Vertex> GetVertices();
	
	/**
	 * Gets all the ports on the map.
	 * @return All the ports.
	 */
	Iterator<Entry<Edge, Hex>> GetPorts();
	
	/**
	 * Gets all the pips in the map.
	 * @return All the pips.
	 */
	Iterator<Entry<Integer, List<Hex>>> GetPips();
	
	/**
	 * Gets the location of the robber.
	 * @return The hex of the robber.
	 */
	Hex GetRobberPlacement() throws MapException;
	
	void StartMove(PieceType pieceType, boolean isFree, boolean allowDisconnected);
	
	void CancelMove();
}


package shared.model.map;

import java.util.Iterator;
import java.util.List;

import client.base.*;
import client.data.*;
import shared.definitions.*;

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
	boolean canPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color);
	
	/**
	 * This method is called whenever the user is trying to place a settlement
	 * on the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the settlement to
	 * be placed at the specified location.
	 * 
	 * @param point the location of the vertex
	 * @return true if the settlement can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceSettlement(Coordinate point);
	
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
	boolean canPlaceCity(Coordinate point, CatanColor color);
	
	/**
	 * This method is called whenever the user is trying to place the robber on
	 * the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the robber to be
	 * placed at the specified location.
	 * 
	 * @param point the coordinate of the hex
	 * @return true if the robber can be placed at hexLoc, false otherwise
	 */
	boolean canPlaceRobber(Coordinate point);
	
	/**
	 * This method is called when the user clicks the mouse to place a road.
	 * 
	 * @param p1 the coordinate of the start of the road
	 * @param p2 the coordinate of the end of the road
	 * @param color the color of the road being placed
	 */
	void placeRoad(Coordinate p1, Coordinate p2, CatanColor color);
	
	/**
	 * This method is called when the user clicks the mouse to place a
	 * settlement.
	 * 
	 * @param point the coordinate of the vertex
	 * @param the color of the settlement being placed
	 */
	void placeSettlement(Coordinate point, CatanColor color);
	
	/**
	 * This method is called when the user clicks the mouse to place a city.
	 * 
	 * @param point the coordinate of the vertex
	 * @param color the color of the city being placed
	 */
	void placeCity(Coordinate point, CatanColor color);
	
	/**
	 * This method is called when the user clicks the mouse to place the robber.
	 * 
	 * @param point the coordinate of the hex
	 */
	void placeRobber(Coordinate point);
	
	/**
	 * Gets the settlements or villages that are associated with a role.
	 * @param role The role of the dice.
	 * @return The associated villages.
	 */
	public Iterator<Transaction> GetVillages(int role);
	
	/**
	 * This method is called when the user requests to place a piece on the map
	 * (road, city, or settlement)
	 * 
	 * @param pieceType
	 *            The type of piece to be placed
	 * @param isFree
	 *            true if the piece should not cost the player resources, false
	 *            otherwise. Set to true during initial setup and when a road
	 *            building card is played.
	 * @param allowDisconnected
	 *            true if the piece can be disconnected, false otherwise. Set to
	 *            true only during initial setup.
	 */
	void startMove(PieceType pieceType, boolean isFree,
				   boolean allowDisconnected);
	
	/**
	 * This method is called from the modal map overlay when the cancel button
	 * is pressed.
	 */
	void cancelMove();
	
	/**
	 * This method is called when the user plays a "soldier" development card.
	 * It should initiate robber placement.
	 */
	void playSoldierCard();
	
	/**
	 * This method is called when the user plays a "road building" progress
	 * development card. It should initiate the process of allowing the player
	 * to place two roads.
	 */
	void playRoadBuildingCard();
	
	/**
	 * This method is called by the Rob View when a player to rob is selected
	 * via a button click.
	 * 
	 * @param victim
	 *            The player to be robbed
	 */
	void robPlayer(RobPlayerInfo victim);
	
	
	/**
	 * This gets the list of ports that a player is connected to
	 * @param color the player color
	 * @return the list - possibly empty
	 */
	List<PortType> playerPorts(CatanColor color);
}


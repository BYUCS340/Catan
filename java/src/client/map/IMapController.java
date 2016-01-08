package client.map;

import client.base.*;
import client.data.*;
import shared.definitions.*;
import shared.locations.*;

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
	 * @param edgeLoc
	 *            The proposed road location
	 * @return true if the road can be placed at edgeLoc, false otherwise
	 */
	boolean canPlaceRoad(EdgeLocation edgeLoc);
	
	/**
	 * This method is called whenever the user is trying to place a settlement
	 * on the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the settlement to
	 * be placed at the specified location.
	 * 
	 * @param vertLoc
	 *            The proposed settlement location
	 * @return true if the settlement can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceSettlement(VertexLocation vertLoc);
	
	/**
	 * This method is called whenever the user is trying to place a city on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the city to be placed at the
	 * specified location.
	 * 
	 * @param vertLoc
	 *            The proposed city location
	 * @return true if the city can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceCity(VertexLocation vertLoc);
	
	/**
	 * This method is called whenever the user is trying to place the robber on
	 * the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the robber to be
	 * placed at the specified location.
	 * 
	 * @param hexLoc
	 *            The proposed robber location
	 * @return true if the robber can be placed at hexLoc, false otherwise
	 */
	boolean canPlaceRobber(HexLocation hexLoc);
	
	/**
	 * This method is called when the user clicks the mouse to place a road.
	 * 
	 * @param edgeLoc
	 *            The road location
	 */
	void placeRoad(EdgeLocation edgeLoc);
	
	/**
	 * This method is called when the user clicks the mouse to place a
	 * settlement.
	 * 
	 * @param vertLoc
	 *            The settlement location
	 */
	void placeSettlement(VertexLocation vertLoc);
	
	/**
	 * This method is called when the user clicks the mouse to place a city.
	 * 
	 * @param vertLoc
	 *            The city location
	 */
	void placeCity(VertexLocation vertLoc);
	
	/**
	 * This method is called when the user clicks the mouse to place the robber.
	 * 
	 * @param hexLoc
	 *            The robber location
	 */
	void placeRobber(HexLocation hexLoc);
	
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
}


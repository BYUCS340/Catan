package client.map;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;

/**
 * Interface for the map view
 */
public interface IMapView extends IView
{
	
	/**
	 * This method adds a hex to the map.
	 * 
	 * @param hexLoc
	 *            The hex's location
	 * @param hexType
	 *            The hex's type
	 */
	void addHex(HexLocation hexLoc, HexType hexType);
	
	/**
	 * This method adds a number to the map.
	 * 
	 * @param hexLoc
	 *            The number's location
	 * @param num
	 *            The number to add (must be in the range 2-6, 8-12)
	 */
	void addNumber(HexLocation hexLoc, int num);
	
	/**
	 * This method adds a port to the map.
	 * 
	 * @param edgeLoc
	 *            The port's location
	 * @param portType
	 *            The port's type
	 */
	void addPort(EdgeLocation edgeLoc, PortType portType);
	
	/**
	 * This method places a road on the map.
	 * 
	 * @param edgeLoc
	 *            The road's location
	 * @param color
	 *            The road's color
	 */
	void placeRoad(EdgeLocation edgeLoc, CatanColor color);
	
	/**
	 * This method places a settlement on the map.
	 * 
	 * @param vertLoc
	 *            The settlement's location
	 * @param color
	 *            The settlement's color
	 */
	void placeSettlement(VertexLocation vertLoc, CatanColor color);
	
	/**
	 * This method places a city on the map.
	 * 
	 * @param vertLoc
	 *            The city's location
	 * @param color
	 *            The city's color
	 */
	void placeCity(VertexLocation vertLoc, CatanColor color);
	
	/**
	 * This method places the robber on the map.
	 * 
	 * @param hexLoc
	 *            The robber's location
	 */
	void placeRobber(HexLocation hexLoc);
	
	/**
	 * This method displays the modal map overlay and allows the player to place
	 * the specified type of piece.
	 * 
	 * @param pieceType
	 *            The type of piece to be placed
	 * @param pieceColor
	 *            The piece color
	 * @param isCancelAllowed
	 *            true if the player may cancel out of the piece placement,
	 *            false otherwise
	 */
	void startDrop(PieceType pieceType, CatanColor pieceColor,
				   boolean isCancelAllowed);
}


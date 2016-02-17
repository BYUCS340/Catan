package client.map.view;

import shared.definitions.*;

import client.base.*;

/**
 * Interface for the map view
 */
public interface IMapView extends IView
{	
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
	
	/**
	 * Used to refresh the display.
	 */
	void RefreshView();
}


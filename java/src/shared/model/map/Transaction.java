package shared.model.map;

import shared.definitions.*;

/**
 * A transaction is used to transfer who receives what after a role. Thus, it stores the
 * hex type, the piece type (as that dictates quantity), and the color.
 * @author Jonathan Sadler
 *
 */
public class Transaction 
{
	private HexType hexType;
	private PieceType pieceType;
	private CatanColor color;
	
	/**
	 * Creates a transaction object.
	 * @param hexType The hex type.
	 * @param pieceType The piece type.
	 * @param color The color of the piece.
	 */
	public Transaction(HexType hexType, PieceType pieceType, CatanColor color)
	{
		this.hexType = hexType;
		this.pieceType = pieceType;
		this.color = color;
	}

	/**
	 * @return the hexType
	 */
	public HexType getHexType() {
		return hexType;
	}

	/**
	 * @return the pieceType
	 */
	public PieceType getPieceType() {
		return pieceType;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}

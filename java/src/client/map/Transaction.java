package client.map;

import shared.definitions.*;

public class Transaction {

	private HexType hexType;
	private PieceType pieceType;
	private CatanColor color;
	
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

package shared.networking.parameter;

import java.io.Serializable;

import shared.locations.HexLocation;

public class PSoldierCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7427874189043352063L;
	private int victimIndex;
	private HexLocation hexLocation;
	/**
	 * @return the victimIndex
	 */
	public int getVictimIndex() {
		return victimIndex;
	}
	/**
	 * @param victimIndex the victimIndex to set
	 */
	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	/**
	 * @return the hexLocation
	 */
	public HexLocation getHexLocation() {
		return hexLocation;
	}
	/**
	 * @param hexLocation the hexLocation to set
	 */
	public void setHexLocation(HexLocation hexLocation) {
		this.hexLocation = hexLocation;
	}
	
}

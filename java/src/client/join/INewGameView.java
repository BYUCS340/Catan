package client.join;

import client.base.*;

/**
 * Interface for the new game view, which lets the user enter parameters for a
 * new game
 */
public interface INewGameView extends IOverlayView
{
	
	/**
	 * Sets the value of the title field
	 * 
	 * @param value
	 *            New value
	 */
	void setTitle(String value);
	
	/**
	 * Gets the value of the title field
	 * 
	 * @return Current value
	 */
	String getTitle();
	
	/**
	 * Sets the value of the randomly place numbers check box
	 * 
	 * @param value
	 *            New value
	 */
	void setRandomlyPlaceNumbers(boolean value);
	
	/**
	 * Gets the value of the randomly place numbers check box
	 * 
	 * @return Current value
	 */
	boolean getRandomlyPlaceNumbers();
	
	/**
	 * Sets the value of the randomly place hexes check box
	 * 
	 * @param value
	 *            New value
	 */
	void setRandomlyPlaceHexes(boolean value);
	
	/**
	 * Gets the value of the randomly place hexes check box
	 * 
	 * @return Current value
	 */
	boolean getRandomlyPlaceHexes();
	
	/**
	 * Sets the value of the use random ports check box
	 * 
	 * @param value
	 *            New value
	 */
	void setUseRandomPorts(boolean value);
	
	/**
	 * Gets the value of the use random ports check box
	 * 
	 * @return Current value
	 */
	boolean getUseRandomPorts();
	
}


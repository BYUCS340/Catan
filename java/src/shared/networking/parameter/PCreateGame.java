package shared.networking.parameter;

import java.io.Serializable;

public class PCreateGame implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1050196711207540829L;
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String name;
	
	public PCreateGame()
	{
		
	}
	
	/**
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @param name
	 */
	public PCreateGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		super();
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.name = name;
	}
	
	/**
	 * @return the randomTiles
	 */
	public boolean isRandomTiles() {
		return randomTiles;
	}
	/**
	 * @param randomTiles the randomTiles to set
	 */
	public void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}
	/**
	 * @return the randomNumbers
	 */
	public boolean isRandomNumbers() {
		return randomNumbers;
	}
	/**
	 * @param randomNumbers the randomNumbers to set
	 */
	public void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}
	/**
	 * @return the randomPorts
	 */
	public boolean isRandomPorts() {
		return randomPorts;
	}
	/**
	 * @param randomPorts the randomPorts to set
	 */
	public void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}

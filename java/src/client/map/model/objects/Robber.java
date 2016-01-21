package client.map.model.objects;

/**
 * Used to store information about the robber.
 * @author Jonathan Sadler
 *
 */
public class Robber {

	private Hex placedOn;
	
	/**
	 * Creates a robber object and associates it with a hex.
	 * @param placeOn The hex the robber will be started on.
	 */
	public Robber(Hex placeOn)
	{
		placedOn = placeOn;
	}
	
	/**
	 * Moves the robber to a new hex.
	 * @param placeOn The hex to move the robber to.
	 */
	public void setRobber(Hex placeOn)
	{
		placedOn = placeOn;
	}
	
	/**
	 * Determines if the robber is on a certain hex.
	 * @param hex The hex in question.
	 * @return True if yes, else false.
	 */
	public boolean isOnHex(Hex hex)
	{
		return hex.equals(placedOn);
	}
}

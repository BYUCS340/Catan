package shared.model.map.objects;

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
	
	/**
	 * Gets the hex that the robber is on.
	 * @return The hex.
	 */
	public Hex GetHex()
	{
		return placedOn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placedOn == null) ? 0 : placedOn.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Robber))
			return false;
		Robber other = (Robber) obj;
		if (placedOn == null) {
			if (other.placedOn != null)
				return false;
		} else if (!placedOn.equals(other.placedOn))
			return false;
		return true;
	}
}

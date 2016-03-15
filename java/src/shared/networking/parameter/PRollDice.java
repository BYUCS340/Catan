package shared.networking.parameter;

import java.io.Serializable;

public class PRollDice implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8891732380278707079L;
	private int Roll;

	public PRollDice()
	{
		
	}
	
	/**
	 * @param roll
	 */
	public PRollDice(int roll)
	{
		super();
		Roll = roll;
	}

	/**
	 * @return the roll
	 */
	public int getRoll() {
		return Roll;
	}

	/**
	 * @param roll the roll to set
	 */
	public void setRoll(int roll) {
		Roll = roll;
	}
}

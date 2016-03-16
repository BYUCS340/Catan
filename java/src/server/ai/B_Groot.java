package server.ai;

import shared.definitions.AIType;

/**
 * Groot AI.
 * @author Jonathan Sadler
 *
 */
public class B_Groot extends AI 
{
	/**
	 * Plants Groot.
	 */
	public B_Groot() 
	{
		super(AIType.BEGINNER);
	}
	
	@Override
	public String GetName() 
	{
		return "Groot";
	}

	@Override
	void TakeTurn(int gameID) 
	{
		
	}
}

package server.ai.characters;

import server.ai.characters.personalities.BeginnerPersonality;
import server.ai.characters.personalities.GrootPersonality;
import shared.definitions.AIType;

/**
 * Groot AI.
 * @author Jonathan Sadler
 *
 */
public class B_Groot extends AI 
{
	private static final String NAME = "Groot";
	
	/**
	 * Plants Groot.
	 */
	public B_Groot() 
	{
		super(AIType.BEGINNER, new GrootPersonality(NAME));
	}
	
	@Override
	public String GetName() 
	{
		return NAME;
	}
	
	
}

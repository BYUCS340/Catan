package server.ai.characters;

import server.ai.characters.personalities.B_ChatterBotPersonality;
import server.ai.characters.personalities.GrootPersonality;
import shared.definitions.AIType;

/**
 * Groot AI.
 * @author Jonathan Sadler
 *
 */
public class B_Steve extends AI 
{
	private static final String NAME = "Steve";
	
	/**
	 * Plants Groot.
	 */
	public B_Steve() 
	{
		super(AIType.BEGINNER, new B_ChatterBotPersonality(NAME));
	}
	
	@Override
	public String GetName() 
	{
		return NAME;
	}
	
	
}

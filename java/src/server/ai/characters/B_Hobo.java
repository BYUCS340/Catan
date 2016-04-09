package server.ai.characters;

import server.ai.characters.personalities.B_CleverBotPersonality;
import shared.definitions.AIType;

/**
 * Groot AI.
 * @author Jonathan Sadler
 *
 */
public class B_Hobo extends AI 
{
	private static final String NAME = "Hobo";
	
	/**
	 * Plants Groot.
	 */
	public B_Hobo() 
	{
		super(AIType.BEGINNER, new B_CleverBotPersonality(NAME));
	}
	
	@Override
	public String GetName() 
	{
		return NAME;
	}
	
	
}

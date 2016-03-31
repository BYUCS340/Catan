package server.ai.characters;

import server.ai.characters.personalities.BeginnerPersonality;
import shared.definitions.AIType;

/**
 * Creates Trogdor.
 * @author Jonathan Sadler
 *
 */
public class B_Trogdor extends AI
{
	private static final String NAME = "Trogdor";
	
	/**
	 * Creates Trogdor.
	 */
	public B_Trogdor() 
	{
		super(AIType.BEGINNER, new BeginnerPersonality(NAME));
	}

	@Override
	public String GetName() 
	{
		return NAME;
	}

}

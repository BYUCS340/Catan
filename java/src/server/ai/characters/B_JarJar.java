package server.ai.characters;

import server.ai.characters.personalities.BeginnerPersonality;
import shared.definitions.AIType;

/**
 * Creates Jar Jar.
 * @author Jonathan Sadler
 *
 */
public class B_JarJar extends AI 
{
	private static final String NAME = "Jar Jar Binks";
	
	/**
	 * Creates Jar Jar.
	 */
	public B_JarJar()
	{
		super(AIType.BEGINNER, new BeginnerPersonality(NAME));
	}

	@Override
	public String GetName() 
	{
		return NAME;
	}

}

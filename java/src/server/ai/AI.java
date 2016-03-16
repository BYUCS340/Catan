package server.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import shared.definitions.AIType;
import shared.definitions.CatanColor;

/**
 * Abstract AI object. Implements general methods needed by AI characters and defines
 * needed abstract methods for operations.
 * @author Jonathan Sadler
 *
 */
public abstract class AI
{
	private int id;
	private AIType type;
	
	/**
	 * Constructs AI object.
	 * @param type The type of AI.
	 */
	protected AI(AIType type) 
	{	
		this.type = type;
	}
	
	/**
	 * Called to tell the AI to take its turn.
	 * @param gameID The ID of the game they are playing in.
	 */
	abstract void TakeTurn(int gameID);
	
	/**
	 * Gets the formal name of the AI.
	 * @return The AI's name.
	 */
	public abstract String GetName();

	/**
	 * Gets the AI type of the AI.
	 * @return The AI type.
	 */
	public AIType GetType()
	{
		return type;
	}
	
	/**
	 * Gets the player ID of the AI.
	 * @return The player ID.
	 */
	public int GetID()
	{
		return id;
	}
	
	/**
	 * Sets the ID of the AI.
	 * @param id The ID to set.
	 */
	public void SetID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Called to let a AI pick a color.
	 * @param notAvailable The colors not available to the AI as they are already picked.
	 * @return The selected color.
	 */
	public CatanColor PickColor(Set<CatanColor> notAvailable)
	{
		List<CatanColor> colors = GetAvailableColors(notAvailable);
		
		Random random = new Random();
		int color = random.nextInt(colors.size());
		return colors.get(color);
	}
	
	protected List<CatanColor> GetAvailableColors(Set<CatanColor> notAvailable)
	{
		List<CatanColor> allColors = Arrays.asList(CatanColor.values());
		List<CatanColor> colors = new ArrayList<CatanColor>();
		for (CatanColor color : allColors)
		{
			if (!notAvailable.contains(color))
				colors.add(color);
		}
		
		return colors;
	}
}

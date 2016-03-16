package server.ai.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import shared.definitions.AIType;
import shared.definitions.CatanColor;

public abstract class AI
{
	private int index;
	private AIType type;
	
	public AI(AIType type) 
	{	
		this.type = type;
	}

	public AIType GetType()
	{
		return type;
	}
	
	public int GetIndex()
	{
		return index;
	}
	
	public void SetIndex(int index)
	{
		this.index = index;
	}
	
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
	
	public abstract String GetName();
	
	abstract void TakeTurn();
}

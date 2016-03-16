package server.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import server.ai.types.AI;
import server.ai.types.B_Groot;
import shared.definitions.AIType;
import shared.definitions.CatanColor;

public class AIHandler 
{
	private List<String> types;
	private Map<AIType, Set<AI>> AIbyType;
	private Map<String, AI> AIbyName;
	private Map<Integer, AI> AIbyIndex;
	
	public AIHandler()
	{
		CompileTypes();
		AIbyType = new HashMap<AIType, Set<AI>>();
		AIbyName = new HashMap<String, AI>();
		AIbyIndex = new HashMap<Integer, AI>();
		
		AddAI(new B_Groot());
	}
	
	public List<String> GetTypes()
	{
		return java.util.Collections.unmodifiableList(types);
	}
	
	public void RegisterAI(String name, int playerIndex)
	{
		AI ai = AIbyName.get(name);
		ai.SetIndex(playerIndex);
		AIbyIndex.put(playerIndex, ai);
	}
	
	public Set<String> GetNames()
	{
		return java.util.Collections.unmodifiableSet(AIbyName.keySet());
	}
	
	public int GetAI(AIType type)
	{
		Set<AI> ais = null;
		
		if (type == AIType.RANDOM)
		{
			ais = new HashSet<AI>();
			ais.addAll(AIbyType.get(AIType.BEGINNER));
			ais.addAll(AIbyType.get(AIType.MODERATE));
			ais.addAll(AIbyType.get(AIType.EXPERT));
		}
		else
		{
			ais = AIbyType.get(type);
		}
			
		Random random = new Random();
		int aiIndex = random.nextInt(ais.size());
		
		AI[] aiList = ais.toArray(new AI[0]);
		
		return aiList[aiIndex].GetIndex();
	}
	
	public CatanColor PickColor(int index, Set<CatanColor> notAvailable)
	{
		return AIbyIndex.get(index).PickColor(notAvailable);
	}
	
	public String GetName(int index)
	{
		return AIbyIndex.get(index).GetName();
	}
	
	private void CompileTypes()
	{
		types = new ArrayList<String>();
		for (AIType type : AIType.values())
			types.add(AIType.toString(type));
	}
	
	private void AddAI(AI ai)
	{
		AIType type = ai.GetType();
		
		if (AIbyType.containsKey(type))
		{
			AIbyType.get(type).add(ai);
		}
		else
		{
			Set<AI> aiSet = new HashSet<AI>();
			aiSet.add(ai);
			AIbyType.put(type, aiSet);
		}
		
		AIbyName.put(ai.GetName(), ai);
	}
}

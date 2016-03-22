package server.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import server.Log;
import server.ai.characters.AI;
import server.ai.characters.B_Groot;
import server.ai.characters.B_JarJar;
import server.ai.characters.B_Trogdor;
import shared.definitions.AIType;
import shared.definitions.CatanColor;

/**
 * Class used for interacting with AI objects.
 * @author Jonathan Sadler
 *
 */
public class AIHandler 
{
	private static AIHandler handler = null;
	
	public static AIHandler GetHandler()
	{
		if (handler == null)
			handler = new AIHandler();
		
		return handler;
	}
	
	private List<String> types;
	private Map<AIType, Set<AI>> AIbyType;
	private Map<String, AI> AIbyName;
	private Map<Integer, AI> AIbyIndex;
	
	/**
	 * Creates an AI Handler
	 */
	private AIHandler()
	{
		CompileTypes();
		AIbyType = new HashMap<AIType, Set<AI>>();
		AIbyName = new HashMap<String, AI>();
		AIbyIndex = new HashMap<Integer, AI>();
		
		AddAI(new B_Groot());
		AddAI(new B_JarJar());
		AddAI(new B_Trogdor());
	}
	
	/**
	 * Sets the player ID of the AI player.
	 * @param name The AI's name.
	 * @param playerID The player ID to set.
	 */
	public void RegisterAI(String name, int playerID)
	{
		AI ai = AIbyName.get(name);
		ai.SetID(playerID);
		AIbyIndex.put(playerID, ai);
	}
	
	/**
	 * Gets the available types of AIs.
	 * @return A list of AI types.
	 */
	public List<String> GetTypes()
	{
		return java.util.Collections.unmodifiableList(types);
	}
	
	/**
	 * Gets the names of all available AIs.
	 * @return A list of names.
	 */
	public Set<String> GetNames()
	{
		return java.util.Collections.unmodifiableSet(AIbyName.keySet());
	}
	
	/**
	 * Gets the name of the AI based on ID.
	 * @param id The ID of the AI.
	 * @return The name of the AI.
	 */
	public String GetName(int id)
	{
		return AIbyIndex.get(id).GetName();
	}
	
	/**
	 * Gets a random AI associated with the desired type.
	 * @param type The AI.
	 * @return An AI associated with the type. If the type is random, then it returns
	 * an AI with a random type.
	 */
	public int GetAI(AIType type, List<Integer> inGame)
	{
		Set<AI> ais = null;
		
		if (type == AIType.RANDOM)
		{
			ais = new HashSet<AI>();
			ais.addAll(AIbyType.get(AIType.BEGINNER));
//			ais.addAll(AIbyType.get(AIType.MODERATE));
//			ais.addAll(AIbyType.get(AIType.EXPERT));
		}
		else
		{
			ais = AIbyType.get(type);
		}
		
		for (int gamer : inGame)
		{
			if (AIbyIndex.containsKey(gamer))
			{
				AI ai = AIbyIndex.get(gamer);
				ais.remove(ai);
			}
		}
			
		Random random = new Random();
		int aiIndex = random.nextInt(ais.size());
		
		AI[] aiList = ais.toArray(new AI[0]);
		
		return aiList[aiIndex].GetID();
	}
	
	/**
	 * Asks the AI to pick a color.
	 * @param id The ID of the AI.
	 * @param notAvailable The colors already selected.
	 * @return The selected color.
	 */
	public CatanColor PickColor(int id, Set<CatanColor> notAvailable)
	{
		return AIbyIndex.get(id).PickColor(notAvailable);
	}
	
	/**
	 * Tells an AI to begin its turn.
	 * @param aiID the ID of the AI.
	 * @param gameID The ID of the game.
	 */
	public void RunAI(int aiID, int gameID)
	{
		new AITakeTurn(aiID, gameID).start();;
	}
	
	/**
	 * Tells an AI to discard.
	 * @param aiID The ID of the AI.
	 * @param gameID The ID of the game.
	 */
	public void Discard(int aiID, int gameID)
	{
		new AIDiscard(aiID, gameID).start();
	}
	
	/**
	 * Tells an AI of a chat message.
	 * @param aiID The ID of the AI.
	 * @param gameID the ID of the game.
	 * @param message The message to give to the AI.
	 */
	public void Chat(int aiID, int gameID, String message)
	{
		new AIChat(aiID, gameID, message).start();
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
	
	private class AITakeTurn extends Thread
	{
		private int aiID;
		private int gameID;
		
		public AITakeTurn(int aiID, int gameID)
		{
			this.aiID = aiID;
			this.gameID = gameID;
		}
		
		@Override
		public void run()
		{
			try 
			{
				Thread.sleep(1000);
				AIbyIndex.get(aiID).TakeTurn(gameID);
			}
			catch (InterruptedException e) 
			{
				Log.GetLog().throwing("AIThread", "run", e);
				e.printStackTrace();
			}
		}
	}
	
	private class AIDiscard extends Thread
	{
		private int aiID;
		private int gameID;
		
		public AIDiscard(int aiID, int gameID)
		{
			this.aiID = aiID;
			this.gameID = gameID;
		}
		
		@Override
		public void run()
		{
			try 
			{
				Thread.sleep(1000);
				AIbyIndex.get(aiID).Discard(gameID);
			}
			catch (InterruptedException e) 
			{
				Log.GetLog().throwing("AIThread", "run", e);
				e.printStackTrace();
			}
		}
	}
	
	private class AIChat extends Thread
	{
		private int aiID;
		private int gameID;
		private String message;
		
		public AIChat(int aiID, int gameID, String message)
		{
			this.aiID = aiID;
			this.gameID = gameID;
			this.message = message;
		}
		
		@Override
		public void run()
		{
			try 
			{
				Thread.sleep(1000);
				AIbyIndex.get(aiID).Chat(gameID, message);
			}
			catch (InterruptedException e) 
			{
				Log.GetLog().throwing("AIThread", "run", e);
				e.printStackTrace();
			}
		}
	}
}

package server.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import server.Log;
import server.ai.characters.AI;
import server.ai.characters.B_Groot;
import server.ai.characters.B_Hobo;
import server.ai.characters.B_JarJar;
import server.ai.characters.B_Steve;
import server.ai.characters.B_Trogdor;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.model.OfferedTrade;

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
	
	private boolean enabled = false;
	private List<String> types;
	private Map<AIType, Set<AI>> AIbyType;
	private Map<String, AI> AIbyName;
	private Map<Integer, AI> AIbyID;
	
	/**
	 * Creates an AI Handler
	 */
	private AIHandler()
	{
		CompileTypes();
		AIbyType = new HashMap<AIType, Set<AI>>();
		AIbyName = new HashMap<String, AI>();
		AIbyID = new HashMap<Integer, AI>();
		
		AddAI(new B_Groot());
		AddAI(new B_JarJar());
		AddAI(new B_Trogdor());
		AddAI(new B_Steve());
		AddAI(new B_Hobo());
	}
	
	/**
	 * Enables the AI handler.
	 * @param enable True to enable, else false.
	 */
	public void EnableAIHandling(Boolean enable)
	{
		enabled = enable;
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
		AIbyID.put(playerID, ai);
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
		return AIbyID.get(id).GetName();
	}
	
	/**
	 * Gets a random AI associated with the desired type.
	 * @param type The AI.
	 * @return An AI associated with the type. If the type is random, then it returns
	 * an AI with a random type.
	 */
	public int GetAI(AIType type, List<Integer> inGame)
	{
		Set<AI> ais = new HashSet<AI>();
		Set<Integer> aiIndexs = new HashSet<>();
		if (type == AIType.RANDOM)
		{
			ais.addAll(AIbyType.get(AIType.BEGINNER));
//			ais.addAll(AIbyType.get(AIType.MODERATE));
//			ais.addAll(AIbyType.get(AIType.EXPERT));
		}
		else
		{
			ais = AIbyType.get(type);
		}
		
		Iterator<AI> iter = ais.iterator();
		while(iter.hasNext())
		{
			aiIndexs.add(iter.next().GetID());
		}
		//Log.GetLog().fine(aiIndexs.toString());
		//remove the AI's in use for this game
		for (int gamer : inGame)
		{
			//Log.GetLog().fine("Removing AI" + gamer);
			aiIndexs.remove(gamer);
		}
		//Log.GetLog().fine(aiIndexs.toString());
		
			
		Random random = new Random();
		if (aiIndexs.size() == 0) 
		{
			Log.GetLog().severe("NO AIS TO ADD");
			
		}
		int aiIndex = random.nextInt(aiIndexs.size());
		//Log.GetLog().finest("Adding AI id: "+aiIndex);
		Object[] aiList = aiIndexs.toArray();
		//Log.GetLog().finest(aiList.toString());
		
		
		return (int) aiList[aiIndex];
	}
	
	/**
	 * Asks the AI to pick a color.
	 * @param id The ID of the AI.
	 * @param notAvailable The colors already selected.
	 * @return The selected color.
	 */
	public CatanColor PickColor(int id, Set<CatanColor> notAvailable)
	{
		return AIbyID.get(id).PickColor(notAvailable);
	}
	
	/**
	 * Tells an AI to begin its turn.
	 * @param aiID the ID of the AI.
	 * @param gameID The ID of the game.
	 */
	public void RunAI(int aiID, int gameID)
	{
		new AITakeTurn(aiID, gameID).start();
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
	
	public void Trade(int aiID, int gameID, OfferedTrade trade)
	{
		Log.GetLog().fine("AI is considering Offer");
		new AITrade(aiID, gameID, trade).start();
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
			if (!enabled)
				return;
			
			try 
			{
				Thread.sleep(1000);
				AIbyID.get(aiID).TakeTurn(gameID);
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
			if (!enabled)
				return;
			
			try 
			{
				Thread.sleep(1000);
				AIbyID.get(aiID).Discard(gameID);
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
			if (!enabled)
				return;
			
			try 
			{
				Thread.sleep(1000);
				AIbyID.get(aiID).Chat(gameID, message);
			}
			catch (InterruptedException e) 
			{
				Log.GetLog().throwing("AIThread", "run", e);
				e.printStackTrace();
			}
		}
	}	
	private class AITrade extends Thread
	{
		private int aiID;
		private int gameID;
		private OfferedTrade trade;
		
		public AITrade(int aiID, int gameID, OfferedTrade trade)
		{
			this.aiID = aiID;
			this.gameID = gameID;
			this.trade = trade;
		}
		
		@Override
		public void run()
		{
			if (!enabled)
				return;
			
			try 
			{
				Thread.sleep(1000);
				AIbyID.get(aiID).ReceivedOffer(gameID, trade);
			}
			catch (InterruptedException e) 
			{
				Log.GetLog().throwing("AIThread", "run", e);
				e.printStackTrace();
			}
		}
	}
}

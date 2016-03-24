package server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import shared.data.GameInfo;

public class GameHandler 
{
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private Map<Integer, ServerGameManager> gameIndex;
	private Set<String> gameNames;
	
	public GameHandler() 
	{
		gameIndex = new HashMap<Integer, ServerGameManager>();
		gameNames = new HashSet<String>();
		
	}

	public Collection<ServerGameManager> GetAllGames()
	{
		lock.readLock().lock();
		try
		{
			return java.util.Collections.unmodifiableCollection(gameIndex.values());
		}
		finally
		{
			lock.readLock().unlock();
		}
	}
	
	public boolean ContainsGame(String name)
	{
		lock.readLock().lock();
		try
		{
			return gameNames.contains(name);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}
	
	public GameInfo AddGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{
		lock.writeLock().lock();
		try
		{
			int index = gameIndex.size();
			ServerGameManager sgm = new ServerGameManager(name, randomTiles, randomNumbers, randomPorts, index);
			gameIndex.put(index, sgm);
			gameNames.add(name);
			
			GameInfo info = new GameInfo();
			info.setId(index);
			info.setTitle(name);
			return info;
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * Gets a game object
	 * @param id the ID of the game
	 * @return The game associated with the ID.
	 * @throws GameException if the game is not found
	 */
	public ServerGameManager GetGame (int id) throws GameException
	{
		lock.readLock().lock();
		try
		{
			if (!gameIndex.containsKey(id))
				throw new GameException("Game " + id + " not found");
			else
				return gameIndex.get(id);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	public void SetGame(ServerGameManager sgm) {
		gameIndex.put(sgm.GetGameID(), sgm);
		gameNames.add(sgm.GetGameTitle());
		
	}
}

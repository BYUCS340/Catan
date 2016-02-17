package shared.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import shared.definitions.ModelNotification;

public class NotificationCenter {
	
	private Map<ModelNotification, Set<ModelObserver>> listeners = new HashMap<>();
	
	/**
	 * Adds a listener to a specific type
	 * @param listener
	 * @param type
	 */
	public void add(ModelObserver listener, ModelNotification type)
	{
		Set<ModelObserver> list = listeners.get(type);
		if (list == null){
			list = new HashSet<>();
			listeners.put(type, list);
		}
		list.add(listener);
		
	}
	
	/**
	 * Adds a listener for the all type
	 * @param listener
	 */
	public void add(ModelObserver listener)
	{
		this.add(listener,ModelNotification.ALL);
		
	}
	
	/**
	 * Removes a listener from all types
	 * @param listener
	 */
	public void remove(ModelObserver listener)
	{
		remove(listener,ModelNotification.ALL);
		remove(listener,ModelNotification.CHAT);
		remove(listener,ModelNotification.MAP);
		remove(listener,ModelNotification.PLAYERS);
		remove(listener,ModelNotification.RESOURCES);
		remove(listener,ModelNotification.STATE);
	}
	
	/**
	 * Removes a listener from a specific type
	 * @param listener
	 * @param type
	 */
	public void remove(ModelObserver listener, ModelNotification type)
	{
		Set<ModelObserver> list = listeners.get(type);
		list.remove(listener);
	}
	
	
	/**
	 * Poke the listeners for a specific type
	 * @param type
	 */
	public void notify(ModelNotification type)
	{
		//If we aren't notifying twice
		if (type != ModelNotification.ALL)
		{
			Set<ModelObserver> list = listeners.get(type);
			if (list != null)
				this.pokeListeners(list.iterator());
		}
		
		Set<ModelObserver> list = listeners.get(ModelNotification.ALL);
		//make sure we don't have a null list
		if (list != null)
			this.pokeListeners(list.iterator());
	}
	
	
	/**
	 * Poke the listeners in a list format
	 * @param observers
	 */
	private void pokeListeners(Iterator<ModelObserver> observers)
	{
		//Alert each observer
		while (observers.hasNext())
		{
			//TODO alert of the type
			observers.next().alert();
		}
	}
}

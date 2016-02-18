package shared.model;

import shared.definitions.ModelNotification;

public interface ModelSubject
{
	/**
	 * Starts listening for changes in model
	 * @param observer
	 * @return true if successful
	 */
	public boolean startListening(ModelObserver observer);
	
	/**
	 * starts listening for a specific type of change
	 * @param observer
	 * @return
	 */
	public boolean startListening(ModelObserver observer, ModelNotification typs);
	
	/**
	 * Stops listening for changes in model
	 * @param observer
	 * @return true if successful
	 */
	public boolean stopListening(ModelObserver observer);
}

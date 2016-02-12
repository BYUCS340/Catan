package shared.model;

public interface ModelSubject
{
	/**
	 * Starts listening for changes in model
	 * @param observer
	 * @return true if successful
	 */
	public boolean startListening(ModelObserver observer);
	
	/**
	 * Stops listening for changes in model
	 * @param observer
	 * @return true if successful
	 */
	public boolean stopListening(ModelObserver observer);
}

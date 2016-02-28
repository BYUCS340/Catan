package client.map;

public interface MapObserver
{
	/**
	 * Called when the drag state has begun. Also refreshes the view.
	 */
	public void StartDrag(boolean cancelAllowed);
	
	/**
	 * Called when the drag state has ended.
	 */
	public void EndDrag();
}

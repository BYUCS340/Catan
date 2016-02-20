package client.map;

public interface MapObserver
{
	/**
	 * Called when the drag state has begun.
	 */
	public void StartDrag(boolean cancelAllowed);
	
	/**
	 * Called when the drag state has ended.
	 */
	public void EndDrag();
	
	/**
	 * Called to refresh views.
	 */
	public void Refresh();
}

/**
 * 
 */
package shared.networking.parameter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Parker
 *
 */
public class POfferTrade implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5083072181763641916L;
	private List<Integer> resourceList;
	private int receiver;
	/**
	 * @return the resourceList
	 */
	public List<Integer> getResourceList() {
		return resourceList;
	}
	/**
	 * @param resourceList the resourceList to set
	 */
	public void setResourceList(List<Integer> resourceList) {
		this.resourceList = resourceList;
	}
	/**
	 * @return the receiver
	 */
	public int getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	
}

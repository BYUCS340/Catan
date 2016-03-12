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
public class PDiscardCards implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7996669937596791352L;
	List<Integer> resourceList;

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
	
}

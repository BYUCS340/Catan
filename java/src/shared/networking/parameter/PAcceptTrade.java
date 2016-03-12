/**
 * 
 */
package shared.networking.parameter;

import java.io.Serializable;

/**
 * @author Parker
 *
 */
public class PAcceptTrade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4381267533270491034L;
	private boolean willAccept;

	/**
	 * @return the willAccept
	 */
	public boolean isWillAccept() {
		return willAccept;
	}

	/**
	 * @param willAccept the willAccept to set
	 */
	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}
	
}

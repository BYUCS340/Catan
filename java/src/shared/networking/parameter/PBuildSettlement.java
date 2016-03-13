package shared.networking.parameter;

import java.io.Serializable;

import shared.locations.VertexLocation;

public class PBuildSettlement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 778817440625963270L;
	private VertexLocation vertexLocation;
	private boolean free;
	/**
	 * @return the vertexLocation
	 */
	public VertexLocation getVertexLocation() {
		return vertexLocation;
	}
	/**
	 * @param vertexLocation the vertexLocation to set
	 */
	public void setVertexLocation(VertexLocation vertexLocation) {
		this.vertexLocation = vertexLocation;
	}
	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}
	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free) {
		this.free = free;
	}
}

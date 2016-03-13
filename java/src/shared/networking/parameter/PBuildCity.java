package shared.networking.parameter;

import java.io.Serializable;

import shared.locations.VertexLocation;

public class PBuildCity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9091624117293202149L;
	private VertexLocation vertexLocation;

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
	
}

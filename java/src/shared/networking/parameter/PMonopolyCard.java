package shared.networking.parameter;

import java.io.Serializable;

import shared.definitions.ResourceType;

public class PMonopolyCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 608532874973153784L;
	private ResourceType resource;

	/**
	 * @return the resource
	 */
	public ResourceType getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceType resource) {
		this.resource = resource;
	}
}

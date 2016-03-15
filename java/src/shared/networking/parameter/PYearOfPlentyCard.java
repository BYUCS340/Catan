package shared.networking.parameter;

import java.io.Serializable;

import shared.definitions.ResourceType;

public class PYearOfPlentyCard implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7722715845460104624L;
	private ResourceType resource1;
	private ResourceType resource2;
	
	public PYearOfPlentyCard()
	{
		
	}
	
	/**
	 * @param resource1
	 * @param resource2
	 */
	public PYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
	{
		super();
		this.resource1 = resource1;
		this.resource2 = resource2;
	}
	/**
	 * @return the resource1
	 */
	public ResourceType getResource1() {
		return resource1;
	}
	/**
	 * @param resource1 the resource1 to set
	 */
	public void setResource1(ResourceType resource1) {
		this.resource1 = resource1;
	}
	/**
	 * @return the resource2
	 */
	public ResourceType getResource2() {
		return resource2;
	}
	/**
	 * @param resource2 the resource2 to set
	 */
	public void setResource2(ResourceType resource2) {
		this.resource2 = resource2;
	}
}

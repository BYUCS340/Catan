package shared.networking.parameter;

import java.io.Serializable;

import shared.definitions.AIType;

public class PAddAI implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -464336451681336425L;
	private String AIType;

	public PAddAI()
	{
		
	}
	
	/**
	 * 
	 * @param aiType
	 */
	public PAddAI(AIType aiType)
	{
		super();
		this.AIType = shared.definitions.AIType.toString(aiType);
	}

	/**
	 * @return the aiType
	 */
	public AIType getAiType()
	{
		return shared.definitions.AIType.fromString(AIType);
	}

	/**
	 * @param aiType the aiType to set
	 */
	public void setAiType(AIType aiType) 
	{
		this.AIType = shared.definitions.AIType.toString(aiType);
	}
}

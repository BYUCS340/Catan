package shared.networking.parameter;

import java.io.Serializable;

import shared.definitions.AIType;

public class PAddAI implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -464336451681336425L;
	private AIType aiType;

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
		this.aiType = aiType;
	}

	/**
	 * @return the aiType
	 */
	public AIType getAiType() {
		return aiType;
	}

	/**
	 * @param aiType the aiType to set
	 */
	public void setAiType(AIType aiType) {
		this.aiType = aiType;
	}
}

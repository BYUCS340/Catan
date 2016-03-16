package server.ai.types;

import shared.definitions.AIType;

public class B_Groot extends AI {

	public B_Groot() 
	{
		super(AIType.BEGINNER);
	}
	
	@Override
	public String GetName() 
	{
		return "Groot";
	}

	@Override
	void TakeTurn() 
	{
		
	}
}

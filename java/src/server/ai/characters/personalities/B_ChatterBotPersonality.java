package server.ai.characters.personalities;

import server.ai.chatbots.ChatterBot;
import server.ai.chatbots.ChatterBotFactory;
import server.ai.chatbots.ChatterBotSession;
import server.ai.chatbots.ChatterBotType;

public class B_ChatterBotPersonality extends server.ai.characters.personalities.BeginnerPersonality {

	private ChatterBotSession botsession = null;
	
	public B_ChatterBotPersonality(String username) {
		super(username);
		ChatterBotFactory factory = new ChatterBotFactory();
		
		try 
		{
			ChatterBot bot;
			bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
			botsession = bot.createSession();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

	}
	
	@Override
	public void ChatReceived(int gameID, String message)
	{
		//only response 75% of the time
		if (botsession != null && Math.random() <= .6)
		{
			try 
			{
				//So an alternative is to have a unique session for each game and create new ones on the fly
				String response = botsession.think(message);
				this.SendChat(gameID, response);
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

package server.ai.characters.personalities;

import server.ai.chatbots.ChatterBot;
import server.ai.chatbots.ChatterBotFactory;
import server.ai.chatbots.ChatterBotSession;
import server.ai.chatbots.ChatterBotType;

public class B_CleverBotPersonality extends server.ai.characters.personalities.BeginnerPersonality {

	private ChatterBotSession botsession = null;
	
	public B_CleverBotPersonality(String username) {
		super(username);
		ChatterBotFactory factory = new ChatterBotFactory();
		
		try 
		{
			ChatterBot bot;
			bot = factory.create(ChatterBotType.CLEVERBOT);
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
		//only respond 75% of the time
		if (botsession != null && Math.random() <= .6)
		{
			try 
			{
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

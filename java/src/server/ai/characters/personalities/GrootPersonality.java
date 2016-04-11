package server.ai.characters.personalities;


public class GrootPersonality extends server.ai.characters.personalities.BeginnerPersonality {

	public GrootPersonality(String username) {
		super(username);
	}
	
	@Override
	public void ChatReceived(int gameID, String message)
	{
		if (message.toLowerCase().equals("i am groot"))
			this.SendChat(gameID, "I AM "+this.username);
		else if (message.indexOf('?') >= 0)
			this.SendChat(gameID, "I am "+this.username);
	}

}

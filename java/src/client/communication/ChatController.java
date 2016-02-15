package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import client.model.ClientGame;
import shared.definitions.CatanColor;
import shared.model.GameManager;
import shared.model.ModelObserver;
import shared.model.chat.ChatBox;
import shared.model.chat.ChatMessage;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, ModelObserver {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		ClientGame.getGame().SendChat(message);
	}

	@Override
	public void alert()
	{
		GameManager mng = ClientGame.getGame();
		ChatBox chat = mng.getChat();
		
		//go through messages and make a list of LogEntry objects
		//to pass to the view
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		int numChats = chat.size();
		
		for(int i = 0; i < numChats; i++)
		{
			ChatMessage tempChat = chat.get(i);
			int playerIndex = tempChat.getPlayerId();
			CatanColor col =  mng.getPlayerColorByIndex(playerIndex);
			LogEntry tempEntry = new LogEntry(col, tempChat.getMessage());
			entries.add(tempEntry);
		}
		
		this.getView().setEntries(entries);
		
		
	}

}


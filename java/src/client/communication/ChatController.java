package client.communication;

import client.base.Controller;
import client.model.ClientGame;
import shared.model.ModelObserver;
import shared.model.chat.ChatBox;


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
		// TODO Access the model to get chat messages
		ChatBox chat = ClientGame.getGame().getChat();
		
	}

}


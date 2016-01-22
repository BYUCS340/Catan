package shared.model.chat;

import java.util.List;

/**
 * Stores the chat messages for different players. Should probably extend an immutable list
 * @author matthewcarlson
 *
 */
public class ChatBox {
	List <ChatMessage> messages;
	
	public void put(String message, int playerID){
		ChatMessage mess = new ChatMessage(playerID,message);
		messages.add(e);
	}
	public ChatMessage get(int i){
		return null;
	}
}

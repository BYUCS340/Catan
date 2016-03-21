package shared.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the chat messages for different players. Should probably extend an immutable list
 * @author matthewcarlson
 *
 */
public class ChatBox implements Serializable
{
	private static final long serialVersionUID = -5653973592529483133L;

	private List <ChatMessage> messages;
	
	/**
	 * Default constructor; initializes message list
	 */
	public ChatBox()
	{
		messages = new ArrayList<>();
	}
	
	/**
	 * Construct a message and put it in the list
	 * @param message the message
	 * @param playerID the ID of the player who sent the message
	 */
	public void put(String message, int playerID)
	{
		ChatMessage mess = new ChatMessage(playerID, message);
		messages.add(mess);
	}
	
	/**
	 * Get a chat message
	 * @param i the chat message to get
	 * @return a chat message
	 */
	public ChatMessage get(int i)
	{
		return messages.get(i);
	}
	
	/**
	 * Get the size of the chat
	 * @return the size
	 */
	public int size()
	{
		return messages.size();
	}
	
	/**
	 * Gets the index of the last player to chat
	 * @return
	 */
	public int lastChatter()
	{
		if (messages.size() == 0) return -1;
		return messages.get(messages.size() - 1).playerId;
	}
}

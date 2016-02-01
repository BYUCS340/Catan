package shared.model.chat;
/**
 * A wrapper for a chat message
 * @author matthewcarlson
 *
 */
public class ChatMessage
{
	
	public int playerId;   //the player's id 0-3
	public String message; //the message itself
	public long timestamp; //time the message was sent in milliseconds
	
	/**
	 * Creates a chatmessage
	 * @param playerID
	 * @param message
	 */
	public ChatMessage(int playerID, String message)
	{
		if (playerID < 0 || playerID > 3) playerID = -1;
		this.playerId = playerID;
		this.message = message;
		timestamp = System.currentTimeMillis();
	}
	public ChatMessage(int playerID, String message, long timestamp)
	{
		this(playerID, message);
		this.timestamp = timestamp;
	}
	
}

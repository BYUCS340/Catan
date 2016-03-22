package shared.model.chat;

import java.io.Serializable;

/**
 * A wrapper for a chat message
 * @author matthewcarlson
 *
 */
public class ChatMessage implements Serializable
{
	private static final long serialVersionUID = -1998360309558925902L;

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
	/**
	 * @return the playerId
	 */
	public int getPlayerId()
	{
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId)
	{
		this.playerId = playerId;
	}
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp()
	{
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}
	
}

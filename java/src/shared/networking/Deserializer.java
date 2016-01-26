package shared.networking;

import shared.networking.transport.NetAI;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetPlayer;

public interface Deserializer
{
	/**
	 * Parses a NetAI from serialized data
	 * @param rawData The serialized data
	 * @return a NetAI Object
	 */
	public NetAI parseNetAI(String rawData);
	
	/**
	 * Parses a NetGame from serialized data
	 * @param rawData The serialized data
	 * @return a NetGame object
	 */
	public NetGame parseNetGame(String rawData);
	
	/**
	 * Parses a NetPlayer from serialized data
	 * @param rawData The serialized data
	 * @return a NetPlayer object
	 */
	public NetPlayer parseNetPlayer(String rawData);
	
	/**
	 * Parses a NetGameModel from serialized data
	 * @param rawData The serialized data
	 * @return a NetGameModel object
	 */
	public NetGameModel parseNetGameModel(String rawData);
	
}

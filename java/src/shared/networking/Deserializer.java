package shared.networking;

import java.util.List;

import shared.definitions.AIType;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetPlayer;

public interface Deserializer
{
	/**
	 * Parses JSON data into an AI Type
	 * @param rawData The serialized data
	 * @return an AIType based on the data
	 */
	public List<AIType> parseAIList(String rawData);
	
	/**
	 * Parses a NetGame from serialized data
	 * @param rawData The serialized data
	 * @return a NetGame object
	 */
	public NetGame parseNetGame(String rawData);
	
	/**
	 * Parses a List of NetGame objects from serialized data
	 * @param rawData The serialized data
	 * @return a List of NetGame objects
	 */
	public List<NetGame> parseNetGameList(String rawData);
	
	/**
	 * Parses a NetPlayer from serialized data
	 * @param rawData The serialized data
	 * @return a NetPlayer object
	 */
	public NetPlayer parsePartialNetPlayer(String rawData);
	
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

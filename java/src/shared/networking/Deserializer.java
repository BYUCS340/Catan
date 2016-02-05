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
	 * @throws Exception 
	 */
	public List<AIType> parseAIList(String rawData) throws Exception;
	
	/**
	 * Parses a NetGame from serialized data
	 * @param rawData The serialized data
	 * @return a NetGame object
	 * @throws Exception 
	 */
	public NetGame parseNetGame(String rawData) throws Exception;
	
	/**
	 * Parses a List of NetGame objects from serialized data
	 * @param rawData The serialized data
	 * @return a List of NetGame objects
	 * @throws Exception 
	 */
	public List<NetGame> parseNetGameList(String rawData) throws Exception;
	
	/**
	 * Parses a NetPlayer from serialized data
	 * @param rawData The serialized data
	 * @return a NetPlayer object
	 * @throws Exception 
	 */
	public NetPlayer parsePartialNetPlayer(String rawData) throws Exception;
	
	/**
	 * Parses a NetPlayer from serialized data
	 * @param rawData The serialized data
	 * @return a NetPlayer object
	 * @throws Exception 
	 */
	public NetPlayer parseNetPlayer(String rawData) throws Exception;
	
	/**
	 * Parses a NetGameModel from serialized data
	 * @param rawData The serialized data
	 * @return a NetGameModel object
	 * @throws Exception 
	 */
	public NetGameModel parseNetGameModel(String rawData) throws Exception;
	
}

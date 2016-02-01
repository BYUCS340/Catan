package shared.model;

import java.util.List;

import client.map.MapController;
import shared.model.chat.ChatBox;

/**
 * A simple data holder for communication between ServerProxy and GameManager
 * @author Garrett
 *
 */
public class GameModel
{
	public MapController mapController;
	public GameState gameState;
	public Bank gameBank;
	public List<Player> players;
	public VictoryPointManager victoryPointManager;
	public ChatBox waterCooler;
	public GameActionLog log;
	public int version;
}

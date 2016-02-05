package shared.model;

import java.util.List;

import shared.model.chat.ChatBox;
import shared.model.map.MapModel;

/**
 * A simple data holder for communication between ServerProxy and GameManager
 * @author Garrett
 *
 */
public class GameModel
{
	public MapModel mapModel;
	public GameState gameState;
	public Bank gameBank;
	public List<Player> players;
	public VictoryPointManager victoryPointManager;
	public ChatBox waterCooler;
	public GameActionLog log;
	public int version;
}

package shared.model;

import java.util.List;

import shared.model.chat.ChatBox;

/**
 * A simple data holder for communication between ServerProxy and GameManager
 * @author Garrett
 *
 */
public class GameModel
{
	public GameActionLog gameActionLog;
	public List<Player> players;
	public ChatBox chatBox;
	
	public int winner;
	public int version;
}

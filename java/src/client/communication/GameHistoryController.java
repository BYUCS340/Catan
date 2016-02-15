package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import client.model.ClientGame;
import client.model.ClientGameManager;
import shared.definitions.CatanColor;
import shared.model.GameActionLog;
import shared.model.ModelObserver;


/**
 * Game history controller implementation
 * @author parkerridd
 */
public class GameHistoryController extends Controller implements IGameHistoryController, ModelObserver {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() 
	{
		//get needed objects
		ClientGameManager game = ClientGame.getGame();
		GameActionLog log = game.getGameActionLog();
		
		//populate the list of LogEntries
		List<LogEntry> entries = new ArrayList<LogEntry>();
		int numEntries = log.size();
		
		for(int i = 0; i < numEntries; i++)
		{
			int playerIndex = log.getPlayerIndex(i);
			CatanColor col = game.getPlayerColorByIndex(playerIndex);
			LogEntry tempEntry = new LogEntry(col, log.getAction(i));
			entries.add(tempEntry);
		}
			
		getView().setEntries(entries);
	}

	@Override
	public void alert()
	{
		this.initFromModel();
	}
	
}


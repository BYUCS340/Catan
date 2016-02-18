package client.turntracker;

import client.base.Controller;
import client.model.ClientGame;
import shared.definitions.CatanColor;
import shared.model.ModelObserver;
import shared.model.VictoryPointManager;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, ModelObserver {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
	}

	@Override
	public void alert()
	{
		// TODO Auto-generated method stub
		VictoryPointManager vp = ClientGame.getGame().getVictoryPointManager();
		
	}

}


package client.turntracker;

import client.base.Controller;
import client.model.ClientGame;
import client.model.ClientGameManager;
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
		ClientGame.getGame().endTurn();
	}
	
	private void initFromModel() {
		ClientGameManager game = ClientGame.getGame();
		int myIndex = game.myPlayerID();
		VictoryPointManager vp = game.getVictoryPointManager();
		
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
		
		//set views for each player
		for(int i = 0; i < 3; i++)
		{
			
			//0. See if it is this player's turn and highlight if it is
			
			

			//1. if the current player has the longest road or the largest army, 
			//display icon on turn tracker
			
			boolean largestArmy = false;
			boolean longestRoad = false;
			if(vp.getCurrentLargestArmyPlayer() == i)
			{
				largestArmy = true;
			}
			
			if(vp.getCurrentLongestRoadPlayer() == i)
			{
				longestRoad = true;
			}

		}
		
		//2. Enable finish turn button on "playing" state and not "discarding" or "rolling"
		//3. update points display with amount of points the player has		

		
		
	}

	@Override
	public void alert()
	{
		// TODO Auto-generated method stub
		VictoryPointManager vp = ClientGame.getGame().getVictoryPointManager();
		
	}

}


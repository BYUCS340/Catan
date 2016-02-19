package client.turntracker;

import client.base.Controller;
import client.model.ClientGame;
import client.model.ClientGameManager;
import shared.definitions.CatanColor;
import shared.definitions.GameRound;
import shared.model.ModelObserver;
import shared.model.VictoryPointManager;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, ModelObserver {
	
	private boolean isInitialized;

	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		ClientGame.getGame().startListening(this);
		isInitialized = false;
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		ClientGame.getGame().endTurn();
	}
	
	private void initializeTurns()
	{
		ClientGameManager game = ClientGame.getGame();
		int myIndex = game.myPlayerID();
		CatanColor myColor = game.getPlayerColorByIndex(myIndex);
		
		for(int i = 0; i < 4; i++)
		{
			getView().initializePlayer(i, game.getPlayerNameByIndex(i), game.getPlayerColorByIndex(i));
		}

		getView().setLocalPlayerColor(myColor);
	}
	
	private void updateFromModel() {
		ClientGameManager game = ClientGame.getGame();
		int myIndex = game.myPlayerID();
		VictoryPointManager vp = game.getVictoryPointManager();
		
		int currPlayerIndex = game.CurrentPlayersTurn();
		
		if(!isInitialized)
		{
			initializeTurns();
			isInitialized = true;
		}
		
		//update view for each player
		for(int i = 0; i < 4; i++)
		{
			boolean highlight = false;
			//0. See if it is this player's turn and highlight if it is
			if(currPlayerIndex == i)
			{
				highlight = true;
			}
			
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
			
			int points = vp.getVictoryPoints(i);
			getView().updatePlayer(i, points, highlight, largestArmy, longestRoad);
		}


		if(game.CanFinishTurn())
		{
			this.getView().updateGameState("Finish Turn", true);
		}		
		else
		{
			this.getView().updateGameState("Waiting for other players", false);
		}
	}

	@Override
	public void alert()
	{
		ClientGameManager game = ClientGame.getGame();
		
		//OJO if the version number wraps around to -1, THIS WILL NOT WORK
		if(game.GetVersion() != -1 && game.hasGameStarted())
		{
			this.updateFromModel();
		}

	}

}


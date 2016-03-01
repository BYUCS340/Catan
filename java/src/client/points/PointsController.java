package client.points;

import client.base.Controller;
import client.model.ClientGame;
import client.model.ClientGameManager;
import shared.definitions.ModelNotification;
import shared.model.ModelObserver;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, ModelObserver
{

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView)
	{
		super(view);
		
		setFinishedView(finishedView);
		ClientGame.getGame().startListening(this, ModelNotification.ALL );
		
		updateFromModel();
	}
	
	public IPointsView getPointsView()
	{
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView()
	{
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView)
	{
		this.finishedView = finishedView;
	}

	private void updateFromModel()
	{
		ClientGameManager game = ClientGame.getGame();
		int points = game.PlayerPoints();
		getPointsView().setPoints(points);
		
		//if there's a winner, set the modal and show it
		if(game.getVictoryPointManager().anyWinner())
		{
			
			//If we're the winner, show the right modal!
			int winnerIdx = game.getVictoryPointManager().winner();
			this.getFinishedView().setWinner(game.getPlayerNameByIndex(winnerIdx), winnerIdx == game.myPlayerIndex());
			this.getFinishedView().showModal();
		}
		
	}

	@Override
	public void alert() {
		updateFromModel();
	}
	
}


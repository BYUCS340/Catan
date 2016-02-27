package client.points;

import client.base.*;
import client.model.ClientGame;
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
		ClientGame.getGame().startListening(this, ModelNotification.SCORE);
		
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
		int points = ClientGame.getGame().PlayerPoints();
		getPointsView().setPoints(points);
	}

	@Override
	public void alert() {
		// TODO Auto-generated method stub
		updateFromModel();
	}
	
}


package client.points;

import client.base.*;
import client.model.ClientGame;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController
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
		
		initFromModel();
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

	private void initFromModel()
	{
		int points = ClientGame.getGame().PlayerPoints();
		getPointsView().setPoints(points);
	}
	
}


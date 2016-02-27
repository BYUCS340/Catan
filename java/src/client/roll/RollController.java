package client.roll;

import client.base.Controller;
import client.model.ClientGame;
import shared.definitions.ModelNotification;
import shared.definitions.TurnState;
import shared.model.ModelObserver;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, ModelObserver {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		ClientGame.getGame().startListening(this, ModelNotification.STATE);
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		int roll = ClientGame.getGame().RollDice();
		getResultView().setRollValue(roll);
		getResultView().showModal();
	}

	@Override
	public void alert()
	{
		if(ClientGame.getGame().getTurnState() == TurnState.ROLLING)
		{
			this.getRollView().showModal();
		}	
		else if(this.getRollView().isModalShowing())
		{
			this.getRollView().closeModal();
		}
	}

}


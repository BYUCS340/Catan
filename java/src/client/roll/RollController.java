package client.roll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import client.base.Controller;
import client.model.ClientGame;
import shared.definitions.ModelNotification;
import shared.definitions.TurnState;
import shared.model.ModelObserver;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, ModelObserver, ActionListener {

	private IRollResultView resultView;
	private Timer timer;
	
	private static final int TIMER_INTERVAL = 3000;

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
		timer = new Timer(TIMER_INTERVAL, this);
		
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
		if (roll == 0) return;
		getResultView().setRollValue(roll);
		getResultView().showModal();
		
		//the timer should be stopped
		if(timer.isRunning())
		{
			timer.stop();
		}
	}

	@Override
	public void alert()
	{
		//the modal should only be shown during the rolling state
		if(ClientGame.getGame().getTurnState() == TurnState.ROLLING)
		{
			this.getRollView().showModal();
			timer.start();
		}	
		
		//if we're not rolling, why the heck is the roll view showing?
		//tell it to stop.
		else if(this.getRollView().isModalShowing())
		{
			this.getRollView().closeModal();
			if(timer.isRunning())
			{
				timer.stop();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//this should ONLY be called if the modal is open
		//if not, the exception thrown should be a sanity check
		this.getRollView().closeModal();
		this.rollDice();
	}

}


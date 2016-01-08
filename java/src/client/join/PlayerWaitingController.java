package client.join;

import client.base.*;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {

		getView().showModal();
	}

	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
	}

}


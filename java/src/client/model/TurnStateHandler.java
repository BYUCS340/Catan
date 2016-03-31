package client.model;

import shared.definitions.ModelNotification;
import shared.definitions.TurnState;
import shared.model.NotificationCenter;

/**
 * Ensures proper handling of the turn state.
 * @author Jonathan Sadler
 *
 */
public class TurnStateHandler 
{
	private NotificationCenter notificationCenter;
	private TurnState state;
	
	/**
	 * Creates a turn state handler.
	 * @param notificationCenter The notification center for the turn state handler.
	 */
	public TurnStateHandler(NotificationCenter notificationCenter) 
	{
		this.notificationCenter = notificationCenter;
		state = TurnState.WAITING_FOR_PLAYERS;
	}

	/**
	 * Gets the current turn state.
	 * @return The turn state.
	 */
	public TurnState GetState()
	{
		return state;
	}
	
	/**
	 * Sets the turn state.
	 * @param state The state to set.
	 */
	public void SetState(TurnState state)
	{
		this.state = state;
		
		notificationCenter.notify(ModelNotification.STATE);
	}

	/**
	 * Checks if the state is the turn state.
	 * @param state The state to check.
	 * @return True if yes, else false.
	 */
	public boolean IsState(TurnState state)
	{
		return this.state == state;
	}
}

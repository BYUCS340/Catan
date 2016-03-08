package client.map.view.mapState;

import shared.definitions.PieceType;

/**
 * The map state handles what the map should do in special cases, for instance during setup
 * when a settlement and road should be placed consecutively.
 * @author Jonathan Sadler
 *
 */
public interface IMapState
{
	/**
	 * Gets the piece type associated with the current state.
	 * @return The current piece type.
	 */
	public PieceType GetPieceType();
	
	/**
	 * Gets the next state after the completion of the current state.
	 * @return The next state object.
	 */
	public IMapState GetNextMapState();
	
	/**
	 * Returns if the current state is a map setup state.
	 * @return True if yes, else false.
	 */
	public Boolean IsSetup();
	
	/**
	 * Returns if the current state allows cancelling placement.
	 * @return True if yes, else false.
	 */
	public Boolean AllowCancel();
}

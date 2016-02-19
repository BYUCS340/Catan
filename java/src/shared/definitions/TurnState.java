package shared.definitions;

public enum TurnState
{
	WAITING_FOR_PLAYERS, WAITING, PLAYING, ROBBING, ROLLING, DISCARDING, DOMESTIC_TRADE, OFFERED_TRADE, MARITIME_TRADE, PLACING_PIECE,
		FIRST_ROUND_MY_TURN, FIRST_ROUND_WAITING, SECOND_ROUND_MY_TURN, SECOND_ROUND_WAITING, GAME_OVER();
}

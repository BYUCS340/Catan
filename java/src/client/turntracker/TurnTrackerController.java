package client.turntracker;

import client.base.Controller;
import client.model.ClientGame;
import client.model.ClientGameManager;
import shared.definitions.CatanColor;
import shared.definitions.GameRound;
import shared.definitions.ModelNotification;
import shared.definitions.TurnState;
import shared.model.ModelObserver;
import shared.model.VictoryPointManager;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, ModelObserver {
	
	private int isInitializedTo;

	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		ClientGame.getGame().startListening(this, ModelNotification.STATE);
		ClientGame.getGame().startListening(this, ModelNotification.SCORE);
		ClientGame.getGame().startListening(this, ModelNotification.PLAYERS);
		ClientGame.getGame().startListening(chatObserver, ModelNotification.CHAT);
		
		isInitializedTo = 0;
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
		int myIndex = game.myPlayerIndex();
		CatanColor myColor = game.getPlayerColorByIndex(myIndex);
		
		for(int i = 0; i < game.getNumberPlayers(); i++)
		{
			getView().initializePlayer(i, game.getPlayerNameByIndex(i), game.getPlayerColorByIndex(i));
		}

		getView().setLocalPlayerColor(myColor);
	}
	
	private void updateFromModel() {
		ClientGameManager game = ClientGame.getGame();
		int myIndex = game.myPlayerIndex();
		VictoryPointManager vp = game.getVictoryPointManager();
		
		int currPlayerIndex = game.CurrentPlayersTurn();
		int currNumPlayers = game.getNumberPlayers();
		
		if(isInitializedTo < currNumPlayers )
		{
			initializeTurns();
			isInitializedTo = currNumPlayers;
		}
		
		//update view for each player; 

		for(int i = 0; i < game.getNumberPlayers();i++)
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
		
		if (game.CurrentState() == GameRound.ROLLING && currPlayerIndex == myIndex)
		{
			String soundName = "images"+File.separator+"yourTurn.wav";    
			AudioInputStream audioInputStream;
			audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			} catch (UnsupportedAudioFileException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Clip clip;
			try {
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


		if(game.CanFinishTurn() && currPlayerIndex == myIndex)
		{
			this.getView().updateGameState("Finish Turn", true);
		}
		else if(game.getTurnState() == TurnState.DISCARDING)
		{
			this.getView().updateGameState("Discarding...", false);
		}
		else if(game.getTurnState() == TurnState.ROBBING)
		{
			this.getView().updateGameState("Robbing...", false);
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
		//System.out.println("Trying to update on turn tracker");
		if(game.hasGameStarted())
		{
			//System.out.println("Update on turn tracker");
			this.updateFromModel();
		}

	}
	
	private ModelObserver chatObserver = new ModelObserver()
	{
		@Override
		public void alert()
		{
			String soundName = "images"+File.separator+"yourTurn.wav";    
			AudioInputStream audioInputStream;
			audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			} catch (UnsupportedAudioFileException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Clip clip;
			try {
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

}


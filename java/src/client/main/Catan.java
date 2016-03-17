package client.main;

import javax.swing.*;

import client.catan.*;
import client.login.*;
import client.join.*;
import client.misc.*;
import client.model.ClientGame;
import client.networking.RealEarlyServerProxy;
import client.base.*;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame
{
	private CatanPanel catanPanel;
	
	public Catan(String host, int port)
	{
		client.base.OverlayView.setWindow(this);
		
		this.setTitle("Settlers of Catan");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		RealEarlyServerProxy sp = new RealEarlyServerProxy(host, port);
		ClientGame.startGameWithProxy(sp);
		
		catanPanel = new CatanPanel();
		this.setContentPane(catanPanel);
		
		display();
	}
	
	private void display()
	{
		pack();
		setVisible(true);
	}
	
	//
	// Main
	//
	
	public static void main(final String[] args)
	{
		String host = "localhost";
		int port = 8081;
		
		if (args.length == 2)
		{
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Run(host, port);
	}
	
	private static void Run(final String host, final int port)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new Catan(host, port);
				
				PlayerWaitingView playerWaitingView = new PlayerWaitingView();
				final PlayerWaitingController playerWaitingController = new PlayerWaitingController(
																									playerWaitingView);
				playerWaitingView.setController(playerWaitingController);
				
				JoinGameView joinView = new JoinGameView();
				NewGameView newGameView = new NewGameView();
				SelectColorView selectColorView = new SelectColorView();
				MessageView joinMessageView = new MessageView();
				final JoinGameController joinController = new JoinGameController(
																				 joinView,
																				 newGameView,
																				 selectColorView,
																				 joinMessageView);
				joinController.setJoinAction(new IAction() {
					@Override
					public void execute()
					{
						playerWaitingController.start();
					}
				});
				joinView.setController(joinController);
				newGameView.setController(joinController);
				selectColorView.setController(joinController);
				joinMessageView.setController(joinController);
				
				LoginView loginView = new LoginView();
				MessageView loginMessageView = new MessageView();
				LoginController loginController = new LoginController(
																	  loginView,
																	  loginMessageView);
				loginController.setLoginAction(new IAction() {
					@Override
					public void execute()
					{
						joinController.start();
					}
				});
				loginView.setController(loginController);
				loginView.setController(loginController);
				
				loginController.start();
			}
		});
	}	
}


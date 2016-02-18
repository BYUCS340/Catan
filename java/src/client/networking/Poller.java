package client.networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import client.model.ClientGame;
import shared.model.ModelException;

/**
 * The poller
 * @author matthewcarlson
 *
 */
public class Poller implements ActionListener 
{
	//Refresh every second and half
	private final static int delay = 1500;
	private Timer timer;
	
	/**
	 * Initializes the poller with a certain poll interval and uses the ServerProxy
	 * passed as the object to poll
	 * @param pollInterval The period of polling, in milliseconds
	 * @param serverProxy The ServerProxy object to poll
	 */
	public Poller()
	{
		this(delay);
		
	}
	public Poller(int delay)
	{
		timer = new javax.swing.Timer(delay,(ActionListener) this);
		timer.addActionListener((ActionListener) this);
		timer.setRepeats(true);
		
	}
	
	/**
	 * Polls the server at the interval specified at the instantiation of the Poller.
	 */
	public void beginPolling()
	{
		timer.start();
	}
	
	/**
	 * Causes the client to stop polling the server.
	 */
	public void stopPolling()
	{
		timer.stop();
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		//System.out.println("Poller has fired");
		try {
			ClientGame.getGame().RefreshFromServer();
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to poll server");
			e.printStackTrace();
		}
	}

}

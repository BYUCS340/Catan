package testing.client.networking;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.model.ClientGame;
import client.networking.Poller;

public class TestClientPoller {
	Poller poller;
	@Before
	public void setUp() throws Exception {
		ClientGame.startGameWithProxy(null);
		poller = new Poller();
	}

	@After
	public void tearDown() throws Exception {
		poller.stopPolling();
	}

	@Test
	public void test() throws InterruptedException {
		poller.beginPolling();
		//Not sure how to check if poller began polling
		
		//We need some sort of delay here ideally
		if (ClientGame.getGame().GetVersion() == -1)
		{
			//fail("Refresh did not happen");
		}
	}

}

package testing.client.networking;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.model.ClientGame;
import client.networking.MockServerProxy;
import client.networking.Poller;

public class TestClientPoller {
	Poller poller;
	@Before
	public void setUp() throws Exception {
		ClientGame.startGameWithProxy(new MockServerProxy());
		poller = new Poller(1);
	}

	@After
	public void tearDown() throws Exception {
		poller.stopPolling();
	}

	@Test
	public void test() throws InterruptedException {
		poller.beginPolling();
		//Not sure how to check if poller began polling
		int i = 1;
		assertEquals(ClientGame.getGame().GetRefreshCount(), 0);
		Thread.sleep(5);
		
		while(ClientGame.getGame().GetRefreshCount() == 0 && i != 0) {
			i++;
			Thread.sleep(1);
		}
		//System.out.println("Polled the server");
		poller.stopPolling();
		assertNotEquals(ClientGame.getGame().GetRefreshCount(), 0);
		if (i == 0) fail("Timeout on poll");
		
	}

}

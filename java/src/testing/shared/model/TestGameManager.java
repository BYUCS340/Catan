package testing.shared.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.CatanColor;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.Player;

public class TestGameManager {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testPlayerAdding()
	{
		GameManager gm = new GameManager();
		try {
			int index = gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			assertEquals(index,0);
			index = gm.AddPlayer("Matt2", CatanColor.RED, false);
			assertEquals(index,1);
			index = gm.AddPlayer("Matt3", CatanColor.GREEN, false);
			assertEquals(index,2);
			index = gm.AddPlayer("Matt4", CatanColor.YELLOW, false);
			assertEquals(index,3);
			
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
	}

}

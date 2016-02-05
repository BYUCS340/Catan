package testing.shared.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.ModelException;

public class TestBank {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bank = new Bank();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	private Bank bank;

	@Test
	public void test() {
		//check to make sure we can't buy something
		assertFalse(bank.canBuildCity());
		assertFalse(bank.canBuildRoad());
		assertFalse(bank.canBuildSettlement());
		assertFalse(bank.canBuyDevCard());
		
		try 
		{
			bank.resetToPlayerDefaults();
			bank.giveResource(ResourceType.BRICK);
			bank.giveResource(ResourceType.WOOD);
			assertTrue(bank.canBuildRoad());
			bank.buildRoad();
			assertFalse(bank.canBuildRoad());
			
			
			
		} 
		catch (ModelException e) 
		{
			fail("We shouldn't have an exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Test settlement building exceotion
		try {
			bank.buildSettlement();
			fail("Can't build a settlement");
		} catch (ModelException e) {
			//We need to get here
		}
		
		//Test buy dev card building exceotion
		try {
			bank.buyDevCard();
			fail("Can't buy a dev card");
		} catch (ModelException e) {
			//We need to get here
		}
		
		//Test road building exceotion
		try {
			bank.buildRoad();
			fail("Can't build a road");
		} catch (ModelException e) {
			//We need to get here
		}
		
		//Test city building exceotion
		try {
			bank.buildCity();
			fail("Can't build a city");
		} catch (ModelException e) {
			//We need to get here
		}
		
		
		
		//build a settlement (one wood, one brick, one sheep, one wheat, one settlement)
		try{
			bank.giveResource(ResourceType.WOOD);
			assertFalse(bank.canBuildSettlement());
			bank.giveResource(ResourceType.BRICK);
			assertFalse(bank.canBuildSettlement());
			bank.giveResource(ResourceType.SHEEP);
			assertFalse(bank.canBuildSettlement());
			bank.giveResource(ResourceType.WHEAT);
			assertTrue(bank.canBuildSettlement());
			bank.buildSettlement();
			assertFalse(bank.canBuildSettlement());
		}
		catch (ModelException e) {
			fail("We should be able to build a settlement");
		}
		

		//build a city (two wheat, three ore, one city)
		try{
			bank.giveResource(ResourceType.ORE,3);
			assertFalse(bank.canBuildSettlement());
			bank.giveResource(ResourceType.WHEAT,2);
			assertTrue(bank.canBuildCity());
			bank.buildCity();
			assertFalse(bank.canBuildCity());
		}
		catch (ModelException e) {
			fail("We should be able to build a settlement");
		}
	}
}

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
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

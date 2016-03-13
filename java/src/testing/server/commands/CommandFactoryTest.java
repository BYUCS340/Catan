package testing.server.commands;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.commands.*;
import server.commands.game.*;
import server.commands.games.*;
import server.commands.moves.*;
import server.commands.user.*;
import server.commands.util.*;

public class CommandFactoryTest 
{
	private static CommandFactory factory;
	
	private int playerID = -1;
	private String object = null; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		factory = CommandFactory.GetCommandFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		factory = null;
	}
	
	
	@Test(expected=InvalidFactoryParameterException.class)
	public void TestInvalidParameter() throws InvalidFactoryParameterException
	{
		String url = "invalid";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		factory.GetCommand(param, playerID, object);
	}

	@Test
	public void TestUserLogin() throws InvalidFactoryParameterException
	{
		String url = "user/login";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == UserLoginCommand.class);
	}
	
	@Test
	public void TestUserRegister() throws InvalidFactoryParameterException
	{
		String url = "user/register";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == UserRegisterCommand.class);
	}
	
	@Test
	public void TestGamesList() throws InvalidFactoryParameterException
	{
		String url = "games/list";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GamesListCommand.class);
	}
	
	@Test
	public void TestGamesCreate() throws InvalidFactoryParameterException
	{
		String url = "games/create";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GamesCreateCommand.class);
	}
	
	@Test
	public void TestGamesJoin() throws InvalidFactoryParameterException
	{
		String url = "games/join";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GamesJoinCommand.class);
	}
	
	@Test
	public void TestGamesSave() throws InvalidFactoryParameterException
	{
		String url = "games/save";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GamesSaveCommand.class);
	}
	
	@Test
	public void TestGamesLoad() throws InvalidFactoryParameterException
	{
		String url = "games/load";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GamesLoadCommand.class);
	}
	
	@Test
	public void TestGameModel() throws InvalidFactoryParameterException
	{
		String url = "game/model";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GameModelCommand.class);
	}
	
	@Test
	public void TestGameReset() throws InvalidFactoryParameterException
	{
		String url = "game/reset";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GameResetCommand.class);
	}
	
	@Test
	public void TestGameCommands() throws InvalidFactoryParameterException
	{
		String url = "game/commands";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GameCommandsCommand.class);
	}
	
	@Test
	public void TestGameAddAI() throws InvalidFactoryParameterException
	{
		String url = "game/addai";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GameAddAICommand.class);
	}
	
	@Test
	public void TestGameListAI() throws InvalidFactoryParameterException
	{
		String url = "game/listai";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == GameListAICommand.class);
	}
	
	@Test
	public void TestMovesSendChat() throws InvalidFactoryParameterException
	{
		String url = "moves/sendchat";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesSendChatCommand.class);
	}
	
	@Test
	public void TestMovesRollNumber() throws InvalidFactoryParameterException
	{
		String url = "moves/rollnumber";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesRollNumberCommand.class);
	}
	
	@Test
	public void TestMovesRobPlayer() throws InvalidFactoryParameterException
	{
		String url = "moves/robplayer";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesRobPlayerCommand.class);
	}
	
	@Test
	public void TestMovesFinishTurn() throws InvalidFactoryParameterException
	{
		String url = "moves/finishturn";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesFinishTurnCommand.class);
	}
	
	@Test
	public void TestMovesBuyDevCard() throws InvalidFactoryParameterException
	{
		String url = "moves/buydevcard";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesBuyDevCardCommand.class);
	}
	
	@Test
	public void TestMovesYearOfPlenty() throws InvalidFactoryParameterException
	{
		String url = "moves/yearofplenty";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesYearOfPlentyCommand.class);
	}
	
	@Test
	public void TestMovesYear_Of_Plenty() throws InvalidFactoryParameterException
	{
		String url = "moves/year_of_plenty";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesYearOfPlentyCommand.class);
	}
	
	@Test
	public void TestMovesRoadBuilding() throws InvalidFactoryParameterException
	{
		String url = "moves/roadbuilding";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesRoadBuildingCommand.class);
	}
	
	@Test
	public void TestMovesRoad_Builder() throws InvalidFactoryParameterException
	{
		String url = "moves/road_building";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesRoadBuildingCommand.class);
	}
	
	@Test
	public void TestMovesSoldier() throws InvalidFactoryParameterException
	{
		String url = "moves/soldier";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesSoldierCommand.class);
	}
	
	@Test
	public void TestMovesMonopoly() throws InvalidFactoryParameterException
	{
		String url = "moves/monopoly";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesMonopolyCommand.class);
	}
	
	@Test
	public void TestMovesMonument() throws InvalidFactoryParameterException
	{
		String url = "moves/monument";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesMonumentCommand.class);
	}
	
	@Test
	public void TestMovesBuildRoad() throws InvalidFactoryParameterException
	{
		String url = "moves/buildroad";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesBuildRoadCommand.class);
	}
	
	@Test
	public void TestMovesBuildSettlement() throws InvalidFactoryParameterException
	{
		String url = "moves/buildsettlement";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesBuildSettlementCommand.class);
	}
	
	@Test
	public void TestMovesBuildCity() throws InvalidFactoryParameterException
	{
		String url = "moves/buildcity";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesBuildCityCommand.class);
	}
	
	@Test
	public void TestMovesOfferTrade() throws InvalidFactoryParameterException
	{
		String url = "moves/offertrade";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesOfferTradeCommand.class);
	}
	
	@Test
	public void TestMovesAcceptTrade() throws InvalidFactoryParameterException
	{
		String url = "moves/accepttrade";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesAcceptTradeCommand.class);
	}
	
	@Test
	public void TestMovesMaritimeTrade() throws InvalidFactoryParameterException
	{
		String url = "moves/maritimetrade";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesMaritimeTradeCommand.class);
	}
	
	@Test
	public void TestMovesDiscardCards() throws InvalidFactoryParameterException
	{
		String url = "moves/discardcards";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == MovesDiscardCardsCommand.class);
	}
	
	@Test
	public void TestUtilChangeLogLevel() throws InvalidFactoryParameterException
	{
		String url = "util/changeloglevel";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, playerID, object);
		
		assertTrue(command.getClass() == UtilChangeLogLevelCommand.class);
	}
}

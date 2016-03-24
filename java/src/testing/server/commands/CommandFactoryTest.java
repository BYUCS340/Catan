package testing.server.commands;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.commands.InvalidFactoryParameterException;
import server.commands.game.GameAddAICommand;
import server.commands.game.GameCommandsCommand;
import server.commands.game.GameListAICommand;
import server.commands.game.GameModelCommand;
import server.commands.game.GameResetCommand;
import server.commands.games.GamesCreateCommand;
import server.commands.games.GamesJoinCommand;
import server.commands.games.GamesListCommand;
import server.commands.games.GamesLoadCommand;
import server.commands.games.GamesSaveCommand;
import server.commands.moves.MovesAcceptTradeCommand;
import server.commands.moves.MovesBuildCityCommand;
import server.commands.moves.MovesBuildRoadCommand;
import server.commands.moves.MovesBuildSettlementCommand;
import server.commands.moves.MovesBuyDevCardCommand;
import server.commands.moves.MovesDiscardCardsCommand;
import server.commands.moves.MovesFinishTurnCommand;
import server.commands.moves.MovesMaritimeTradeCommand;
import server.commands.moves.MovesMonopolyCommand;
import server.commands.moves.MovesMonumentCommand;
import server.commands.moves.MovesOfferTradeCommand;
import server.commands.moves.MovesRoadBuildingCommand;
import server.commands.moves.MovesRobPlayerCommand;
import server.commands.moves.MovesRollNumberCommand;
import server.commands.moves.MovesSendChatCommand;
import server.commands.moves.MovesSoldierCommand;
import server.commands.moves.MovesYearOfPlentyCommand;
import server.commands.user.UserLoginCommand;
import server.commands.user.UserRegisterCommand;
import server.commands.util.UtilChangeLogLevelCommand;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.model.map.Coordinate;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;
import shared.networking.parameter.PAcceptTrade;
import shared.networking.parameter.PAddAI;
import shared.networking.parameter.PBuildCity;
import shared.networking.parameter.PBuildRoad;
import shared.networking.parameter.PBuildSettlement;
import shared.networking.parameter.PCreateGame;
import shared.networking.parameter.PCredentials;
import shared.networking.parameter.PDiscardCards;
import shared.networking.parameter.PGetModel;
import shared.networking.parameter.PJoinGame;
import shared.networking.parameter.PMaritimeTrade;
import shared.networking.parameter.PMonopolyCard;
import shared.networking.parameter.POfferTrade;
import shared.networking.parameter.PRoadBuildingCard;
import shared.networking.parameter.PRobPlayer;
import shared.networking.parameter.PRollDice;
import shared.networking.parameter.PSendChat;
import shared.networking.parameter.PSoldierCard;
import shared.networking.parameter.PYearOfPlentyCard;

public class CommandFactoryTest 
{
	private static CommandFactory factory;
	
	private NetworkCookie cookie;
	private String object; 
	
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

	@Before
	public void setUp() throws Exception
	{
		cookie = new NetworkCookie("username", "password", 1);
		object = null;
	}
	
	@After
	public void tearDown() throws Exception
	{
		cookie = null;
		object = null;
	}
	
	
	@Test(expected=InvalidFactoryParameterException.class)
	public void TestInvalidParameter() throws InvalidFactoryParameterException
	{
		String url = "invalid";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		factory.GetCommand(param, cookie, object);
	}

	@Test
	public void TestUserLogin() throws InvalidFactoryParameterException
	{
		String url = "user/login";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PCredentials credentials = new PCredentials();
		credentials.setUsername("Jon");
		credentials.setPassword("Sadler");
		
		object = SerializationUtils.serialize(credentials);
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == UserLoginCommand.class);
	}
	
	@Test
	public void TestUserRegister() throws InvalidFactoryParameterException
	{
		String url = "user/register";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PCredentials credentials = new PCredentials();
		credentials.setUsername("Jon");
		credentials.setPassword("Sadler");
		
		String object = SerializationUtils.serialize(credentials);
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == UserRegisterCommand.class);
	}
	
	@Test
	public void TestGamesList() throws InvalidFactoryParameterException
	{
		String url = "games/list";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GamesListCommand.class);
	}
	
	@Test
	public void TestGamesCreate() throws InvalidFactoryParameterException
	{
		String url = "games/create";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PCreateGame create = new PCreateGame(false, false, true, "hellotest");
		String object = SerializationUtils.serialize(create);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GamesCreateCommand.class);
	}
	
	@Test
	public void TestGamesJoin() throws InvalidFactoryParameterException
	{
		String url = "games/join";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PJoinGame join = new PJoinGame(0, CatanColor.BLUE);
		String object = SerializationUtils.serialize(join);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		assertTrue(command.getClass() == GamesJoinCommand.class);
	}
	
	@Test
	public void TestGamesSave() throws InvalidFactoryParameterException
	{
		String url = "games/save";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GamesSaveCommand.class);
	}
	
	@Test
	public void TestGamesLoad() throws InvalidFactoryParameterException
	{
		String url = "games/load";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		//ICommand command = factory.GetCommand(param, cookie, object);
		
		//assertTrue(command.getClass() == GamesLoadCommand.class);
	}
	
	@Test
	public void TestGameModel() throws InvalidFactoryParameterException
	{
		String url = "game/model";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		PGetModel model = new PGetModel(2141);
		String object = SerializationUtils.serialize(model);
		//ICommand command = factory.GetCommand(param, cookie, object);
		
		//assertTrue(command.getClass() == GameModelCommand.class);
	}
	
	@Test
	public void TestGameReset() throws InvalidFactoryParameterException
	{
		String url = "game/reset";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GameResetCommand.class);
	}
	
	@Test
	public void TestGameCommands() throws InvalidFactoryParameterException
	{
		String url = "game/commands";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GameCommandsCommand.class);
	}
	
	@Test
	public void TestGameAddAI() throws InvalidFactoryParameterException
	{
		String url = "game/addai";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PAddAI addai = new PAddAI(AIType.BEGINNER);
		String object = SerializationUtils.serialize(addai);
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GameAddAICommand.class);
	}
	
	@Test
	public void TestGameListAI() throws InvalidFactoryParameterException
	{
		String url = "game/listai";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == GameListAICommand.class);
	}
	
	@Test
	public void TestMovesSendChat() throws InvalidFactoryParameterException
	{
		String url = "moves/sendchat";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PSendChat chat = new PSendChat();
		chat.setContent("I'm a lasagna hog");
		String object = SerializationUtils.serialize(chat);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesSendChatCommand.class);
	}
	
	@Test
	public void TestMovesRollNumber() throws InvalidFactoryParameterException
	{
		String url = "moves/rollnumber";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PRollDice dice = new PRollDice(5);
		String object = SerializationUtils.serialize(dice);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesRollNumberCommand.class);
	}
	
	@Test
	public void TestMovesRobPlayer() throws InvalidFactoryParameterException
	{
		String url = "moves/robplayer";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PRobPlayer robber = new PRobPlayer();
		robber.setLocation(new Coordinate(6,8));
		robber.setVictimIndex(2);
		String object = SerializationUtils.serialize(robber);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesRobPlayerCommand.class);
	}
	
	@Test
	public void TestMovesFinishTurn() throws InvalidFactoryParameterException
	{
		String url = "moves/finishturn";
		StringBuilder param = new StringBuilder(url.toUpperCase());		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesFinishTurnCommand.class);
	}
	
	@Test
	public void TestMovesBuyDevCard() throws InvalidFactoryParameterException
	{
		String url = "moves/buydevcard";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesBuyDevCardCommand.class);
	}
	
	@Test
	public void TestMovesYearOfPlenty() throws InvalidFactoryParameterException
	{
		String url = "moves/yearofplenty";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PYearOfPlentyCard plenty = new PYearOfPlentyCard(ResourceType.BRICK, ResourceType.WOOD);
		String object = SerializationUtils.serialize(plenty);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesYearOfPlentyCommand.class);
	}
	
	@Test
	public void TestMovesYear_Of_Plenty() throws InvalidFactoryParameterException
	{
		String url = "moves/year_of_plenty";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PYearOfPlentyCard plenty = new PYearOfPlentyCard(ResourceType.BRICK, ResourceType.WOOD);
		String object = SerializationUtils.serialize(plenty);		
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesYearOfPlentyCommand.class);
	}
	
	@Test
	public void TestMovesRoadBuilding() throws InvalidFactoryParameterException
	{
		String url = "moves/roadbuilding";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PRoadBuildingCard rbcard = new PRoadBuildingCard();
		rbcard.setStart1(new Coordinate(0,0));
		rbcard.setStart2(new Coordinate(5,5));
		rbcard.setEnd1(new Coordinate(1,1));
		rbcard.setEnd2(new Coordinate(6,6));
		String object = SerializationUtils.serialize(rbcard);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesRoadBuildingCommand.class);
	}
	
	@Test
	public void TestMovesRoad_Builder() throws InvalidFactoryParameterException
	{
		String url = "moves/road_building";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PRoadBuildingCard rbcard = new PRoadBuildingCard();
		rbcard.setStart1(new Coordinate(0,0));
		rbcard.setStart2(new Coordinate(5,5));
		rbcard.setEnd1(new Coordinate(1,1));
		rbcard.setEnd2(new Coordinate(6,6));
		String object = SerializationUtils.serialize(rbcard);	
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesRoadBuildingCommand.class);
	}
	
	@Test
	public void TestMovesSoldier() throws InvalidFactoryParameterException
	{
		String url = "moves/soldier";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PSoldierCard soldiercard = new PSoldierCard();
		soldiercard.setLocation(new Coordinate(0,0));
		soldiercard.setVictimIndex(0);
		String object = SerializationUtils.serialize(soldiercard);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesSoldierCommand.class);
	}
	
	@Test
	public void TestMovesMonopoly() throws InvalidFactoryParameterException
	{
		String url = "moves/monopoly";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PMonopolyCard mon = new PMonopolyCard(ResourceType.BRICK);
		String object = SerializationUtils.serialize(mon);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesMonopolyCommand.class);
	}
	
	@Test
	public void TestMovesMonument() throws InvalidFactoryParameterException
	{
		String url = "moves/monument";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesMonumentCommand.class);
	}
	
	@Test
	public void TestMovesBuildRoad() throws InvalidFactoryParameterException
	{
		String url = "moves/buildroad";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PBuildRoad road = new PBuildRoad(new Coordinate(0,0), new Coordinate(1,1), false);
		String object = SerializationUtils.serialize(road);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesBuildRoadCommand.class);
	}
	
	@Test
	public void TestMovesBuildSettlement() throws InvalidFactoryParameterException
	{
		String url = "moves/buildsettlement";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PBuildSettlement settle = new PBuildSettlement(new Coordinate(0,0), true);
		String object = SerializationUtils.serialize(settle);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesBuildSettlementCommand.class);
	}
	
	@Test
	public void TestMovesBuildCity() throws InvalidFactoryParameterException
	{
		String url = "moves/buildcity";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PBuildCity city = new PBuildCity(new Coordinate(0,0));
		String object = SerializationUtils.serialize(city);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesBuildCityCommand.class);
	}
	
	@Test
	public void TestMovesOfferTrade() throws InvalidFactoryParameterException
	{
		String url = "moves/offertrade";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		POfferTrade trade = new POfferTrade();
		trade.setReceiver(0);
		List<Integer> rList = new ArrayList<Integer>();
		rList.add(0);
		rList.add(1);
		rList.add(-1);
		rList.add(0);
		rList.add(0);
		trade.setResourceList(rList);
		String object = SerializationUtils.serialize(trade);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesOfferTradeCommand.class);
	}
	
	@Test
	public void TestMovesAcceptTrade() throws InvalidFactoryParameterException
	{
		String url = "moves/accepttrade";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		
		PAcceptTrade acc = new PAcceptTrade(false);
		String object = SerializationUtils.serialize(acc);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesAcceptTradeCommand.class);
	}
	
	@Test
	public void TestMovesMaritimeTrade() throws InvalidFactoryParameterException
	{
		String url = "moves/maritimetrade";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		PMaritimeTrade trade = new PMaritimeTrade();
		trade.setInputResource(ResourceType.BRICK);
		trade.setOutputResource(ResourceType.ORE);
		trade.setRatio(3);
		String object = SerializationUtils.serialize(trade);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesMaritimeTradeCommand.class);
	}
	
	@Test
	public void TestMovesDiscardCards() throws InvalidFactoryParameterException
	{
		String url = "moves/discardcards";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		PDiscardCards discard = new PDiscardCards();
		List<Integer> rList = new ArrayList<Integer>();
		rList.add(0);
		rList.add(1);
		rList.add(3);
		rList.add(0);
		rList.add(0);
		discard.setResourceList(rList);
		String object = SerializationUtils.serialize(discard);
		
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == MovesDiscardCardsCommand.class);
	}
	
	@Test
	public void TestUtilChangeLogLevel() throws InvalidFactoryParameterException
	{
		String url = "util/changeloglevel";
		StringBuilder param = new StringBuilder(url.toUpperCase());
		ICommand command = factory.GetCommand(param, cookie, object);
		
		assertTrue(command.getClass() == UtilChangeLogLevelCommand.class);
	}
}

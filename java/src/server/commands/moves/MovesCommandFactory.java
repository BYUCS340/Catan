package server.commands.moves;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.CookieBuilder;
import server.commands.Factory;
import server.commands.ICommand;
import server.commands.ICommandBuilder;
import server.commands.ICommandDirector;
import server.commands.InvalidFactoryParameterException;
import server.model.GameException;
import shared.definitions.ResourceType;
import shared.model.map.Coordinate;
import shared.networking.GSONUtils;
import shared.networking.cookie.NetworkCookie;
import shared.networking.parameter.PAcceptTrade;
import shared.networking.parameter.PBuildCity;
import shared.networking.parameter.PBuildRoad;
import shared.networking.parameter.PBuildSettlement;
import shared.networking.parameter.PDiscardCards;
import shared.networking.parameter.PMaritimeTrade;
import shared.networking.parameter.PMonopolyCard;
import shared.networking.parameter.POfferTrade;
import shared.networking.parameter.PRoadBuildingCard;
import shared.networking.parameter.PRobPlayer;
import shared.networking.parameter.PRollDice;
import shared.networking.parameter.PSendChat;
import shared.networking.parameter.PSoldierCard;
import shared.networking.parameter.PYearOfPlentyCard;

/**
 * Creates moves command objects.
 * @author Jonathan Sadler and Parker Ridd
 *
 */
public class MovesCommandFactory extends Factory 
{
	private Map<String, ICommandDirector> directors;
	
	/**
	 * Creates a GamesCommandFactory.
	 */
	public MovesCommandFactory() 
	{
		directors = new HashMap<String, ICommandDirector>(19);
		
		directors.put("ACCEPTTRADE", new AcceptTradeDirector());
		directors.put("BUILDCITY", new BuildCityDirector());
		directors.put("BUILDROAD", new BuildRoadDirector());
		directors.put("BUILDSETTLEMENT", new BuildSettlementDirector());
		directors.put("BUYDEVCARD", new BuyDevCardDirector());
		directors.put("DISCARDCARDS", new DiscardCardsDirector());
		directors.put("FINISHTURN", new FinishTurnDirector());
		directors.put("MARITIMETRADE", new MaritimeTradeDirector());
		directors.put("MONOPOLY", new MonopolyDirector());
		directors.put("MONUMENT", new MonumentDirector());
		directors.put("OFFERTRADE", new OfferTradeDirector());
		directors.put("ROADBUILDING", new RoadBuildingDirector());
		directors.put("ROAD_BUILDING", directors.get("ROADBUILDING"));
		directors.put("ROBPLAYER", new RobPlayerDirector());
		directors.put("ROLLNUMBER", new RollNumberDirector());
		directors.put("SENDCHAT", new SendChatDirector());
		directors.put("SOLDIER", new SoldierDirector());
		directors.put("YEAROFPLENTY", new YearOfPlentyDirector());
		directors.put("YEAR_OF_PLENTY", directors.get("YEAROFPLENTY"));
	}

	@Override
	public ICommand GetCommand(StringBuilder param, NetworkCookie cookie, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("MovesCommandFactory", "GetCommand", e);
			throw e;
		}
		
		try
		{
			CookieBuilder builder = (CookieBuilder)directors.get(key).GetBuilder();
			builder.SetData(object);
			builder.SetCookie(cookie);
			return builder.BuildCommand();
		}
		catch (GameException e)
		{
			InvalidFactoryParameterException e1 = new InvalidFactoryParameterException("Invalid cookie", e);
			Logger.getLogger("CatanServer").throwing("MovesCommandFactory", "GetCommand", e1);
			throw e1;
		}
	}
	
	private class AcceptTradeDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new AcceptTradeBuilder();
		}
	}
	
	private class BuildCityDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new BuildCityBuilder();
		}
	}
	
	private class BuildRoadDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new BuildRoadBuilder();
		}
	}
	
	private class BuildSettlementDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new BuildSettlementBuilder();
		}
	}
	
	private class BuyDevCardDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new BuyDevCardBuilder();
		}
	}
	
	private class DiscardCardsDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new DiscardCardsBuilder();
		}
	}
	
	private class FinishTurnDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new FinishTurnBuilder();
		}
	}
	
	private class MaritimeTradeDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new MaritimeTradeBuilder();
		}
	}
	
	private class MonopolyDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new MonopolyBuilder();
		}
	}
	
	private class MonumentDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new MonumentBuilder();
		}
	}
	
	private class OfferTradeDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new OfferTradeBuilder();
		}
	}
	
	private class RoadBuildingDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder()
		{
			return new RoadBuildingBuilder();
		}
	}
	
	private class RobPlayerDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new RobPlayerBuilder();
		}
	}
	
	private class RollNumberDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new RollNumberBuilder();
		}
	}
	
	private class SendChatDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new SendChatBuilder();
		}
	}
	
	private class SoldierDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder()
		{
			return new SoldierBuilder();
		}
	}
	
	private class YearOfPlentyDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new YearOfPlentyBuilder();
		}
	}
	
	private class AcceptTradeBuilder extends CookieBuilder
	{
		private boolean willAccept;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesAcceptTradeCommand(cookie, playerIndex, willAccept);
		}

		@Override
		public void SetData(String object) 
		{
			PAcceptTrade acctrade = GSONUtils.deserialize(object, PAcceptTrade.class);
			willAccept = acctrade.willAccept();
		}
	}
	
	private class BuildCityBuilder extends CookieBuilder
	{
		private Coordinate point;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesBuildCityCommand(cookie, playerIndex, point);
		}

		@Override
		public void SetData(String object) 
		{
			//get the correct hex location from the input object 
			PBuildCity pbcity = GSONUtils.deserialize(object, PBuildCity.class);
			point = pbcity.getLocation();
		}
	}
	
	private class BuildRoadBuilder extends CookieBuilder
	{
		private Coordinate start;
		private Coordinate end;
		private boolean free;
		
		@Override
		public ICommand BuildCommand()
		{
			return new MovesBuildRoadCommand(cookie, playerIndex, start, end, free);
		}

		@Override
		public void SetData(String object) 
		{
			PBuildRoad broad = GSONUtils.deserialize(object, PBuildRoad.class);
			start = broad.getStart();
			end = broad.getEnd();
			free = broad.isFree();		
		}
	}
	
	private class BuildSettlementBuilder extends CookieBuilder
	{
		private Coordinate point;
		private boolean free;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesBuildSettlementCommand(cookie, playerIndex, point, free);
		}

		@Override
		public void SetData(String object) 
		{
			PBuildSettlement pbsettlement = GSONUtils.deserialize(object, PBuildSettlement.class);
			point = pbsettlement.getLocation();
			free = pbsettlement.isFree();	
		}
	}
	
	private class BuyDevCardBuilder extends CookieBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesBuyDevCardCommand(cookie, playerIndex);
		}

		@Override
		public void SetData(String object) 
		{
			return;
		}
	}
	
	private class DiscardCardsBuilder extends CookieBuilder
	{
		private List<Integer> toDiscard;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesDiscardCardsCommand(cookie, playerIndex, toDiscard);
		}

		@Override
		public void SetData(String object) 
		{
			PDiscardCards pdiscard = GSONUtils.deserialize(object, PDiscardCards.class);
			toDiscard = pdiscard.getResourceList();
		}
	}
	
	private class FinishTurnBuilder extends CookieBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesFinishTurnCommand(cookie, playerIndex);
		}

		@Override
		public void SetData(String object) 
		{
			return;
		}
	}
	
	private class MaritimeTradeBuilder extends CookieBuilder
	{
		private int ratio;
		private ResourceType input;
		private ResourceType output;

		@Override
		public ICommand BuildCommand() 
		{
			return new MovesMaritimeTradeCommand(cookie, playerIndex, ratio, input, output);
		}

		@Override
		public void SetData(String object) 
		{
			PMaritimeTrade pmaritime = GSONUtils.deserialize(object, PMaritimeTrade.class);
			ratio = pmaritime.getRatio();
			input = pmaritime.getInputResource();
			output = pmaritime.getOutputReseource();
		}
	}
	
	private class MonopolyBuilder extends CookieBuilder
	{
		private ResourceType resource;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesMonopolyCommand(cookie, playerIndex, resource);
		}

		@Override
		public void SetData(String object) 
		{
			PMonopolyCard pmonopoly = GSONUtils.deserialize(object, PMonopolyCard.class);
			resource = pmonopoly.getResource();
		}
	}
	
	private class MonumentBuilder extends CookieBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesMonumentCommand(cookie, playerIndex);
		}

		@Override
		public void SetData(String object) 
		{
			return;
		}
	}
	
	private class OfferTradeBuilder extends CookieBuilder
	{
		private int receiverIndex;
		private List<Integer> offer;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesOfferTradeCommand(cookie, playerIndex, receiverIndex, offer);
		}

		@Override
		public void SetData(String object) 
		{
			POfferTrade poffertrade = GSONUtils.deserialize(object, POfferTrade.class);
			receiverIndex = poffertrade.getReceiver();
			offer = poffertrade.getResourceList();		
		}
	}
	
	private class RoadBuildingBuilder extends CookieBuilder
	{
		private Coordinate start1;
		private Coordinate end1;
		private Coordinate start2;
		private Coordinate end2;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesRoadBuildingCommand(cookie, playerIndex, start1, end1, start2, end2);
		}

		@Override
		public void SetData(String object) 
		{
			PRoadBuildingCard prbc = GSONUtils.deserialize(object, PRoadBuildingCard.class);
			start1 = prbc.getStart1();
			start2 = prbc.getStart2();
			end1 = prbc.getEnd1();
			end2 = prbc.getEnd2();	
		}
	}
	
	private class RobPlayerBuilder extends CookieBuilder
	{
		private int victimIndex;
		private Coordinate point;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesRobPlayerCommand(cookie, playerIndex, victimIndex, point);
		}

		@Override
		public void SetData(String object) 
		{
			PRobPlayer prob = GSONUtils.deserialize(object, PRobPlayer.class);
			victimIndex = prob.getVictimIndex();
			point = prob.getLocation();
		}
	}
	
	private class RollNumberBuilder extends CookieBuilder
	{
		private int roll;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesRollNumberCommand(cookie, playerIndex, roll);
		}

		@Override
		public void SetData(String object) 
		{
			PRollDice proll = GSONUtils.deserialize(object, PRollDice.class);
			roll = proll.getRoll();
		}
	}
	
	private class SendChatBuilder extends CookieBuilder
	{
		private String message;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesSendChatCommand(cookie, playerIndex, message);
		}

		@Override
		public void SetData(String object) 
		{
			PSendChat pchat = GSONUtils.deserialize(object, PSendChat.class);
			message = pchat.getContent();	
		}
	}
	
	private class SoldierBuilder extends CookieBuilder
	{
		private int victimIndex;
		private Coordinate point;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesSoldierCommand(cookie, playerIndex, victimIndex, point);
		}

		@Override
		public void SetData(String object) 
		{
			PSoldierCard psoldier = GSONUtils.deserialize(object, PSoldierCard.class);
			victimIndex = psoldier.getVictimIndex();
			point = psoldier.getLocation();		
		}
	}
	
	private class YearOfPlentyBuilder extends CookieBuilder
	{
		private ResourceType resource1;
		private ResourceType resource2;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesYearOfPlentyCommand(cookie, playerIndex, resource1, resource2);
		}

		@Override
		public void SetData(String object)
		{
			PYearOfPlentyCard pyear = GSONUtils.deserialize(object, PYearOfPlentyCard.class);
			resource1 = pyear.getResource1();
			resource2 = pyear.getResource2();
		}
	}
}

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
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.map.Coordinate;
import shared.networking.GSONUtils;
import shared.networking.parameter.PBuildCity;

/**
 * Creates moves command objects.
 * @author Jonathan Sadler
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
	public ICommand GetCommand(StringBuilder param, int playerID, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("MovesCommandFactory", "GetCommand", e);
			throw e;
		}
		
		CookieBuilder builder = (CookieBuilder)directors.get(key).GetBuilder();
		builder.SetData(object);
		builder.SetPlayerData(playerID);
		return builder.BuildCommand();
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
		private int playerIndex;
		private boolean willAccept;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesAcceptTradeCommand(playerID, playerIndex, willAccept);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class BuildCityBuilder extends CookieBuilder
	{
		private int playerIndex;
		private Coordinate point;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesBuildCityCommand(playerID, playerIndex, point);
		}

		@Override
		public void SetData(String object) 
		{
			//get the correct hex location from the input object 
			PBuildCity pbcity = GSONUtils.deserialize(object, PBuildCity.class);
			HexLocation temploc = pbcity.getVertexLocation().getNormalizedLocation().getHexLoc();
			point = new Coordinate(temploc.getX(), temploc.getY());
			
		}
	}
	
	private class BuildRoadBuilder extends CookieBuilder
	{
		private int playerIndex;
		private Coordinate start;
		private Coordinate end;
		private boolean free;
		
		@Override
		public ICommand BuildCommand()
		{
			return new MovesBuildRoadCommand(playerID, playerIndex, start, end, free);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class BuildSettlementBuilder extends CookieBuilder
	{
		private int playerIndex;
		private Coordinate point;
		private boolean free;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesBuildSettlementCommand(playerID, playerIndex, point, free);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class BuyDevCardBuilder extends CookieBuilder
	{
		private int playerIndex;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesBuyDevCardCommand(playerID, playerIndex);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
		}
	}
	
	private class DiscardCardsBuilder extends CookieBuilder
	{
		private int playerIndex;
		private List<Integer> toDiscard;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesDiscardCardsCommand(playerID, playerIndex, toDiscard);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class FinishTurnBuilder extends CookieBuilder
	{
		private int playerIndex;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesFinishTurnCommand(playerID, playerIndex);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
		}
	}
	
	private class MaritimeTradeBuilder extends CookieBuilder
	{
		private int playerIndex;
		private int ratio;
		private String input;
		private String output;

		@Override
		public ICommand BuildCommand() 
		{
			return new MovesMaritimeTradeCommand(playerID, playerIndex, ratio, input, output);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class MonopolyBuilder extends CookieBuilder
	{
		private int playerIndex;
		private ResourceType resource;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesMonopolyCommand(playerID, playerIndex, resource);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class MonumentBuilder extends CookieBuilder
	{
		private int playerIndex;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesMonumentCommand(playerID, playerIndex);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
		}
	}
	
	private class OfferTradeBuilder extends CookieBuilder
	{
		private int playerIndex;
		private int receiverIndex;
		private List<Integer> offer;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesOfferTradeCommand(playerID, playerIndex, receiverIndex, offer);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class RoadBuildingBuilder extends CookieBuilder
	{
		private int playerIndex;
		private Coordinate start1;
		private Coordinate end1;
		private Coordinate start2;
		private Coordinate end2;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesRoadBuildingCommand(playerID, playerIndex, start1, end1, start2, end2);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class RobPlayerBuilder extends CookieBuilder
	{
		private int playerIndex;
		private int victimIndex;
		private Coordinate point;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesRobPlayerCommand(playerID, playerIndex, victimIndex, point);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class RollNumberBuilder extends CookieBuilder
	{
		private int playerIndex;
		private int roll;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesRollNumberCommand(playerID, playerIndex, roll);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class SendChatBuilder extends CookieBuilder
	{
		private int playerIndex;
		private String message;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesSendChatCommand(playerID, playerIndex, message);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class SoldierBuilder extends CookieBuilder
	{
		private int playerIndex;
		private int victimIndex;
		private Coordinate point;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesSoldierCommand(playerID, playerIndex, victimIndex, point);
		}

		@Override
		public void SetData(String object) 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	private class YearOfPlentyBuilder extends CookieBuilder
	{
		private int playerIndex;
		private ResourceType resource1;
		private ResourceType resource2;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new MovesYearOfPlentyCommand(playerID, playerIndex, resource1, resource2);
		}

		@Override
		public void SetData(String object)
		{
			// TODO Auto-generated method stub
			
		}
	}
}

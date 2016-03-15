package server.commands.user;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.*;
import shared.networking.GSONUtils;
import shared.networking.parameter.PCredentials;

/**
 * Creates user command objects.
 * @author Jonathan Sadler
 *
 */
public class UserCommandFactory extends Factory 
{
	private Map<String, ICommandDirector> directors;
	
	/**
	 * Creates a UserCommandFactory.
	 */
	public UserCommandFactory() 
	{
		directors = new HashMap<String, ICommandDirector>(2);
		
		directors.put("LOGIN", new LoginDirector());
		directors.put("REGISTER", new RegisterDirector());
	}
	
	@Override
	public ICommand GetCommand(StringBuilder param, int playerID, int gameID, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!directors.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("UserCommandFactory", "GetCommand", e);
			throw e;
		}
		
		ICommandBuilder builder = directors.get(key).GetBuilder();
		builder.SetData(object);
		return builder.BuildCommand();
	}
	
	private class LoginDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder()
		{
			return new LoginBuilder();
		}
	}
	
	private class RegisterDirector implements ICommandDirector
	{
		@Override
		public ICommandBuilder GetBuilder() 
		{
			return new RegisterBuilder();
		}	
	}

	private class LoginBuilder implements ICommandBuilder
	{
		private String username = null;
		private String password = null;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new UserLoginCommand(username, password);
		}

		@Override
		public void SetData(String object)
		{
			PCredentials creds = GSONUtils.deserialize(object, PCredentials.class);
			username = creds.getUsername();
			password = creds.getPassword();
		}
	}
	
	private class RegisterBuilder implements ICommandBuilder
	{
		private String username;
		private String password;
		
		@Override
		public ICommand BuildCommand() 
		{
			return new UserRegisterCommand(username, password);
		}

		@Override
		public void SetData(String object) 
		{
			PCredentials creds = GSONUtils.deserialize(object, PCredentials.class);
			username = creds.getUsername();
			password = creds.getPassword();
		}
	}
}

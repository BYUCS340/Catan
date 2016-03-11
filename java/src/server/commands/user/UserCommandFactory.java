package server.commands.user;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import server.commands.*;

/**
 * Creates user command objects.
 * @author Jonathan Sadler
 *
 */
public class UserCommandFactory extends Factory 
{
	private Map<String, ICommandBuilder> builders;
	
	/**
	 * Creates a UserCommandFactory.
	 */
	public UserCommandFactory() 
	{
		builders = new HashMap<String, ICommandBuilder>(2);
		
		builders.put("login", new LoginBuilder());
		builders.put("register", new RegisterBuilder());
	}
	
	@Override
	public ICommand GetCommand(StringBuilder param, int playerID, String object) throws InvalidFactoryParameterException 
	{
		String key = PopToken(param);
		
		if (!builders.containsKey(key))
		{
			InvalidFactoryParameterException e = new InvalidFactoryParameterException("Key doesn't exist: " + key);
			Logger.getLogger("CatanServer").throwing("UserCommandFactory", "GetCommand", e);
			throw e;
		}
		
		return builders.get(key).BuildCommand();
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
			//TODO Get username and password from serialized object
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
			//TODO Get username and password from serialized object
		}
	}
}

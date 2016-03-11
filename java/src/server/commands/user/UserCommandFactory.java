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
	public ICommand GetCommand(StringBuilder param, String object) throws InvalidFactoryParameterException 
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
		@Override
		public ICommand BuildCommand() 
		{
			return new LoginCommand();
		}

		@Override
		public void SetData(String object)
		{
			//Deserialize object from string
		}
	}
	
	private class RegisterBuilder implements ICommandBuilder
	{
		@Override
		public ICommand BuildCommand() 
		{
			return new RegisterCommand();
		}

		@Override
		public void SetData(String object) 
		{
			//Deserialize object from string
		}
	}
}

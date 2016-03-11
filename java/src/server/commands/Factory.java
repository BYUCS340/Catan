package server.commands;

/**
 * Abstract class used by factories.
 * @author Jonathan Sadler
 *
 */
public abstract class Factory 
{
	/**
	 * Gets the necessary command from the provided data.
	 * @param param The file path from the URL provided to the server.
	 * @param object The serialized object.
	 * @return The associated command object.
	 * @throws InvalidFactoryParameterException Thrown if there isn't a command associated with param.
	 */
	public abstract ICommand GetCommand(StringBuilder param, String object) throws InvalidFactoryParameterException;
	
	protected String PopToken(StringBuilder param)
	{
		int index = param.indexOf("/");
		
		String token = null;
		if (index != -1)
		{
			token = param.substring(0, index);
			param = param.delete(0, index + 1);
		}
		else
		{
			token = param.toString();
			param = param.delete(0, param.length());
		}
		
		return token;
	}
}

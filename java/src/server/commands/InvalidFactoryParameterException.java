package server.commands;

@SuppressWarnings("serial")
public class InvalidFactoryParameterException extends Exception 
{
	public InvalidFactoryParameterException() { }

	public InvalidFactoryParameterException(String arg0)
	{
		super(arg0);
	}

	public InvalidFactoryParameterException(Throwable arg0) 
	{
		super(arg0);
	}

	public InvalidFactoryParameterException(String arg0, Throwable arg1) 
	{
		super(arg0, arg1);
	}

	public InvalidFactoryParameterException(String arg0, Throwable arg1, boolean arg2, boolean arg3) 
	{
		super(arg0, arg1, arg2, arg3);
	}

}

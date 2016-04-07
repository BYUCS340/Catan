package server.persistence;

public class PersistenceException extends Exception 
{
	private static final long serialVersionUID = -5302159979885185960L;

	public PersistenceException()
	{
		super();
	}
	
	public PersistenceException(String string) 
	{
		super(string);
	}
	
	public PersistenceException(String string, Throwable throwable)
	{
		super(string, throwable);
	}
}

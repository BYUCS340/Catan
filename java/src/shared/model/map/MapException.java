package shared.model.map;

@SuppressWarnings("serial")
public class MapException extends Exception {

	public MapException()
	{
		super();
	}
	
	public MapException(String message)
	{
		super(message);
	}
	
	public MapException(String message, Exception ex)
	{
		super(message, ex);
	}
}

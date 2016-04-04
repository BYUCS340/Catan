package server.persistence;

/**
 * This class is used to load the plugin. Like a registry
 * @author matthewcarlson
 *
 */
public class PersistenceHandler
{
	private String DAOType;
	
	public PersistenceHandler(String type)
	{
		DAOType = type;
	}
	
	/**
	 * Returns whether this persistence handler has a valid plugin it can load
	 * @return
	 */
	public boolean IsValidPlugin()
	{
		return false;
	}
	
	/**
	 * Returns the plugin
	 * @return
	 */
	public IPersistenceProvider GetPlugin()
	{
		return null;
	}
	
	public String GetType()
	{
		return DAOType;
	}
}

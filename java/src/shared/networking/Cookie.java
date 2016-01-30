package shared.networking;

public interface Cookie 
{
	
	/**
	 * Gets the original cookie text needed to communicate to 
	 * the server
	 * @return The cookie text
	 */
	public String getCookieText();

}

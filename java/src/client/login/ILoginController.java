package client.login;

import client.base.*;

/**
 * Interface for the login controller
 */
public interface ILoginController extends IController
{
	
	/**
	 * Displays the login view
	 */
	void start();
	
	/**
	 * Called when the user clicks the "Sign in" button in the login view
	 */
	void signIn();
	
	/**
	 * Called when the user clicks the "Register" button in the login view
	 */
	void register();
	
}


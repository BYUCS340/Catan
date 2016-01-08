package client.login;

import client.base.*;

/**
 * Interface for the login view, which lets the user create a new account and
 * login
 */
public interface ILoginView extends IOverlayView
{
	
	/**
	 * Returns the value of the login username field
	 * 
	 * @return The value of the login username field
	 */
	String getLoginUsername();
	
	/**
	 * Returns the value of the login password field
	 * 
	 * @return The value of the login password field
	 */
	String getLoginPassword();
	
	/**
	 * Returns the value of the register username field
	 * 
	 * @return The value of the register username field
	 */
	String getRegisterUsername();
	
	/**
	 * Returns the value of the register password field
	 * 
	 * @return The value of the register password field
	 */
	String getRegisterPassword();
	
	/**
	 * Returns the value of the register password repeat field
	 * 
	 * @return The value of the register password repeat field
	 */
	String getRegisterPasswordRepeat();
	
}


package client.login;

import client.base.*;
import client.misc.*;
import client.model.ClientGame;
import client.networking.ServerProxyException;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn()
	{	
		// TODO: log in user
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();
		
		try 
		{
			if (!ClientGame.getCurrentProxy().loginUser(username, password))
			{
				this.showMessage("User/Password not found");
				return;
			}
		}
		catch (ServerProxyException e) 
		{
			e.printStackTrace();
			this.showMessage("Unable to connect to server");
			return;
		}

		// If log in succeeded
		getLoginView().closeModal();
		loginAction.execute();
	}
	
	/**
	 * Shows a message via the message view modal
	 * @param mess
	 */
	private void showMessage(String mess)
	{
		System.err.println(mess);
		getMessageView().setMessage(mess);
		getMessageView().showModal();
	}

	@Override
	public void register() {
		
		// TODO: register new user (which, if successful, also logs them in)
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String password2 = getLoginView().getRegisterPasswordRepeat();
		
		//check to make sure the passwords patch
		if (!password.equals(password2))
		{
			this.showMessage("Register User Passwords do not match");
			return;
		}
		
		//check to make sure there is an actual password
		if (password.equals("") || password.length() < 5)
		{
			this.showMessage("Bad Password");
			return;
		}
		
		//check to make there is a real username
		if (username.equals("") || username.length() < 3 || username.length() > 7)
		{
			this.showMessage("Bad Username");
			return;
		}
		
		try 
		{
			if (!ClientGame.getCurrentProxy().registerUser(username, password))
			{
				this.showMessage("Unable Register User");
				return;
			}
			if (!ClientGame.getCurrentProxy().loginUser(username, password))
			{
				this.showMessage("Unable Register User");
				return;
			}
		} 
		catch (ServerProxyException e) 
		{
			this.showMessage("Unable to connect to server");
			e.printStackTrace();
			return;
		}
		
		// If register succeeded
		getLoginView().closeModal();
		loginAction.execute();
	}

}


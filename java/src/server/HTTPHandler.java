package server;

import java.io.IOException;
import java.util.logging.Logger;

import com.sun.net.httpserver.*;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.commands.InvalidFactoryParameterException;

/**
 * Handles HTTP requests.
 * @author Jonathan Sadler
 *
 */
public class HTTPHandler implements HttpHandler
{
	private static Logger logger = Logger.getLogger("CatanServer");
	
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		StringBuilder uri = new StringBuilder(exchange.getRequestURI().toString());
		uri.delete(0, 1);
		
		logger.finest("Handling: " + uri);
		
		try 
		{
			ICommand command = CommandFactory.GetCommandFactory().GetCommand(uri, null);
			command.Execute();
		}
		catch (InvalidFactoryParameterException e) 
		{
			logger.severe("Unable to find needed key");
		} 
	}

}

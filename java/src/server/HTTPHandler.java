package server;

import java.io.*;
import java.net.HttpURLConnection;
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
		
		String line = null;
		StringBuilder object = new StringBuilder();
		while ((line = reader.readLine()) != null)
		    object.append(line + "\n");
		
		uri.delete(0, 1);
		
		logger.finest("Handling: " + uri);
		
		int playerID = -1; //TODO We need to get this from the cookie;
		
		try 
		{
			ICommand command = CommandFactory.GetCommandFactory().GetCommand(uri, playerID, object.toString());
			
			if (command.Execute())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				OutputStream oStream = exchange.getResponseBody();
				OutputStreamWriter writer = new OutputStreamWriter(oStream, "UTF-8");
				writer.write(command.Response());
				writer.close();
				exchange.getResponseBody().close();
			}
			else
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			}
		}
		catch (InvalidFactoryParameterException e) 
		{
			logger.severe("Unable to find needed key");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
		} 
	}

}

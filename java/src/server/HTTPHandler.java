package server;

import java.io.*;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.*;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.commands.InvalidFactoryParameterException;
import server.swagger.SwaggerHandlers;

/**
 * Handles HTTP requests.
 * @author Jonathan Sadler
 *
 */
public class HTTPHandler implements HttpHandler
{	
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		String request = exchange.getRequestURI().toString();
		
		//If the request contains a period or is less than 1, then it is for swagger and should
		//be redirected there.
		if (request.lastIndexOf('.') != -1 || request.length() <= 1)
		{
			SwaggerHandlers.BasicFile swagger = new SwaggerHandlers.BasicFile();
			swagger.handleRedirect(exchange);
			return;
		}
		
		StringBuilder uri = new StringBuilder(request.toUpperCase());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
		
		String line = null;
		StringBuilder object = new StringBuilder();
		while ((line = reader.readLine()) != null)
		    object.append(line + "\n");
		
		uri.delete(0, 1);
		
		Log.GetLog().finest("Handling: " + uri);
		
		int playerID = -1; //TODO We need to get this from the cookie;
		
		try 
		{
			ICommand command = CommandFactory.GetCommandFactory().GetCommand(uri, playerID, object.toString());
			
			if (command.Execute())
			{
				//Set the content type to be JSON
				Headers responseHeaders = exchange.getResponseHeaders();
				responseHeaders.set("Content-Type", "application/json");
				
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				OutputStream oStream = exchange.getResponseBody();
				OutputStreamWriter writer = new OutputStreamWriter(oStream, "UTF-8");
				writer.write(command.Response());
				writer.close();
				exchange.getResponseBody().close();
			}
			else
			{
				Log.GetLog().warning("Bad request received");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			}
		}
		catch (InvalidFactoryParameterException e) 
		{
			Log.GetLog().severe("Unable to find needed key");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
		} 
	}

}

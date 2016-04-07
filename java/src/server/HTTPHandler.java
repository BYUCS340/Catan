package server;

import java.io.*;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.*;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.commands.InvalidFactoryParameterException;
import server.commands.games.GamesCreateCommand;
import server.commands.moves.MovesCommand;
import server.commands.user.UserRegisterCommand;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import server.persistence.PersistenceException;
import server.persistence.PersistenceFacade;
import server.swagger.SwaggerHandlers;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

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
		
		//Read in data from request body
		BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
		
		String line = null;
		StringBuilder object = new StringBuilder();
		while ((line = reader.readLine()) != null)
		    object.append(line + "\n");
		
		//Get request type
		StringBuilder uri = new StringBuilder(request.toUpperCase());
		uri.delete(0, 1);
		Log.GetLog().finest("Handling: " + uri);
		
		//Handle user cookie
		NetworkCookie cookie = null;
		Headers headers = exchange.getRequestHeaders();
		if (headers.containsKey("Cookie"))
		{
			try
			{
				String jsonCookie = headers.get("Cookie").get(0);
				cookie = SerializationUtils.deserialize(jsonCookie, NetworkCookie.class);
			}
			catch (Exception e)
			{
				SendResponse(exchange, HttpURLConnection.HTTP_NOT_ACCEPTABLE, "Invalid cookie. Try resetting cookie in browser.");
			}
		}
		
		try 
		{
			ICommand command = CommandFactory.GetCommandFactory().GetCommand(uri, cookie, object.toString());
			
			if (command.Execute())
			{
				HandlePersistence(command);
				
				String response = command.GetResponse();
				String cookieHeader = command.GetHeader();
				
				if (response == null)
					response = "";
				
				//Content-Type is need for Swagger. It gets mad otherwise.
				Headers responseHeaders = exchange.getResponseHeaders();
				if (response.startsWith("{"))
					responseHeaders.set("Content-Type", "application/json");
				else
					responseHeaders.set("Content-Type", "text/html");
				
				if (cookieHeader != null)
				{
					cookieHeader = cookieHeader + ";Path=/;";
					responseHeaders.set("Set-cookie", cookieHeader);
				}
				
				SendResponse(exchange, HttpURLConnection.HTTP_OK, response);
			}
			else
			{
				Log.GetLog().warning("Bad request received");
				String response = command.GetResponse();
				
				SendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, response);
			}
		}
		catch (InvalidFactoryParameterException e) 
		{
			Log.GetLog().severe("Unable to find needed key");
			SendResponse(exchange, HttpURLConnection.HTTP_BAD_METHOD, "Invalid key");
		} 
	}

	private void SendResponse(HttpExchange exchange, int responseCode, String responseMessage) throws IOException
	{
		exchange.sendResponseHeaders(responseCode, 0);
		OutputStream oStream = exchange.getResponseBody();
		oStream.write(responseMessage.getBytes());
		oStream.close();
	}
	
	private void HandlePersistence(ICommand command)
	{
		PersistenceFacade facade = PersistenceFacade.GetPersistence();
		
		try
		{
			if (command.getClass() == UserRegisterCommand.class)
			{
				UserRegisterCommand user = (UserRegisterCommand)command;
				facade.AddUser(user.GetPlayer());
			}
			else if (command.getClass() == GamesCreateCommand.class)
			{
				GamesCreateCommand game = (GamesCreateCommand)command;
				facade.AddGame(game.GetGame());
			}
			else if (command.getClass() == MovesCommand.class)
			{
				MovesCommand move = (MovesCommand)command;
				int gameID = move.GetGameID();
				
				if (!facade.AddCommand(gameID, command))
				{
					ServerGameManager sgm = GameArcade.games().GetGame(gameID);
					facade.UpdateGame(sgm);
				}
			}
		}
		catch (PersistenceException | GameException e)
		{
			Log.GetLog().throwing("HTTPHandler", "HandlePersistence", e);
		}
	}
}

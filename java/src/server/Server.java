package server;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpServer;

import server.ai.AIHandler;
import server.commands.ICommand;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import server.model.ServerPlayer;
import server.persistence.PersistenceException;
import server.persistence.PersistenceFacade;
import server.swagger.SwaggerHandlers;

/**
 * Awesome server. Oh yeah.
 * @author Jonathan Sadler
 *
 */
public class Server 
{
	private static final int DEFAULT_PORT = 8081;
	private static final int MAX_WAITING = 20;
	
	private static String SwaggerPath = "";
	
	/**
	 * Starts the server.
	 * @param args
	 * <br/>Command line arguments can be the following:
	 * <br/>[plug-in][command limit][port];
	 * <br/>[plug-in][command limit][swagger path (path to swagger documentation)];
	 * <br/>[plug-in][command limit][port][swagger path]
	 */
	public static void main(final String[] args) 
	{
		int port = DEFAULT_PORT;
		int commands = 10;
		String plugin = ""; 
		
		
		if (args.length >= 1)
			plugin = args[0];
		
		if (args.length >= 2)
			commands = Integer.parseInt(args[1]);
		
		
		
		if (args.length >= 3)
		{
			int tempPort = TrySettingPort(args[2]);
			
			if (tempPort == -1)
			{
				TrySettingSwaggerPath(args[2]);
			}
			else
			{
				port = tempPort;
				
				if (args.length == 4)
					TrySettingSwaggerPath(args[3]);
			}
		}
		
		new Server().Initialize(port, plugin, commands);
	}
	
	private static int TrySettingPort(String value)
	{
		try
		{
			return Integer.parseInt(value);
		}
		catch (NumberFormatException ex) 
		{ 
			return -1;
		}
	}
	
	private static void TrySettingSwaggerPath(String value)
	{
		File file = new File(value);
		
		if (file.isDirectory())
			SwaggerPath = file.getPath() + "\\";
	}
	
	
	private void Initialize(int port, String plugin, int commandLimit)
	{	
		try 
		{
			Level defaultLevel = Level.FINE;
			
			Log.GetLog().log(defaultLevel, "Starting server");
			HttpServer server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING);
			
			try 
			{
				PersistenceFacade.Initialize(plugin, commandLimit);
				TryInitializingData();
			} 
			catch (PersistenceException e) 
			{
				e.printStackTrace();
			}
			
			AIHandler.GetHandler().EnableAIHandling(true);
			
			server.createContext("/", new HTTPHandler());
			server.createContext("/docs/api/data", new SwaggerHandlers.JSONAppender());
			server.createContext("/docs/api/view", new SwaggerHandlers.BasicFile());
			SwaggerHandlers.SetRootPath(SwaggerPath);
			
			
			//actually throw exceptions instead of silently dying
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
			{
				@Override
				public void uncaughtException(Thread arg0, Throwable arg1)
				{
					Log.GetLog().log(Level.SEVERE, arg1.getMessage(), arg1);
					arg1.printStackTrace();
				}
				
			});
			
			server.start();
			
			Log.GetLog().log(defaultLevel, "Server created successfully");
			Log.GetLog().log(defaultLevel, "IP: " + InetAddress.getLocalHost().getHostAddress());
			Log.GetLog().log(defaultLevel, "Port: " + port);
		}
		catch (IOException | GameException e) 
		{
			Log.GetLog().log(Level.SEVERE, e.getMessage(), e);
			return;
		}
	}
	
	private void TryInitializingData() throws PersistenceException, GameException
	{
		PersistenceFacade facade = PersistenceFacade.GetPersistence();
		
		List<ServerPlayer> players = facade.GetAllUsers();
		for (ServerPlayer player : players)
		{
			try
			{
				Log.GetLog().finest("Reloading player:" +player);
				GameArcade.games().RegisterPlayer(player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
			
		
		List<ServerGameManager> games = facade.GetAllGames();
		for (ServerGameManager game : games){
			try
			{
				Log.GetLog().finest("Reloading game:" +game.GetGameTitle());
				GameArcade.games().CreateGame(game, false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
			
		
		List<ICommand> commands = facade.GetAllCommands();
		for (ICommand command : commands)
			command.Execute();
	}
}

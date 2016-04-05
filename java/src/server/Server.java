package server;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpServer;

import server.persistence.PersistenceException;
import server.persistence.PersistenceHandler;
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
	 * <br/>[port];
	 * <br/>[swagger path (path to swagger documentation)];
	 * <br/>[port] [swagger path]
	 */
	public static void main(final String[] args) 
	{
		int port = DEFAULT_PORT;
		
		if (args.length >= 1)
		{
			int tempPort = TrySettingPort(args[0]);
			
			if (tempPort == -1)
			{
				TrySettingSwaggerPath(args[0]);
			}
			else
			{
				port = tempPort;
				
				if (args.length == 2)
					TrySettingSwaggerPath(args[1]);
			}
		}
		
		new Server().Initialize(port);
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
	
	private void Initialize(int port)
	{	
		try 
		{
			
			PersistenceHandler ph = new PersistenceHandler("SQL");
			
			try {
				ph.GetPlugin().Clear();
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Level defaultLevel = Level.FINE;
			
			Log.GetLog().log(defaultLevel, "Starting server");
			HttpServer server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING);
			
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
		catch (IOException e) 
		{
			Log.GetLog().log(Level.SEVERE, e.getMessage(), e);
			return;
		}
	}
}

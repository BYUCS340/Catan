package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

import com.sun.net.httpserver.*;

public class Server 
{
	private static final int DEFAULT_PORT = 8081;
	private static final int MAX_WAITING = 20;
	
	private static final Level DEFAULT_CONSOLE_LEVEL = Level.FINEST;
	private static final String DEFAULT_LOG_FILE = "CatanServer.log";
	
	private Logger logger;
	
	public static void main(final String[] args) 
	{
		if (args.length == 1)
		{
			try
			{
				int port = Integer.parseInt(args[0]);
				new Server().Initialize(port);
			}
			catch (NumberFormatException ex)
			{
				System.out.println("Unable to get port from input argument");
			}
		}
		else
		{
			new Server().Initialize(DEFAULT_PORT);
		}
	}
	
	private void Initialize(int port)
	{
		InitializeLogger();
		
		try 
		{
			Level defaultLevel = Level.FINE;
			
			logger.log(defaultLevel, "Starting server");
			HttpServer server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING);
			
			server.createContext("/", new HTTPHandler());
			
			server.start();
			
			logger.log(defaultLevel, "Server created successfully");
			logger.log(defaultLevel, "IP: " + InetAddress.getLocalHost().getHostAddress());
			logger.log(defaultLevel, "Port: " + port);
		}
		catch (IOException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
	}
	
	private void InitializeLogger()
	{
		logger = Logger.getLogger("CatanServer");
		logger.setLevel(Level.FINEST);
		logger.setUseParentHandlers(false);
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(DEFAULT_CONSOLE_LEVEL);
		consoleHandler.setFormatter(new logFormatter());
		logger.addHandler(consoleHandler);
		
		try
		{
			FileHandler fileHandler = new FileHandler(DEFAULT_LOG_FILE, false);
			fileHandler.setLevel(Level.FINEST);
			fileHandler.setFormatter(new logFormatter());
			logger.addHandler(fileHandler);
		}
		catch (IOException ex)
		{
			logger.warning("Unable to attach file handler");
		}
		
		logger.log(Level.FINE, "Logger initialized");
	}
	
	private static class logFormatter extends Formatter
	{
		@Override
		public String format(LogRecord record)
		{
			Date date = new Date(record.getMillis());
			DateFormat formatter = new SimpleDateFormat("hh:mm:ss.SSS");
			String dateFormatted = formatter.format(date);
			
			if (record.getThrown() == null)
			{
				return dateFormatted + "-" + record.getMessage() + "\n";
			}
			else
			{
				return dateFormatted + "-EXCEPTION!\n--Class=" + record.getSourceClassName() 
					+ "\n--Method=" + record.getSourceMethodName() + "\n--Message=" + record.getThrown().getMessage() + "\n";
			}
		}
	}
}

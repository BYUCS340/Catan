package server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.swagger.SwaggerHandlers;

public class Server 
{
	private static final int DEFAULT_PORT = 8081;
	private static final int MAX_WAITING = 20;
	
	private static final Level DEFAULT_CONSOLE_LEVEL = Level.FINEST;
	private static final String DEFAULT_LOG_FILE = "CatanServer.log";
	
	private static String SwaggerPath = "";
	
	private Logger logger;
	
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
		InitializeLogger();
		
		try 
		{
			Level defaultLevel = Level.FINE;
			
			logger.log(defaultLevel, "Starting server");
			HttpServer server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING);
			
			server.createContext("/", new HTTPHandler());
			server.createContext("/docs/api/data", new SwaggerHandlers.JSONAppender());
			server.createContext("/docs/api/view", new SwaggerHandlers.BasicFile());
			SwaggerHandlers.SetRootPath(SwaggerPath);
			
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

package server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Handles logging for the server.
 * @author Jonathan Sadler
 *
 */
public class Log 
{
	private static final Level DEFAULT_CONSOLE_LEVEL = Level.FINEST;
	private static final String DEFAULT_LOG_FILE = "CatanServer.log";
	private static final String DEFAULT_LOG = "CatanServer";
	
	private static Logger logger = null;
	
	/**
	 * Gets the logger for the server.
	 * @return The logger.
	 */
	public static Logger GetLog()
	{
		if (logger == null)
			InitializeLogger();
		
		return logger;
	}
	
	static boolean inFirePlace = false;
	private static void putInFirePlace()
	{
		inFirePlace = true;
	}
	
	private static void litOnFire()
	{
		if (!inFirePlace)
		{
			logger = null;
			logger.fine("BURNINATE");
		}
	}
	
	private static void InitializeLogger()
	{
		logger = Logger.getLogger(DEFAULT_LOG);
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

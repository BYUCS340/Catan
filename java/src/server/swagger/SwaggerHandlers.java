package server.swagger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SwaggerHandlers
{
	private final static String DEFAULT_PAGE = "index.html";
	private final static String DEFAULT_PATH = "docs/api/view/";
	private static Logger LOGGER = Logger.getLogger("CatanServer");
	private static String rootPath = "";
	
	public static void SetRootPath(String path)
	{
		rootPath = path.replace('\\', '/');
	}
	
	private abstract static class BaseFile implements HttpHandler
	{	
		protected BaseFile() { }
		
		protected String getRequestPath(HttpExchange exchange)
		{
			return exchange.getRequestURI().getPath().substring(1);
		}
		
		protected void sendFile(HttpExchange exchange, String filepath) throws IOException
		{
			try
			{
				filepath = SwaggerUtils.CleanPath(filepath);
				
				LOGGER.log(Level.FINEST, "Requesting " + filepath);
				byte[] response = SwaggerUtils.ReadFile(filepath);
				
				ArrayList<String> mimetypes = new ArrayList<String>();
				mimetypes.add(SwaggerUtils.GetMimeType(filepath));
				exchange.getResponseHeaders().put("Content-type", mimetypes);
				
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
				OutputStream oStream = exchange.getResponseBody();
				oStream.write(response);
				oStream.close();
			}
			catch(IOException e)
			{
				LOGGER.log(Level.SEVERE, "Failed to retrieve " + filepath);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
			}
		}
	}
	
	public static class BasicFile extends BaseFile
	{
		public BasicFile() 
		{
			super();
		}
		
		public void handle(HttpExchange exchange) throws IOException
		{
			String request = this.getRequestPath(exchange);
			String filepath = rootPath + request;
			this.sendFile(exchange, filepath);
		}
		
		public void handleRedirect(HttpExchange exchange) throws IOException
		{
			String request = this.getRequestPath(exchange);
			
			if (request.length() == 0)
				request = DEFAULT_PAGE;
			
			String filepath = rootPath + DEFAULT_PATH + request;
			this.sendFile(exchange, filepath);
		}
	}
	
	public static class JSONAppender extends BaseFile
	{
		public JSONAppender() 
		{
			super();
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException 
		{
			String filepath = rootPath + this.getRequestPath(exchange) + ".json";
			this.sendFile(exchange, filepath);
		}
	}
}
package server.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import server.Log;

/**
 * This class is used to load the plugin. Like a registry
 * @author matthewcarlson
 *
 */
public class PersistenceHandler
{
	private String DAOType;
	private Class<IPersistenceProvider> provider = null;
	 
	public PersistenceHandler(String type)
	{
		DAOType = type.toLowerCase().trim();
		if (DAOType != null && DAOType.length() >= 3)
		{
			//Get the path to the JAR
			String pathToJar = "plugins"+File.separator+DAOType+"-plugin.jar";
			//Load the jar
			LoadJar(pathToJar);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void LoadJar(String pathToJar)
	{
		try 
		{
			//get the file
			File file  = new File(pathToJar);
			URL url = file.toURI().toURL();  
			URL[] urls = new URL[]{url};
			//figure out the main class in the jar
			String className = this.getPluginClass(pathToJar);
			if (className == null)
			{
				throw new ClassNotFoundException("Bad Class");
			}
			//get the loader from the jar
			URLClassLoader loader = URLClassLoader.newInstance(urls);
			
			//load the class
			Class<?> clazz = loader.loadClass(className);
			
			//check to make sure we can assign it to provider
			if (IPersistenceProvider.class.isAssignableFrom(clazz))
			{
				provider = (Class<IPersistenceProvider>) clazz;
			}
			else
			{
				Log.GetLog().severe("Unable to Load: "+clazz.getName());
				
			}
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			Log.GetLog().severe("Unable to Load: "+DAOType);
		} 
		catch (MalformedURLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * Gets the class in a jar that ends in Plugin.class
	 * @param jarName
	 * @return
	 */
	private String getPluginClass(String jarName) 
	{
		JarInputStream jarFile = null;
		String pluginClass = null;
	    try 
	    {
	        jarFile = new JarInputStream(new FileInputStream(
	                jarName));
	        JarEntry jarEntry  = jarFile.getNextJarEntry();

	        while (jarEntry != null) 
	        {
	        	
	            if (jarEntry.getName().endsWith("Plugin.class"))
	            {
	            	pluginClass = jarEntry.getName().replaceAll("/", "\\.");
	            	pluginClass = pluginClass.substring(0, pluginClass.length()-6);
	            }
	            jarEntry = jarFile.getNextJarEntry();
	            
	        }
	        
	        jarFile.close();
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return pluginClass;
	}
	
	/**
	 * Returns whether this persistence handler has a valid plugin it can load
	 * @return
	 */
	public boolean IsValidPlugin()
	{
		return (provider != null);
	}
	
	/**
	 * Returns the plugin
	 * @return
	 * @throws PersistenceException 
	 */
	public IPersistenceProvider GetPlugin() throws PersistenceException
	{
		if (provider == null)
			throw new PersistenceException("No plugin");
		try 
		{
			return provider.newInstance();
		} 
		catch (InstantiationException | IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistenceException("Plugin was unable to create instance");
		}
		
	}
	
	/**
	 * Returns the type of the plugin
	 * @return sql or file
	 */
	public String GetType()
	{
		return DAOType;
	}
}

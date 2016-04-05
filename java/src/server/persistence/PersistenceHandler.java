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
		DAOType = type;
		String pathToJar = "plugins"+File.separator+"file-plugin.jar";
		
		try 
		{
			File file  = new File(pathToJar);
			
			URL url = file.toURI().toURL();  
			//URL[] urls = new URL[]{url};
			URL jarURL = new URL("jar:file://" + pathToJar + "!/");
			URL[] urls = new URL[]{jarURL,url};
			String className = this.getPluginClass(pathToJar);
			System.out.println("PLUGIN: "+className);
			URLClassLoader loader = URLClassLoader.newInstance(urls,System.class.getClassLoader());
			Class<?> clazz;
			
			String classPath = "server.persistence.plugins.FilePlugin.FilePlugin";
			
			clazz = loader.loadClass(classPath);
			System.out.println(clazz.getName());
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (MalformedURLException e1)
		{// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
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
	
	public String GetType()
	{
		return DAOType;
	}
}

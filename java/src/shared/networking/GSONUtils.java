package shared.networking;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * 
 * @author Parker
 *
 */
public class GSONUtils 
{
	public static String serialize(Serializable obj)
	{
		Gson gson = new Gson();
		String retStr = gson.toJson(obj);
		
		return retStr;
	}
	
	public static Serializable deserialize(String json, java.lang.Class<Serializable> objClass)
	{
		Gson gson = new Gson();
		Serializable retObj = gson.fromJson(json, objClass);
		
		return retObj;
	}
}

package shared.networking;

import java.io.Serializable;
import java.util.Collection;

import com.google.gson.Gson;

/**
 * 
 * @author Parker
 *
 */
public class GSONUtils 
{
	private static Gson gson = null;
	
	private static Gson gson()
	{
		if (gson == null){
			gson = new Gson();
		}
		return gson;
	}
	public static String serialize(Serializable obj)
	{
		String retStr = GSONUtils.gson().toJson(obj);
		
		return retStr;
	}
	
	public static String serialize(Collection<?> obj)
	{
		String retStr = GSONUtils.gson().toJson(obj);
		
		return retStr;
	}
	
	public static <T extends Serializable> T deserialize(String json, java.lang.Class<T> objClass)
	{
		T retObj = GSONUtils.gson().fromJson(json, objClass);
		
		return retObj;
	}
}

package shared.networking;

import java.io.Serializable;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class that handles serialization of objects.
 * @author Parker
 *
 */
public class SerializationUtils 
{
	private static Gson gson = null;
	
	private static Gson gson()
	{
		if (gson == null)
			gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			
		return gson;
	}
	
	/**
	 * Serializes an object.
	 * @param obj The object to be serialized.
	 * @return The serialized object.
	 */
	public static String serialize(Serializable obj)
	{
		String retStr = SerializationUtils.gson().toJson(obj);
		
		return retStr;
	}
	
	/**
	 * Serializes a collection.
	 * @param obj The collection to be serialized.
	 * @return The serialized object.
	 */
	public static String serialize(Collection<?> obj)
	{
		String retStr = SerializationUtils.gson().toJson(obj);
		
		return retStr;
	}
	
	/**
	 * Deserializes an object.
	 * @param json The serialized object.
	 * @param objClass The class of the object that is being deserialized.
	 * @return The deserialized object.
	 */
	public static <T extends Serializable> T deserialize(String json, java.lang.Class<T> objClass)
	{
		System.out.println(json);
		T retObj = SerializationUtils.gson().fromJson(json, objClass);
		
		return retObj;
	}
}

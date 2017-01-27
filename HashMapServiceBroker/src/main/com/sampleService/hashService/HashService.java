package main.com.sampleService.hashService;

import java.util.HashMap;

public class HashService {

	private static HashMap <String, HashMap<String, String>> hashMapObject = null;
	
	private static HashService hashServiceObject = null;
	
	public HashService(){
		hashMapObject = new HashMap<String, HashMap<String, String>>();
	}
	
	public static HashService getHashServiceObject(){
		if(hashServiceObject == null){
			hashServiceObject = new HashService();
		}
		return hashServiceObject;
	}
	
	public void createHashMap(String id){
		hashMapObject.put(id, new HashMap<String, String>());
	}
	
	public String getHashValue(String instanceId, String key){
		HashMap<String, String> hashMapInstance = hashMapObject.get(instanceId);
		return hashMapInstance.get(key);
	}
	
	public void addHashKey(String instanceId, String key, String value){
		HashMap<String, String> hashMapInstance = hashMapObject.get(instanceId);
		if(hashMapInstance == null){
			System.out.println("Error");;
		}
		else{
			hashMapInstance.put(key, value);
		}
	}
	
	public void deleteHashValue(String instanceId, String key){
		HashMap<String, String> hashMapInstance = hashMapObject.get(instanceId);
		if(hashMapInstance == null){
			System.out.println("Error");;
		}
		else{
			hashMapInstance.remove(key);
		}
 	}
	
	public void deleteHashMap(String instanceId){
		hashMapObject.remove(instanceId);
	}
	
	
}

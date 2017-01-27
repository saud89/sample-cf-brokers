package main.com.sampleService.model;

public class HashMapPOJO {
	
	String key;
	
	String value;

	public HashMapPOJO() {
		super();
	}

	public HashMapPOJO(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "HashMapPOJO [key=" + key + ", value=" + value + "]";
	}
	
	

}

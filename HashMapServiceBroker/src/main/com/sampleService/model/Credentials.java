package main.com.sampleService.model;

public class Credentials {
	
	String uri;
	
	String username;
	
	String password;

	public Credentials() {
		super();
	}

	public Credentials(String uri, String username, String password) {
		super();
		this.uri = uri;
		this.username = username;
		this.password = password;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}

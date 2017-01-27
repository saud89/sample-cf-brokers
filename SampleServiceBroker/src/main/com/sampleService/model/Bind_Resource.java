package main.com.sampleService.model;

public class Bind_Resource {
	
	String app_guid;
	
	String route;

	public Bind_Resource() {
		super();
	}

	public Bind_Resource(String app_guid, String route) {
		super();
		this.app_guid = app_guid;
		this.route = route;
	}

	public String getApp_guid() {
		return app_guid;
	}

	public void setApp_guid(String app_guid) {
		this.app_guid = app_guid;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "Bind_Resource [app_guid=" + app_guid + ", route=" + route + "]";
	}
	
	

}

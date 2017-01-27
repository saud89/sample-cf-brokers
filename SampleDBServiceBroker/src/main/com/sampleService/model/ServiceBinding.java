package main.com.sampleService.model;

public class ServiceBinding {
	
	String service_bind_id;
	
	String service_instance_id;
	
	String service_id;
	
	String plan_id;
	
	String app_guid;
	
	String credentials_uri;
	
	String credentials_username;
	
	String credentials_password;

	public ServiceBinding() {
		super();
	}

	public ServiceBinding(String service_bind_id, String service_instance_id, String service_id, String plan_id,
			String app_guid, String credentials_uri, String credentials_username, String credentials_password) {
		super();
		this.service_bind_id = service_bind_id;
		this.service_instance_id = service_instance_id;
		this.service_id = service_id;
		this.plan_id = plan_id;
		this.app_guid = app_guid;
		this.credentials_uri = credentials_uri;
		this.credentials_username = credentials_username;
		this.credentials_password = credentials_password;
	}

	public String getService_instance_id() {
		return service_instance_id;
	}

	public void setService_instance_id(String service_instance_id) {
		this.service_instance_id = service_instance_id;
	}

	public String getService_bind_id() {
		return service_bind_id;
	}

	public void setService_bind_id(String service_bind_id) {
		this.service_bind_id = service_bind_id;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getApp_guid() {
		return app_guid;
	}

	public void setApp_guid(String app_guid) {
		this.app_guid = app_guid;
	}

	public String getCredentials_uri() {
		return credentials_uri;
	}

	public void setCredentials_uri(String credentials_uri) {
		this.credentials_uri = credentials_uri;
	}

	public String getCredentials_username() {
		return credentials_username;
	}

	public void setCredentials_username(String credentials_username) {
		this.credentials_username = credentials_username;
	}

	public String getCredentials_password() {
		return credentials_password;
	}

	public void setCredentials_password(String credentials_password) {
		this.credentials_password = credentials_password;
	}

	@Override
	public String toString() {
		return "ServiceBinding [service_bind_id=" + service_bind_id + ", service_instance_id=" + service_instance_id
				+ ", service_id=" + service_id + ", plan_id=" + plan_id + ", app_guid=" + app_guid
				+ ", credentials_uri=" + credentials_uri + ", credentials_username=" + credentials_username
				+ ", credentials_password=" + credentials_password + "]";
	}
	
}

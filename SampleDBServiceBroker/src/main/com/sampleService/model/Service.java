package main.com.sampleService.model;

public class Service {
	
	String name;
	
	String service_id;
	
	String description;
	
	boolean bindable;
	
	String plan_id;

	public Service() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBindable() {
		return bindable;
	}

	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}

	public String getPlanId() {
		return plan_id;
	}

	public void setPlanId(String planId) {
		this.plan_id = planId;
	}
	
}

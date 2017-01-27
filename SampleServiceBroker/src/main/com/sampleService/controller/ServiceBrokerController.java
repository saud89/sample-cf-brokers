package main.com.sampleService.controller;


import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import com.sampleService.resource.*;

import org.json.JSONObject;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import main.com.sampleService.hashService.ControllerBackendOps;
import main.com.sampleService.hashService.HashService;
import main.com.sampleService.model.Credentials;
import main.com.sampleService.model.ServiceBinding;
import main.com.sampleService.model.ServiceInstance;

@Path("/v2")
public class ServiceBrokerController {
	
	private static ControllerBackendOps ops = new ControllerBackendOps();
	//public static HashService hashService = new HashService();
	

	@GET
	@Path("/catalog")
	@Produces("application/json")
	public Response getCatalogServices(@HeaderParam("username") String username, 
			@HeaderParam("password")String password){
		
		org.json.simple.JSONObject catalogJSON = ops.getCatalogJSON();
		if(catalogJSON.isEmpty()){
			return Response.status(501).entity("{}").build();
		}
		return Response.status(200).entity(catalogJSON.toString()).build();
	}
	
	@PUT
	@Path("/service_instances/{instance_id}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createServiceInstance(@PathParam("instance_id") String instanceId,
										ServiceInstance si){
		JSONObject response = new JSONObject();
		si.setService_instance_id(instanceId);
		String serviceInstanceCompare = ops.serviceInstanceCompare(instanceId, si);
		int responseStatus = 400;
		switch(serviceInstanceCompare){
		case ControllerBackendOps.SERVICE_INSTANCE_SAME:
			responseStatus = 200;
			response.put("dashboardurl", ControllerBackendOps.uri+"/"+instanceId);
			break;
		case ControllerBackendOps.SERVICE_INSTANCE_DIFEERENT:
			responseStatus = 409;
			response.put("", "");
			break;
		case ControllerBackendOps.SERVICE_INSTANCE_DOESNT_EXISTS:
			ops.createServiceInstance(si);
			HashService.getHashServiceObject().createHashMap(instanceId);
			responseStatus = 201;
			response.put("dashboardurl", ControllerBackendOps.uri+"/"+instanceId);
			break;
		}
		
		return Response.status(responseStatus).entity(response.toString()).build();
	}
	
	@PUT
	@Path("/service_bindings")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createSomething(ServiceBinding sb){
		JSONObject response = new JSONObject();
		response.put("service_instance_Id", sb.getService_instance_id());
		response.put("planId", sb.getPlan_id());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@PUT
	@Path("/service_instances")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createSomethingInstances(ServiceInstance si){
		JSONObject response = new JSONObject();
		response.put("service_id", si.getService_id());
		response.put("plan_id", si.getPlan_id());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@PUT
	@Path("/service_instances/{instance_id}/service_bindings/{service_bind_id}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createServiceBinding(@PathParam("instance_id") String serviceInstanceId,
										@PathParam("service_bind_id") String serviceBindId,
										ServiceBinding sb){
		JSONObject response = new JSONObject();
		sb.setService_bind_id(serviceBindId);
		sb.setService_instance_id(serviceInstanceId);
		int responseStatus = 400;
		boolean checkIfServiceInstanceExists = ops.isServiceInstancePresent(serviceInstanceId);
		if(!checkIfServiceInstanceExists){
			return Response.status(400).entity("\"description\":\"ServiceInstanceId doesnt exist\"").build();
		}
		String serviceBindingCompare = ops.serviceBindCompareResult(sb, serviceBindId);
		switch (serviceBindingCompare){
		case ControllerBackendOps.SERVICE_BIND_SAME:
			responseStatus = 200;
			Credentials bindingcreds = ops.getBindingCredentials(serviceBindId);
			Map<String, Object> wrapper = new HashMap<>();
			wrapper.put("Credentials", bindingcreds);
			response = new JSONObject(wrapper);
			break;
		case ControllerBackendOps.SERVICE_BIND_DIFFERENT:
			responseStatus = 409;
			response.put("","");
			break;
		case ControllerBackendOps.SERVICE_BIND_DOESNT_EXISTS:
			sb.setCredentials_uri(ControllerBackendOps.uri+"/"+sb.getService_instance_id());
			sb.setCredentials_username(ControllerBackendOps.username);
			sb.setCredentials_password(ControllerBackendOps.password);
			ops.createServiceBinding(sb);
			responseStatus = 201;
			Credentials bindingcred = new Credentials(sb.getCredentials_uri(),sb.getCredentials_username(), sb.getCredentials_password());
			Map<String, Object> wrapper2 = new HashMap<>();
			wrapper2.put("Credentials", bindingcred);
			response = new JSONObject(wrapper2);
			break;
		}
		return Response.status(responseStatus).entity(response.toString()).build();
	}
	
	@DELETE
	@Path("/service_instances/{instance_id}/service_bindings/{service_bind_id}")
	@Produces("application/json")
	public Response deleteServiceBinding(@PathParam("instance_id") String serviceInstanceId,
			@PathParam("service_bind_id") String serviceBindId,
			@QueryParam("service_id") String service_id,
			@QueryParam("plan_id") String plan_id){
		JSONObject response = new JSONObject();
		response.put("", "");
		return Response.status(ops.checkDeleteBinding(serviceBindId)).entity(response.toString()).build();
	}
	
	@DELETE
	@Path("/service_instances/{instance_id}")
	@Produces("application/json")
	public Response deleteService(@PathParam("instance_id") String serviceInstanceId,
			@QueryParam("service_id") String service_id,
			@QueryParam("plan_id") String plan_id){
		JSONObject response = new JSONObject();
		response.put("", "");
		HashService.getHashServiceObject().deleteHashMap(serviceInstanceId);;
		return Response.status(ops.checkDeleteInstance(serviceInstanceId)).entity(response.toString()).build();
	}

}

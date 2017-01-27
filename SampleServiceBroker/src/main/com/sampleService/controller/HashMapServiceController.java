package main.com.sampleService.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import com.sampleService.resource.*;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.com.sampleService.hashService.HashService;
import main.com.sampleService.model.HashMapPOJO;

@Path("/hashService")
public class HashMapServiceController {

	//public static HashService hashService = new HashService();
	
	@GET
	@Path("/{instanceId}/{key}")
	@Produces("application/json")
	public Response getHashMapValue(@PathParam("instanceId") String instanceId,
								@PathParam("key") String key){
		JSONObject response = new JSONObject();
		String value = HashService.getHashServiceObject().getHashValue(instanceId, key);
		if(value != null){
			response.put("value", value);
			return Response.status(200).entity(response.toString()).build();
		}
		else{
			return Response.status(404).entity("{}").build();
		}
	}
	
	@GET
	@Path("/createaHashMap/{id}")
	@Produces("application/json")
	public Response createNewHashMap(@PathParam("id") String id){
		HashService.getHashServiceObject().createHashMap(id);
		System.out.println("was it created correctly???s");
		return Response.status(201).entity("{}").build();
	}
	
	@GET
	@Path("/createaHashMapResponse/{id}")
	@Produces("application/json")
	public Response createNewHashMapResponse(@PathParam("id") String id){
		HashService.getHashServiceObject().createHashMap(id);
		System.out.println("was it created correctly???s");
		JSONObject response = new JSONObject();
		response.put("done", "added");
		return Response.status(201).entity(response.toString()).build();
	}
	
	@POST
	@Path("/{instanceId}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putHashKey(@PathParam("instanceId") String instanceId,
								HashMapPOJO request ){
		HashService.getHashServiceObject().addHashKey(instanceId, request.getKey(), request.getValue());
		String value = HashService.getHashServiceObject().getHashValue(instanceId, request.getKey());
		JSONObject response = new JSONObject();
		if(value != null){
			response.put("value", value);
			return Response.status(201).entity(response.toString()).build();
		}
		else{
			return Response.status(404).entity("{}").build();
		}
		//return Response.status(201).entity(value).build();
	}
	
	@DELETE
	@Path("/{instanceId}/{key}")
	@Produces("application/json")
	public Response deleteHashKey(@PathParam("instanceId") String instanceId,
								@PathParam("key") String key){
		HashService.getHashServiceObject().deleteHashValue(instanceId, key);
		return Response.status(200).entity("{}").build();
	}
	
	public static void main (String args[]){
		HashService.getHashServiceObject().createHashMap("1");
		HashService.getHashServiceObject().addHashKey("1", "saud", "sap");
		System.out.println("Value is : "+HashService.getHashServiceObject().getHashValue("1", "saud"));
		
	}
}

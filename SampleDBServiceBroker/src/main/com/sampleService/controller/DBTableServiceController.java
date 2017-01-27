package main.com.sampleService.controller;

import java.util.HashMap;
import java.util.Map;

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

import main.com.sampleService.DBTableService.DBTableService;
import main.com.sampleService.model.Employee;

@Path("/tableService")
public class DBTableServiceController {
	
	private DBTableService dbService = new DBTableService();
	
	@GET
	@Path("/{instanceId}/{slNo}")
	@Produces("application/json")
	public Response getTableEntry(@PathParam("instanceId") String instanceId,
									@PathParam("slNo") int slNo){
		Employee emp = dbService.getTableRecord(instanceId, slNo);
		if(emp != null ){
			Map<String, Object> wrapper = new HashMap<>();
			wrapper.put("Employee", emp);
			JSONObject response = new JSONObject(wrapper); 
			return Response.status(200).entity(response.toString()).build();
		}
		else{
			return Response.status(404).entity("{}").build();
		}
	}
	
	@POST
	@Path("/{instanceId}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRecordIntoTable(@PathParam("instanceId") String instanceId, Employee emp ){
		dbService.insertTableRecord(instanceId, emp);
		return Response.status(201).entity("{}").build();
	}
	
	@DELETE
	@Path("/{instanceId}/{slNo}")
	@Produces("application/json")
	public Response deleteHashKey(@PathParam("instanceId") String instanceId,
								@PathParam("slNo") int slNo){
		dbService.deleteTableRecord(instanceId, slNo);
		return Response.status(200).entity("{}").build();
	}
}

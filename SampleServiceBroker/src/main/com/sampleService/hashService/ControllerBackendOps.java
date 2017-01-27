package main.com.sampleService.hashService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.com.sampleService.DriverConnection.DBConnection;
import main.com.sampleService.model.Credentials;
import main.com.sampleService.model.ServiceBinding;
import main.com.sampleService.model.ServiceInstance;

public class ControllerBackendOps {
	
	public final String servicesCatalog = "{\"services\" :[{ \"name\": "
			+ "\"Hash-Map-Service\", "
			+ "\"id\": \"bcd1696b-4058-4c06-ba31-19d9729069fb\","
			+ "\"description\": \"Hash Map service\","
			+ "\"bindable\": true,"
			+ "\"plan_updateable\": true,"
			+ "\"plans\": [{"
			+ " 		\"name\": \"Hash-Map-Plan\","
			+ " 		\"id\": \"efe5d937-930b-4401-a94f-c6ed0eeac81c\","
			+ " 		\"description\": \"Hash Map as a service\""
			+ " 		}]"
			+ " 	}]}";
	public static final  String uri = "sampleServiceBroker.cfapps.sapaws.hana.ondemand.com/hashService";
	public static final  String username = "saud";
	public static final  String password = "sap123";
	
	public static final  String SERVICE_INSTANCE_DOESNT_EXISTS = "service instance doesnt exists";
	public static final String SERVICE_INSTANCE_SAME = "service instance is same";
	public static final String SERVICE_INSTANCE_DIFEERENT = "service instance is different";
	public static final String SERVICE_BIND_SAME = "service Binding is same";
	public static final String SERVICE_BIND_DIFFERENT = "service instance is different";
	public static final String SERVICE_BIND_DOESNT_EXISTS = "service Binding doesnt exists";
	
	
	public static final String GET_SERVICE_INSTANCE_QUERY = "select * from service_instance where "
			+ "service_instance_id = ?";
	public static final String CREATE_SERVICE_INSTANCE_QUERY = "Insert into service_instance "
			+ " (service_instance_id,service_id, plan_id, organization_guid, space_guid) values "
			+ " (?,?,?,?,?)";
	private static final String GET_SERVICE_BIND_QUERY = "select * from service_binding where "
			+ "service_bind_id = ?";
	private static final String CREATE_SERVICE_BIND_QUERY = "Insert into service_binding "
			+ " (service_bind_id, service_instance_id, service_id, plan_id, app_guid, credentials_uri,"
			+ "	credentials_username, credentials_password) values"
			+ " (?,?,?,?,?,?,?,?)";
	private static final String DELETE_SERVICE_BIND_QUERY = "Delete from service_binding "
			+ "where service_bind_id = ?";
	private static final String DELETE_SERVICE_INSTANCE_QUERY = "Delete from service_instance"
			+ " where service_instance_id = ? ";
	
	
	
	
	public JSONObject getCatalogJSON() {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		Object obj1 = "";
		//Object obj2 = "";
		try{
			//ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			//classloader.get
			//String url = classloader.getResource("servicesCatalog.json").toString();
			//System.out.println("****** url is : " + url);
			//obj1 = parser.parse(new FileReader(classloader.getResource("servicesCatalog.json").toString()));
			obj1 = parser.parse(servicesCatalog);
			//obj2 = parser.parse(request);
		}
		catch(Exception e){
			System.out.println("Exception...");
			e.printStackTrace();
		}
		return  (JSONObject)obj1;
	}

	public String serviceInstanceCompare(String instanceId, ServiceInstance si) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(GET_SERVICE_INSTANCE_QUERY);
			pstmt.setString(1, instanceId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				//compare service instances
				return compareServiceInstanceParametersWithDB(si, rs);
			}
			else{
				return SERVICE_INSTANCE_DOESNT_EXISTS;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		
		return SERVICE_INSTANCE_DOESNT_EXISTS;
	}

	private String compareServiceInstanceParametersWithDB(ServiceInstance si, ResultSet rs) {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			if(rs.getString("service_id") != null){
				flag = flag && rs.getString("service_id").equalsIgnoreCase(si.getService_id());
			}
			if(rs.getString("plan_id") != null && flag){
				flag = flag && rs.getString("plan_id").equalsIgnoreCase(si.getPlan_id());
			}
			if(rs.getString("organization_guid") != null && flag){
				flag = flag && rs.getString("organization_GUID").equalsIgnoreCase(si.getOrganization_guid());
			}
			if(rs.getString("space_id") != null && flag){
				flag = flag && rs.getString("space_id").equalsIgnoreCase(si.getSpace_guid());
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		if(flag){
			return SERVICE_INSTANCE_SAME;
		}
		else{
			return SERVICE_INSTANCE_DIFEERENT;
		}
		
	}

	public boolean isServiceInstancePresent(String serviceInstanceId) {
		// TODO Auto-generated method stub
		try{
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(GET_SERVICE_INSTANCE_QUERY);
			pstmt.setString(1, serviceInstanceId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
			else 
				return false;
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		return false;
	}
	

	public void createServiceInstance(ServiceInstance si) {
		// TODO Auto-generated method stub
		try{
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(CREATE_SERVICE_INSTANCE_QUERY);
			pstmt.setString(1, si.getService_instance_id());
			pstmt.setString(2, si.getService_id());
			pstmt.setString(3, si.getPlan_id());
			pstmt.setString(4, si.getOrganization_guid());
			pstmt.setString(5, si.getSpace_guid());
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
	}

	public String serviceBindCompareResult(ServiceBinding sb, String serviceBindId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(GET_SERVICE_BIND_QUERY);
			pstmt.setString(1, serviceBindId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				//compare service instances
				return compareServiceBindParametersWithDB(sb, rs);
			}
			else{
				return SERVICE_BIND_DOESNT_EXISTS;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		
		return SERVICE_BIND_DOESNT_EXISTS;
		
		//return null;
	}

	private String compareServiceBindParametersWithDB(ServiceBinding sb, ResultSet rs) {
		// TODO Auto-generated method stub
		boolean flag = true;
		try {
			if(rs.getString("service_instance_id") != null){
				flag = flag && rs.getString("service_instance_id").equalsIgnoreCase(sb.getService_instance_id());
			}
			if(rs.getString("service_id") != null  && flag){
				flag = flag && rs.getString("service_id").equalsIgnoreCase(sb.getService_id());
			}
			if(rs.getString("plan_id") != null && flag){
				flag = flag && rs.getString("plan_id").equalsIgnoreCase(sb.getPlan_id());
			}
			if(rs.getString("AppGUID") != null && flag){
				flag = flag && rs.getString("AppGUID").equalsIgnoreCase(sb.getApp_guid());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag){
			return SERVICE_BIND_SAME;
		}
		else{
			return SERVICE_BIND_DIFFERENT;
		}
	}

	public Credentials getBindingCredentials(String serviceBindId) {
		// TODO Auto-generated method stub
		Credentials creds = null;		
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(GET_SERVICE_BIND_QUERY);
			pstmt.setString(1, serviceBindId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				creds = new Credentials(rs.getString("credentials_uri"), rs.getString("credentials_username"),
						rs.getString("credentials_password"));
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		return creds;
	}

	public void createServiceBinding(ServiceBinding sb) {
		// TODO Auto-generated method stub
		try{
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(CREATE_SERVICE_BIND_QUERY);
			pstmt.setString(1, sb.getService_bind_id());
			pstmt.setString(2, sb.getService_instance_id());
			pstmt.setString(3, sb.getService_id());
			pstmt.setString(4, sb.getPlan_id());
			pstmt.setString(5, sb.getApp_guid());
			pstmt.setString(6, sb.getCredentials_uri());
			pstmt.setString(7, sb.getCredentials_username());
			pstmt.setString(8, sb.getCredentials_password());
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		
	}

	public int checkDeleteBinding(String serviceBindId) {
		// TODO Auto-generated method stub
		int returnCode = 400;
		Credentials cred = getBindingCredentials(serviceBindId);
		if(cred == null){
			returnCode =  410;
		}
		else{
			try{
				PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(DELETE_SERVICE_BIND_QUERY);
				pstmt.setString(1, serviceBindId);
				if(pstmt.executeUpdate() >=0 ){
					returnCode = 200;
				}
			}
			catch(SQLException e){
				e.printStackTrace();
				returnCode = 400;
			}
			finally{
				DBConnection.closeConnection();
			}
		}
		return returnCode;
	}

	public int checkDeleteInstance(String serviceInstanceId) {
		// TODO Auto-generated method stub
		int returnCode = 400;
		try{
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(DELETE_SERVICE_INSTANCE_QUERY);
			pstmt.setString(1, serviceInstanceId);
			int res = pstmt.executeUpdate();
			if( res > 0){
				returnCode = 200;
			}
			else if(res == 0){
				returnCode = 410;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			returnCode = 400;
		}
		finally{
			DBConnection.closeConnection();
		}

		return returnCode;
	}


	/*public static void main (String args[]){
		ServiceInstance si = new ServiceInstance("abc", "def", "ghi", "jkl", null);
		ControllerBackendOps ops = new ControllerBackendOps();
		String result = ops.serviceInstanceCompare("abc", si);
		System.out.println( result);
		switch(result){
		case SERVICE_INSTANCE_SAME: System.out.println("!!!SAME!!! "); break;
		case SERVICE_INSTANCE_DIFEERENT : System.out.println("!!!!Different!!!"); break;
		default : System.out.println("asfdjlkas");
		}
		
	}*/
 
	
	

}

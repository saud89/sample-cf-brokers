package main.com.sampleService.DriverConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.com.sampleService.model.ServiceInstance;

public class DBConnection {

	public static final String CREATE_SERVICE_INSTANCE_TABLE_QUERY = "create table Service_Instance "
			+ " ( "
			+ " service_instance_id varchar(64) NOT NULL,"
			+ " service_id varchar(64),"
			+ " plan_id varchar(64),"
			+ " organization_guid varchar(64),"
			+ " space_guid varchar(64), "
			+ " PRIMARY KEY (service_instance_id)"
			+ " )";

	public static final String CREATE_SERVICE_BIND_TABLE_QUERY = "create table Service_Binding "
			+ " ( "
			+ " service_bind_id varchar(64) NOT NULL, "
			+ " service_instance_id varchar(64),"
			+ " service_id varchar(64), "
			+ " plan_id varchar(64), "
			+ " app_guid varchar(64),"
			+ " credentials_uri varchar(512),"
			+ " credentials_username varchar(32), "
			+ " credentials_password varchar(32), "
			+ " PRIMARY KEY (service_bind_id) "
			+ " ) ";
	public static Connection conn = null;

	public static Connection getDBConnection(){
		try{
			System.out.println("Starting to get the Database connection");
			Class.forName("org.postgresql.Driver");
			//conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SampleTest","test1","iso*help");
			//conn = DriverManager.getConnection("jdbc:postgresql://10.11.241.0:45548/a8a9fngplvrbq1p3", "ttjju0rmiyxzpt0t", "oadpjy5xb5va26ra");
			conn = DriverManager.getConnection("jdbc:postgresql://10.11.241.0:45548/a8a9fngplvrbq1p3", "ttjju0rmiyxzpt0t", "oadpjy5xb5va26ra");
		}
		catch(Exception e){
			System.out.println("Failed to get the Database connection using postgres Driver ");
			e.printStackTrace();
		}
		System.out.println("Database connection obtained from the postgres driver");
		return conn;
	}

	public static void closeConnection(){
		try {
			conn.close();
			System.out.println("Database connection closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("problems closing connection");
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		try{
			//PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement("Select version()");
			/*PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement("Select * from service_instance where service_instance_id = ?");
			System.out.println("Did you get the DB conneciton in this flow?");
			pstmt.setString(1, "abc");
			ServiceInstance si = new ServiceInstance("abc", "def", "ghi", "jkl", "mno");
			ServiceInstance dbsi = null;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()){
				System.out.println("rs value is : " + rs.getString(1));
				System.out.println("rs value of 2 is : " + rs.getString(2));
				System.out.println("rs value of 3 is : " + rs.getString(3));
				//System.out.println("rs value 2 is : " + rs.getString(2));
				dbsi = new ServiceInstance(rs.getString("service_instance_id"),
						rs.getString("service_Id"), rs.getString("plan_Id"), 
						rs.getString("organization_GUID"), rs.getString("space_Id"));
			}
			System.out.println("checking dbsi : " +dbsi.getOrganizationGUID() + " " + dbsi.getPlanId());
			if(si.equals(dbsi)){
				System.out.println(".equals works!!!!!!");
			}
			else{
				System.out.println(".equals doesnt work :(");
			}*/
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement("Delete from service_instance where service_instance_id = ? ");
			pstmt.setString(1, "pqr");
			System.out.println(pstmt.executeUpdate());
			//pstmt.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Error occurred..");
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		System.out.println("Done closing the main function..");
	}
}

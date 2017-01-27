package main.com.sampleService.DBTableService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.com.sampleService.DriverConnection.DBConnection;
import main.com.sampleService.model.Employee;

public class DBTableService {
	
	public void deleteTable(String serviceInstanceId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(dropTableQuery(serviceInstanceId));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
	}

	private String dropTableQuery(String serviceInstanceId) {
		// TODO Auto-generated method stub
		return ("Drop table Table_"+serviceInstanceId);  
	}

	public void createTable(String instanceId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(createTableQuery(instanceId));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
	}

	private String createTableQuery(String instanceId) {
		// TODO Auto-generated method stub
		return ("Create table Table_" +instanceId
				+ " ( "
				+ " slNo int NOT NULL, "
				+ " EmployeeName varchar(32), "
				+ " DeptName varchar(32),"
				+ " PRIMARY KEY (slNo)"
				+ ")");
	}

	public Employee getTableRecord(String instanceId, int slNo) {
		// TODO Auto-generated method stub
		Employee emp = null;
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(getTableEntryQuery(instanceId));
			pstmt.setInt(1, slNo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				emp = new Employee(rs.getInt("slNo"),rs.getString("EmployeeName"), rs.getString("deptName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		return emp;
	}

	private String getTableEntryQuery(String instanceId) {
		// TODO Auto-generated method stub
		return ("Select * from Table_" +instanceId + " where slNo = ? ");
	}

	public void insertTableRecord(String instanceId, Employee emp) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(insertTableEntryQuery(instanceId));
			pstmt.setInt(1, emp.getSlNo());
			pstmt.setString(2, emp.getEmployeeName());
			pstmt.setString(3,emp.getDeptName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
		
	}

	private String insertTableEntryQuery(String instanceId) {
		// TODO Auto-generated method stub
		return ("Insert into Table_"+instanceId 
				+" (slNo, EmployeeName, DeptName) "
				+" values (?,?,?)");
	}

	public void deleteTableRecord(String instanceId, int slNo) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(deleteTableEntryQuery(instanceId));
			pstmt.setInt(1,slNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBConnection.closeConnection();
		}
	}

	private String deleteTableEntryQuery(String instanceId) {
		// TODO Auto-generated method stub
		return (" Delete from Table_" + instanceId
				+" where slNo = ?");
	}

}

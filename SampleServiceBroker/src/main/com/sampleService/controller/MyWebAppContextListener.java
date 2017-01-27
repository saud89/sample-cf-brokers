package main.com.sampleService.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import main.com.sampleService.DriverConnection.DBConnection;



public class MyWebAppContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		DBConnection.closeConnection();
		System.out.println("Closed the DB connection... from context listener");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = DBConnection.getDBConnection().prepareStatement(DBConnection.CREATE_SERVICE_INSTANCE_TABLE_QUERY);
			pstmt.executeUpdate();
			System.out.println("Created Service_instance table..");
			PreparedStatement pstmt2 = DBConnection.getDBConnection().prepareStatement(DBConnection.CREATE_SERVICE_BIND_TABLE_QUERY);
			pstmt2.executeUpdate();
			System.out.println("Created Service_Bind table..");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in creating the Service tables..");
			e.printStackTrace();
		}
	}

}

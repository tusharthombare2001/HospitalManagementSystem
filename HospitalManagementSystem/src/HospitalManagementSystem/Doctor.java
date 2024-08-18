package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	private Connection connection;
	
	
	
	public Doctor(Connection connection) {
		this.connection = connection;
		
	}
	

	
	
	
	 void viewDoctors(){
		String query = "select *from doctors ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Doctors : ");
			System.out.println("+-------------+---------------------+--------------------+");
			System.out.println("| Doctor Id   | name                | Specialization     |");
			System.out.println("+-------------+---------------------+--------------------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String specialization = resultSet.getString("specialization");
				System.out.printf("|%-13s|%-21s|%-20s|\n",id, name, specialization);
				System.out.println("+-------------+---------------------+--------------------+");
			//	System.out.printf("%-10s %-20s %-20s\n", "Doctor Id", "Name", "Specialization");

				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean getDoctorById(int id){
		String query = "select *from doctors where id = ?";
		try {
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		 preparedStatement.setInt(1,id);
		 
		 ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()){ // this method hold a row which come from databse 
			return true;  // if data come in row formar then it will show us true
		}else {
			return false; //otherwise false 
		}
		
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false; // initialy it is false 
		
		
	}

}

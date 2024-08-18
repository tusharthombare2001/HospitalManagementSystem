package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
	private Connection connection;
	private Scanner scanner;
	
	
	public patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	

	
	public void addPatient() {
		System.out.print("Enter Patients Name : ");
		String name = scanner.next();
		
		System.out.print("Enter patients Age  :");
		int age = scanner.nextInt();
		
		System.out.print("Enter patient Gender : ");
		String gender = scanner.next();
		
		
		try {
		String query = "insert into patients (name, age, gender) values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, age);
		preparedStatement.setString(3, gender);
		
		
		int affectedRows = preparedStatement.executeUpdate();
		if(affectedRows > 0){
			System.out.println("patients Aded Succefully...!!");
		}else {
			System.out.println("Failed To Add Patient");
		}
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	 void viewPatients(){
		String query = "select *from patients ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Patients : ");
			System.out.println("+-------------+---------------------+----------+------------+");
			System.out.println("| patients Id | name                | age      | gender     |");
			System.out.println("+-------------+---------------------+----------+------------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("| %-11s | %-19s | %-8s | %-10s |\n",id, name , age, gender);
				System.out.println("+-------------+---------------------+----------+------------+");
				
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean getPatientsById(int id){
		String query = "select *from patients where id = ?";
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































































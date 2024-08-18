package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSyastem {
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";
	private static final String password = "thombare123";
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
			try {
				Connection connection = DriverManager.getConnection(url, username, password);
				
				patient patient = new patient(connection, scanner);
				Doctor doctor = new Doctor(connection);
				
				while(true) {
					System.out.println("***** HOSPITAL MANAGEMENT SYSTEM *****");
					System.out.println("1. Add patient");
					System.out.println("2. view Patients");
					System.out.println("3. view Doctors");
					System.out.println("4. Book Appointment");
					System.out.println("5. Exit");
					System.out.println("Enter Your choise");
					
					int choise = scanner.nextInt();
					switch (choise){
					case 1:
					//Add patient
						patient.addPatient(); // invoke patient by there method it is present in patient class
						break;
					
					case 2:
					// View patient
						patient.viewPatients();// calling method 
						System.out.println();
						break;
					
					case 3:
						//View Doctor
						doctor.viewDoctors();
						System.out.println();
						break;
					case 4:
						//Book Appointment
						bookAppointment(patient, doctor, connection, scanner);
						System.out.println();
						break;
					case 5:
						System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM...!!");
						//
						return;
						default:
							System.out.println("Eenter valid choise");
					}
					
				}
				
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		
	}
	public static void bookAppointment(patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter patient Id : ");
		int patientId = scanner.nextInt();
		
		System.out.println("Enter Doctor Id");
		int doctorId = scanner.nextInt();
		
		System.out.println("Enter appintment date (yyyy-mm-dd)");
		String appointmentDate = scanner.next();
		if(patient.getPatientsById(patientId) && doctor.getDoctorById(doctorId)) {//check patient is availabale  && doctor is available it return true 
			if(checkDoctorAvailability(doctorId, appointmentDate, connection)) { //check is dictor is available for this particular data
				String appointmentQuery = "insert into appointments(patients_id, doctor_id, appointment_date) values (?, ?, ?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected = preparedStatement.executeUpdate();
					if(rowsAffected > 0) {
						System.out.println("Appointment Booked !");
					}else {
						System.out.println("Failed To Book Appointment");
					}	
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("Doctor not available on this data ");
			}
		} else {
			System.out.println("Either doctor or patient dosen't exist");
		}
		
	}	

	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
	String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
	try {
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, doctorId);
		preparedStatement.setString(2, appointmentDate);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			int count = resultSet.getInt(1);//getting id from doctor table
			if(count==0) { // check if rows come from doctor database then it will true
				return true;
			}else {
				return false;
			}
		}
		
	}catch(SQLException e) {
		e.printStackTrace();
		}
	return false;
	}
	
}













































































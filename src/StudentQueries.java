
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Taylor Casavant
 */
public class StudentQueries {
	private static Connection connection;
	private static PreparedStatement addStudent;
	private static PreparedStatement getAllStudents;
	private static PreparedStatement getStudent;
	private static PreparedStatement dropStudent;
	private static PreparedStatement dropStudentGetWaitlist;
	private static ResultSet resultSet;
	private static ResultSet resultSetWaitlist;

	public static void addStudent(StudentEntry student){
		connection = DBConnection.getConnection();
        try
        {
			// Insert attributes of input student to new table row
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) "
												  + "values (?, ?, ?)");
			addStudent.setString(1, student.getStudentID());
			addStudent.setString(2, student.getFirstName());
			addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
	}
	
	
	public static ArrayList<StudentEntry> getAllStudents(){
		connection = DBConnection.getConnection();
		ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();
		
		try {
			// Get all columns from COURSE table  where semester matches selected semester
			getAllStudents = connection.prepareStatement("select * from app.student");
			resultSet = getAllStudents.executeQuery();

			while (resultSet.next())
			{
				String studentID = resultSet.getString(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				
				StudentEntry currentStudent = new StudentEntry(studentID, firstName, lastName);
				
				allStudents.add(currentStudent);
			}
			
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return allStudents;
	}
	
	public static StudentEntry getStudent(String studentID){
		connection = DBConnection.getConnection();
		StudentEntry student = null;
        try
        {
			// Get row from STUDENT table with matching studentID
            getStudent = connection.prepareStatement("select * from app.student where STUDENTID = ?");
			getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
			
			// Make StudentEntry object from result
			while (resultSet.next()){
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				student = new StudentEntry(studentID, firstName, lastName);
			}
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
		return student;
	}
	
	public static void dropStudent(String studentID){
		connection = DBConnection.getConnection();

		try
        {
			// Delete student from table
			dropStudent = connection.prepareStatement("delete from app.student where STUDENTID = ?");
			dropStudent.setString(1, studentID);
			dropStudent.executeUpdate();

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
	}
	
}

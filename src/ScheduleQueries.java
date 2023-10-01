
import java.sql.Timestamp;
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
public class ScheduleQueries {
	private static Connection connection;
	private static PreparedStatement addStudent;
	private static PreparedStatement getScheduleByStudent;
	private static PreparedStatement getScheduledStudentCount;
	private static PreparedStatement getScheduledStudentsByCourse;
	private static PreparedStatement getWaitlistedStudentsByCourse;
	private static PreparedStatement dropStudentScheduleByCourse;
	private static PreparedStatement dropScheduleByCourse;
	private static PreparedStatement updateScheduleEntry;
	private static ResultSet resultSet;
	
	public static void addScheduleEntry(ScheduleEntry entry){
		connection = DBConnection.getConnection();
        try
        {
			// Insert attributes of input course to new table row

            addStudent = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) "
												  + "values (?, ?, ?, ?, ?)");
			addStudent.setString(1, entry.getSemester());
			addStudent.setString(2, entry.getStudentID());
			addStudent.setString(3, entry.getCourseCode());
			addStudent.setString(4, entry.getStatus());
			addStudent.setTimestamp(5, entry.getTimestamp());
            addStudent.executeUpdate();
			
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
	}
	
	
	public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
		connection = DBConnection.getConnection();
		ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
		
		try {
			// Get all columns from SCHEDULE table  where semester matches selected semester and studentID matches selected student
			getScheduleByStudent = connection.prepareStatement("select * from app.schedule where SEMESTER = ? and STUDENTID = ?");
			getScheduleByStudent.setString(1, semester);
			getScheduleByStudent.setString(2, studentID);
			resultSet = getScheduleByStudent.executeQuery();

			// Form new schedule entry and add to list
			while (resultSet.next())
			{
				String courseCode = resultSet.getString(3);
				String status = resultSet.getString(4);
				Timestamp timestamp = resultSet.getTimestamp(5);
				
				ScheduleEntry currentScheduleEntry = new ScheduleEntry(semester, studentID, courseCode, status, timestamp);
				
				schedule.add(currentScheduleEntry);
			}
			
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return schedule;
	}
	
	
	public static int getScheduledStudentCount(String semester, String courseCode){
		connection = DBConnection.getConnection();
		int count = 0;
		
		try {
			// Get all columns from COURSE table  where semester matches selected semester
			getScheduledStudentCount = connection.prepareStatement("select * from app.schedule where SEMESTER = ? and COURSECODE = ?");
			getScheduledStudentCount.setString(1, semester);
			getScheduledStudentCount.setString(2, courseCode);
			resultSet = getScheduledStudentCount.executeQuery();

			// Count number of scheduled students
			while (resultSet.next())
			{
				count++;			
			}
			
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return count;
	}
	
	public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
		connection = DBConnection.getConnection();
		ArrayList<ScheduleEntry> students = new ArrayList<>();
		
		try {
			// Get all columns from COURSE table  where semester matches selected semester
			getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where SEMESTER = ? and COURSECODE = ? and STATUS = 'S' order by TIMESTAMP");
			getScheduledStudentsByCourse.setString(1, semester);
			getScheduledStudentsByCourse.setString(2, courseCode);
			resultSet = getScheduledStudentsByCourse.executeQuery();

			// Count number of scheduled students
			while (resultSet.next())
			{
				if(resultSet.getString(4).equals("S"))
				{
					String studentID = resultSet.getString(2);
					String status = resultSet.getString(4);
					Timestamp timestamp = resultSet.getTimestamp(5);
				
					students.add(new ScheduleEntry(semester, studentID, courseCode, status, timestamp));
				}
			}
			
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return students;
	}
	
	public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
		connection = DBConnection.getConnection();
		ArrayList<ScheduleEntry> students = new ArrayList<>();
		
		try {
			// Get all columns from COURSE table  where semester matches selected semester
			getWaitlistedStudentsByCourse = connection.prepareStatement("select * from app.schedule where SEMESTER = ? and COURSECODE = ? and STATUS = 'W' order by TIMESTAMP");
			getWaitlistedStudentsByCourse.setString(1, semester);
			getWaitlistedStudentsByCourse.setString(2, courseCode);
			resultSet = getWaitlistedStudentsByCourse.executeQuery();

			// Count number of scheduled students
			while (resultSet.next())
			{
				if(resultSet.getString(4).equals("W"))
				{
					String studentID = resultSet.getString(2);
					String status = resultSet.getString(4);
					Timestamp timestamp = resultSet.getTimestamp(5);
				
					students.add(new ScheduleEntry(semester, studentID, courseCode, status, timestamp));
				}
			}
			
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return students;
	}
	
	public static void updateScheduleEntry(String semester, ScheduleEntry entry){
		connection = DBConnection.getConnection();
		
		try {
			// Change status to scheduled for matching entry
			updateScheduleEntry = connection.prepareStatement("update app.schedule set STATUS = 'S' where SEMESTER = ? and COURSECODE = ? and STUDENTID = ? and TIMESTAMP = ?");
			updateScheduleEntry.setString(1, entry.getSemester());
			updateScheduleEntry.setString(2, entry.getCourseCode());
			updateScheduleEntry.setString(3, entry.getStudentID());
			updateScheduleEntry.setTimestamp(4, entry.getTimestamp());
			updateScheduleEntry.executeUpdate();

			
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}

	}
	
	
	public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
		connection = DBConnection.getConnection();
		
		try {
			// Remove input student's schedule entry
			dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where SEMESTER = ? and STUDENTID = ? and COURSECODE = ?");
			dropStudentScheduleByCourse.setString(1, semester);
			dropStudentScheduleByCourse.setString(2, studentID);
			dropStudentScheduleByCourse.setString(3, courseCode);
			dropStudentScheduleByCourse.executeUpdate();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
	}
	
	
	public static void dropScheduleByCourse(String semester, String courseCode){
		connection = DBConnection.getConnection();
		
		try {
			// Remove all entries of input course
			dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where SEMESTER = ? and COURSECODE = ?");
			dropScheduleByCourse.setString(1, semester);
			dropScheduleByCourse.setString(2, courseCode);
			dropScheduleByCourse.executeUpdate();

		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
	}
}

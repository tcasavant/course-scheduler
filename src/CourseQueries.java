
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
public class CourseQueries {
	private static Connection connection;
	private static PreparedStatement getAllCourses;
	private static PreparedStatement addCourse;
	private static PreparedStatement getAllCourseCodes;
	private static PreparedStatement getCourseSeats;
	private static PreparedStatement dropCourse;
	private static ResultSet resultSet;
	
	public static ArrayList<CourseEntry> getAllCourses(String semester){
		connection = DBConnection.getConnection();
		ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
		
		try {
			// Get all columns from COURSE table  where semester matches selected semester
			getAllCourses = connection.prepareStatement("select * from app.course where SEMESTER = ?");
			getAllCourses.setString(1, semester);
			resultSet = getAllCourses.executeQuery();

			while (resultSet.next())
			{
				// Get course attributes from each row and create a course from them, then add to list
				String courseCode = resultSet.getString(2);
				String description = resultSet.getString(3);
				int seats = Integer.parseInt(resultSet.getObject(4).toString());
				
				CourseEntry currentCourse = new CourseEntry(semester, courseCode, description, seats);
				
				allCourses.add(currentCourse);
			}
			
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return allCourses;
	}

	
	public static void addCourse(CourseEntry course){
		connection = DBConnection.getConnection();
        try
        {
			// Insert attributes of input course to new table row
            addCourse = connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) "
												  + "values (?, ?, ?, ?)");
			addCourse.setString(1, course.getSemester());
			addCourse.setString(2, course.getCourseCode());
			addCourse.setString(3, course.getDescription());
			addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
	}
	
	
	public static ArrayList<String> getAllCourseCodes(String semester){
		connection = DBConnection.getConnection();
		ArrayList<String> allCourseCodes = new ArrayList<String>();
		
		try {
			// Get all course code column from COURSE table where semester matches selected semester
			getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where SEMESTER = ?");
			getAllCourseCodes.setString(1, semester);
			resultSet = getAllCourseCodes.executeQuery();

			while (resultSet.next()) 
			{				
				allCourseCodes.add(resultSet.getString(1));
			}
			
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}

		return allCourseCodes;
	}
	
	
	public static int getCourseSeats(String semester, String courseCode){
		connection = DBConnection.getConnection();
		int seats = -1;
		
		try {
			// Get seats column from COURSE table where semester matches selected semester
			getCourseSeats = connection.prepareStatement("select seats from app.course where SEMESTER = ? and COURSECODE = ?");
			getCourseSeats.setString(1, semester);
			getCourseSeats.setString(2, courseCode);
			resultSet = getCourseSeats.executeQuery();

			// Get the only value in the resultSet
			while(resultSet.next())
				seats = resultSet.getInt(1);
			
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
		return seats;
	}
		
	public static void dropCourse(String semester, String courseCode){
		connection = DBConnection.getConnection();
		
		try {
			// Remove course from table
			dropCourse = connection.prepareStatement("delete from app.course where SEMESTER = ? and COURSECODE = ?");
			dropCourse.setString(1, semester);
			dropCourse.setString(2, courseCode);
			dropCourse.executeUpdate();
			
			// Remove all schedule entries of course
			ScheduleQueries.dropScheduleByCourse(semester, courseCode);

		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
	}
}

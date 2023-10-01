/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Taylor Casavant
 */
public class CourseEntry {
	public String semester;
	public String courseCode;
	public String description;
	public int seats;

	public CourseEntry(String semester, String courseCode, String description, int seats) {
		this.semester = semester;
		this.courseCode = courseCode;
		this.description = description;
		this.seats = seats;
	}

	public String getSemester() {
		return semester;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getDescription() {
		return description;
	}

	public int getSeats() {
		return seats;
	}
	
}

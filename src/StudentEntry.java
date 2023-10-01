/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Taylor Casavant
 */
public class StudentEntry {
	public String studentID;
	public String firstName;
	public String lastName;

	public StudentEntry(String studentID, String FirstName, String LastName) {
		this.studentID = studentID;
		this.firstName = FirstName;
		this.lastName = LastName;
	}

	public String getStudentID() {
		return studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String toString() {
		return lastName + "," + firstName + " " + studentID;
	}
}

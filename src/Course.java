import java.util.ArrayList;
import java.util.Scanner;

public class Course implements java.io.Serializable {
	private String courseName;
	private String courseID;
	private int maxStudents;
	private int currStudents;
	private ArrayList<String> listOfStudents; // list of Student Names registered to the course
	private String instructorName;
	private int sectionNum;
	private String courseLocation;
	
	// no-args constructor that will give every data field its default value
	Course() {}
	
	// constructor that takes parameter from the user
	Course(String name, String id, int max, int curr, String instructor, int section, String loc) {
		courseName = name;
		courseID = id;
		maxStudents = max;
		currStudents = curr;
		listOfStudents = new ArrayList<String>(); // creates empty arrayList because no student enrolled yet
		instructorName = instructor;
		sectionNum = section;
		courseLocation = loc;
	}
	
	// getters and setters for all private fields
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public int getMaxStudents() {
		return maxStudents;
	}
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	public int getCurrStudents() {
		return currStudents;
	}	
	public void incCurrStudents() {
		this.currStudents += 1;
	}	
	public void decCurrStudents() {
		this.currStudents -= 1;
	}	
	public String getInstructorName() {
		return instructorName;
	}
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	public int getSectionNum() {
		return sectionNum;
	}
	public void setSectionNum(int sectionNum) {
		this.sectionNum = sectionNum;
	}
	public String getCourseLocation() {
		return courseLocation;
	}
	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}
	
	// display all information for a given course
	public void view() { 
		System.out.println("Course Name: " + courseName);
		System.out.println("Course ID: " + courseID);
		System.out.println("Course maximum capacity: " + maxStudents);
		System.out.println("Current number of students registered: " + currStudents);
		// list of students
		System.out.print("List of students currently registered: ");
		for (int i = 0; i < listOfStudents.size(); i++) {
			System.out.print(listOfStudents.get(i) + ", ");
		}
		System.out.print("\n");
		System.out.println("Instructor's Name: " + instructorName);
		System.out.println("Section Number: " + sectionNum);
		System.out.println("Course Location: " + courseLocation);
	}
	
	// adding and removing a student from the list of students registered to a specific course
	public void addStudent(String fname, String lname) {
		listOfStudents.add(fname + " " + lname);
	}
	public void removeStudent(String fname, String lname) {
		listOfStudents.remove(fname + " " + lname);
	}
	
	// returns the size of the list of students registered to a course
	public int getSize() {
		return listOfStudents.size();
	}
	
	// returns the name of student at index i
	public String get(int i) {
		return listOfStudents.get(i);
	}
}

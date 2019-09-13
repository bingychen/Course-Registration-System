import java.util.ArrayList;

public class Student extends User implements StudentInterface, java.io.Serializable {
	private ArrayList<String> listOfCourses;
	
	// no-args default constructor for Student user
	Student() {}
	
	// overloaded constructor for Student user
	Student(String fname, String lname, String uname, String pass) {
		super.firstName = fname;
		super.lastName = lname;
		super.username = uname;
		super.password = pass;
		listOfCourses = new ArrayList<>(); // by default, new students are not registered for any courses yet
	}
	
	// displays the menu options for the student
	@Override
	public void displayMenu() {
		System.out.println("Course Management for the Student");
		System.out.println("Select a numbered option:");
		System.out.println("1. View all courses");
		System.out.println("2. View all available courses that are not full");
		System.out.println("3. Register a course");
		System.out.println("4. Withdraw from a course");
		System.out.println("5. View all currently registered course(s)");
		System.out.println("6. Exit");
	}
	
	// adds and remove a course from student's list of registered courses
	@Override
	public void addCourse(String cName, int section) {
		String hold = cName + " Section: " + section;
		hold = hold.trim();
		this.listOfCourses.add(hold);
	}
	@Override
	public void removeCourse(String cName, int section) {
		String hold = cName + " Section: " + section;
		hold = hold.trim();
		this.listOfCourses.remove(hold);
	}
	
	// displays all the courses the student is registered in
	@Override
	public void displayRegCourses() {
		for (int i = 0; i < this.listOfCourses.size(); i++) {
			System.out.println((i+1) + ". " + this.listOfCourses.get(i));
		}
	}
	
	// lists all available courses
	@Override
	public void viewCourse(CourseList c) {
		String space;
		for (int i = 0; i < c.getSize(); i++) {
			if (c.get(i).getCurrStudents() == c.get(i).getMaxStudents()) {
				space = "Class is full.";
			}
			else {
				space = "Space available.";
			}
			System.out.println(c.get(i).getCourseName() + " " + c.get(i).getCourseID() + " Sec: " + c.get(i).getSectionNum() + " Prof. " + c.get(i).getInstructorName() + " Loc: " + c.get(i).getCourseLocation() + ": " + c.get(i).getCurrStudents() + "/" + c.get(i).getMaxStudents() + " students. " + space);
		}
	}
	
	// lists all courses that are not full
	@Override
	public void viewCourseNotFull(CourseList c) {
		for (int i = 0; i < c.getSize(); i++) {
			if (c.get(i).getCurrStudents() != c.get(i).getMaxStudents()) {
				System.out.println(c.get(i).getCourseName() + " " + c.get(i).getCourseID() + " Sec: " + c.get(i).getSectionNum() + " Prof. " + c.get(i).getInstructorName() + " Loc: " + c.get(i).getCourseLocation() + ": " + c.get(i).getCurrStudents() + "/" + c.get(i).getMaxStudents() + " students. Space available.");
			}	
		}
	}
	
	// searches for a course and returns true if it is found
	@Override
	public boolean searchCourse(String cName, int section) {
		String key = cName + " Section: " + section;
		key = key.trim();
		String hold;
		 for (int i = 0; i < this.listOfCourses.size(); i++) {
			hold = this.listOfCourses.get(i);
			hold = hold.trim();
			if (key.equals(hold)) {
				return true;
			}
		}
		return false; 
	}
	
	// gets the size of the arraylist listOfCourses
	public int getSize() {
		return listOfCourses.size();
	}
	
	// gets the course (String) at index i
	public String get(int i) {
		return listOfCourses.get(i);
	}
}

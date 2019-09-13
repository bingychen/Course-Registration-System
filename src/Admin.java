
public class Admin extends User implements AdminInterface {
	
	// no-args default constructor for Admin user
	Admin() {}
	
	// overloaded constructor for Admin user
	Admin(String fname, String lname, String uname, String pass) {
		super.firstName = fname;
		super.lastName = lname;
		super.username = uname;
		super.password = pass;
	}
	
	// displays the course management menu options for the admin user
	@Override
	public void displayMenu() {
		System.out.println("Course Management for Admin");
		System.out.println("Select a numbered option:");
		System.out.println("1. Create a new course");
		System.out.println("2. Delete an existing course");
		System.out.println("3. Edit an existing course");
		System.out.println("4. Display course information for a specific course");
		System.out.println("5. Register a student");
		System.out.println("6. Back to main menu");
		System.out.println("7. Exit");
		
	}
	
	// displays reports menu options for the admin user
	public void displayMenu2() {
		System.out.println("Reports for Admin");
		System.out.println("Select a numbered option:");
		System.out.println("1. View all courses");
		System.out.println("2. View all courses at max capacity");
		System.out.println("3. Export all courses that are full");
		System.out.println("4. View all students registered to a specific course");
		System.out.println("5. View all registered course(s) for a specific student");
		System.out.println("6. Sort courses");
		System.out.println("7. Back to main menu");
		System.out.println("8. Exit");
	}
	
	// used to check the username and password of the admin
	@Override
	public boolean logInCheck(String uname, String pword) {
		if (uname.equals(this.username) && pword.equals(this.password)) {
			return true;
		}
		return false;
	}
	
	// lists all courses
	@Override
	public void viewCourse(CourseList c, StudentsList s) {
		for (int i = 0; i < c.getSize(); i++) {
			System.out.println(c.get(i).getCourseName() + " " + c.get(i).getCourseID() + " Sec: " + c.get(i).getSectionNum() + " Currently Enrolled: " + c.get(i).getCurrStudents() + " Maximum Capacity: " + c.get(i).getMaxStudents());
			System.out.print("Students currently enrolled: ");
			for (int j = 0; j < c.get(i).getSize(); j++) {
				System.out.print(c.get(i).get(j) + ", ");
			}
			System.out.print("\n");
		}
	}
	
	// lists all the courses that are full
	@Override
	public void viewCourseFull(CourseList c, StudentsList s) {
		for (int i = 0; i < c.getSize(); i++) {
			if (c.get(i).getCurrStudents() == c.get(i).getMaxStudents()) {
				System.out.println(c.get(i).getCourseName() + " " + c.get(i).getCourseID() + " Sec: " + c.get(i).getSectionNum() + " Currently Enrolled: " + c.get(i).getCurrStudents() + " Maximum Capacity: " + c.get(i).getMaxStudents());
				System.out.print("Students currently enrolled: ");
				for (int j = 0; j < c.get(i).getSize(); j++) {
					System.out.print(c.get(i).get(j) + ", ");
				}
				System.out.print("\n");
			}	
		}
	}
}

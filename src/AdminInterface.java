
public interface AdminInterface {
	boolean logInCheck(String uname, String pword);
	void viewCourse(CourseList c, StudentsList s);
	void viewCourseFull(CourseList c, StudentsList s);
}

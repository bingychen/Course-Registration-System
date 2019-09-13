
public interface StudentInterface {
	void addCourse(String cName, int section);
	void removeCourse(String cName, int section);
	void displayRegCourses();
	void viewCourse(CourseList c);
	void viewCourseNotFull(CourseList c);
	boolean searchCourse(String cName, int section);
}

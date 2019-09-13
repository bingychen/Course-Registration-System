
public class Storage implements java.io.Serializable {
	private CourseList c;
	private StudentsList s;
	
	Storage(CourseList c, StudentsList s) {
		this.c = c;
		this.s = s;
	}
	public CourseList getCourseList() {
		return c;
	}
	public StudentsList getStudentsList() {
		return s;
	}
}

import java.util.ArrayList;

// An ArrayList of Course objects acting as master list of all course created
public class CourseList implements java.io.Serializable {
	private ArrayList<Course> list;
	
	// no-args constructor that creates an arraylist of Course objects with initial capacity of 10
	CourseList() {
		list = new ArrayList<>();
	}
	
	// add or remove a Course object to/from the master list of courses
	public void addCourse(Course c) {
		list.add(c);
	}
	public void removeCourse(Course c) {
		list.remove(c);
	}
	
	// returns an ArrayList of Course objects with all courses that are full
	public ArrayList<Course> getCourseFull() {
		ArrayList<Course> courseFull = new ArrayList<>(0);
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCurrStudents() == list.get(i).getMaxStudents()) {
				courseFull.add(list.get(i));
			}
		}
		return courseFull;
	}
	
	// returns the size of the courselist
	public int getSize() {
		return list.size();
	}
	// returns a course object at index i
	public Course get(int i) {
		return list.get(i);
	}
	
	// searches for a course using course name and section number
	public int search(String cName, int section) {
		for (int i = 0; i < list.size(); i++) {
			if (cName.equals(list.get(i).getCourseName())) {
				if (section == list.get(i).getSectionNum()) {
					return i;
				}
			}
		}
		return -1; 
	}
	
	// overloaded search method to search for a course using course id
	public int search(String id) {
		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getCourseID())) {
				return i;
			}
		}
		return -1;
	}
	
	// sort courses from least num of students registered to most
	public void sortCourses() {
		for (int i = 0; i < list.size() - 1; i++) {
			double currMinStudents = list.get(i).getCurrStudents();
			Course currMin = list.get(i);
			int currMinIndex = i;
			
			for (int j = i + 1; j < list.size(); j++) {
				if (currMinStudents > list.get(j).getCurrStudents()) {
					currMinStudents = list.get(j).getCurrStudents();
					currMin = list.get(j);
					currMinIndex = j;
				}
			}
			
			if (currMinIndex != i) {
				list.set(currMinIndex, list.get(i));
				list.set(i, currMin);
			}
		}
	}
}

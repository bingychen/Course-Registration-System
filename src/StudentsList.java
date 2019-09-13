import java.util.ArrayList;

// an ArrayList of Student objects acting as a master list of all students registered
public class StudentsList implements java.io.Serializable {
	private ArrayList<Student> list;
	
	// no-args constructor that creates an arraylist of Student objects with initial capacity of 10
	StudentsList() {
		list = new ArrayList<>();
	}
	
	// add or remove a Student object to/from the list of registered students
	public void addStudent(Student s) {
		list.add(s);
	}
	public void removeStudent(Student s) {
		list.remove(s);
	}
	
	// allows user to access Student objects saved in arraylist
	public Student get(int i) {
		return list.get(i);
	}
	
	// search method to find student using first name and last name
	public int search(String fname, String lname) {
		for (int i = 0; i < list.size(); i++) {
			if (fname.equals(list.get(i).getFirstName())) {
				if (lname.equals(list.get(i).getLastName())) {
					return i;
				}
			}
		}
		return -1;
	}
	// overloaded search method to find student using username
	public int search(String uname) {
		for (int i = 0; i < list.size(); i++) {
			if (uname.equals(list.get(i).getUsername())) {
				return i;
			}
		}
		return -1;
	}
	
	// searches a student's username and password to see if it matches with one on file
	// if either username or password doesn't match, will return not found
	// used to check log-in information
	public int searchUsPa(String uname, String pword) {
		for (int i = 0; i < list.size(); i++) {
			if (uname.equals(list.get(i).getUsername())) {
				if (pword.equals(list.get(i).getPassword())) {
					return i;
				}
			}
		}
		return -1;
	}
}

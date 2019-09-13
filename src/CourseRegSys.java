import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class CourseRegSys {

	public static void main(String[] args) {
		// declare variables
		String username, password, cName, id, instructor, loc, fname, lname;
		int max, section, curr;		
		Course c;
		Student s;
		
		// create scanner object
		Scanner scan = new Scanner(System.in);
		
		// create list of courses and list of students and storage to store both lists
		CourseList courses = new CourseList(); 
		StudentsList students = new StudentsList(); 
		Storage stor = new Storage(courses, students);
		
		// Create Admin log-in information 
		Admin admin = new Admin("Admin", "01", "Admin", "Admin001");
		ArrayList<Student> studentUser = new ArrayList<>(); // to temporarily hold information for user so they won't have to be prompted to enter their information again
		
		// open ser file for storage 
		String serFile = "UniversityData.ser";
		File sfile = new File(serFile);
		
		// if serialized file does not exist (first time running program), will read input from .csv file
		if (!sfile.exists()) {
			String fileName = "MyUniversityCourses.csv";
			try {
				File inFile = new File(fileName);
				Scanner input = new Scanner(inFile);
				
				while (input.hasNextLine()) {
					// read line
					String line = input.nextLine();
					
					// will skip the first line which gives the names of each column
					if (!line.equals("Course_Name,Course_Id,Maximum_Students,Current_Students,List_Of_Names,Course_Instructor,Course_Section_Number,Course_Location")) {
					
						// split each input delimited by a comma and assign to variables
						String[] tempCourse = line.split(",");
						cName = tempCourse[0];
						id = tempCourse[1];
						max = Integer.parseInt(tempCourse[2]); 
						curr = Integer.parseInt(tempCourse[3]);
						instructor = tempCourse[5];
						section = Integer.parseInt(tempCourse[6]);
						loc = tempCourse[7];
						
						// create course with parameters and add course to master course list
						c = new Course(cName, id, max, curr, instructor, section, loc);
						courses.addCourse(c);
					}
				}
				input.close();
			}
			catch (FileNotFoundException e) {
				System.out.println( "Unable to open file '" + fileName + "'");
				e.printStackTrace();
			}
		} // end reading of .csv file
		
		// serialized file does exist (not first time running program), will read input from serialized file
		else {
			try {
				FileInputStream fis = new FileInputStream(serFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				// read storage object and assigns courselist and studentslist to variables
				stor = (Storage)ois.readObject();
				courses = stor.getCourseList();
				students = stor.getStudentsList();
					
				ois.close();
				fis.close();
			}
			catch (IOException e) {
			    e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} // end reading of .ser file

		// User sign-in
		System.out.println("Are you a student or an admin? (Enter 0 for admin and 1 for student) "); 
		int user = scan.nextInt();
		
		switch (user) {
		case 0: // admin
			while (true) {
				System.out.println("Username: ");
				username = scan.next();
				System.out.println("Password: ");
				password = scan.next();
				
				// username and password match
				if (admin.logInCheck(username, password)) {
					break;
				}
				else { 
					System.out.println("Either the username or the password is incorrect. Please try again.");
					continue;
				}
			}
			break;
			
		case 1: // student
			while (true) {
				System.out.println("Username: ");
				username = scan.next();
				System.out.println("Password: ");
				password = scan.next();
				
				// if student is not found (username and password don't match)
				if (students.searchUsPa(username, password) == -1) {
					System.out.println("Either the username or the password is incorrect. (Make sure your admin has registered you as a student). Try again.");
					continue;
				}
				else { // student username and password both match 
					studentUser.add(students.get(students.searchUsPa(username, password)));
					break;
				}
			}
			break;
		default: 
			System.out.println("Invalid input. Exiting...");
		} // end user log-in
		
		// admin
		if (user == 0) {
			do {
				System.out.println("What would you like to do?");
				System.out.println("1. Manage courses");
				System.out.println("2. View reports");
				System.out.println("3. Exit");
				int menu = scan.nextInt();

				if (menu == 1) { // course management
					do {
						admin.displayMenu();
						int selection = scan.nextInt();
						
						switch (selection) {
						case 1: // create a new course //
							// prompt user for information regarding new course
							System.out.println("Enter Course Name: ");
							String hold = scan.nextLine(); // .nextLine() seems to skip the next input each time, putting a hold here seems to fix it
							cName = scan.nextLine(); // using .nextLine() instead of .next() because some String inputs have spaces, but cause a problem with .nextLine() where it skips the next statement that asks user for inputut
							System.out.println("Enter Course ID (XXXX-XX.XXXX or XXXXX-XX.XXXX): ");
							id = scan.nextLine();
							System.out.println("Enter an integer for the maximum number of students allowed to register: ");
							max = scan.nextInt();
							System.out.println("Enter Course Instructor Name: ");
							hold = scan.nextLine();
							instructor = scan.nextLine();
							System.out.println("Enter Course Section Number: ");
							section = scan.nextInt();
							System.out.println("Enter Course Location: ");
							hold = scan.nextLine();
							loc = scan.nextLine();
							
							// create new course with given parameters
							c = new Course(cName, id, max, 0, instructor, section, loc);
							// add course to list of courses
							courses.addCourse(c);
							System.out.println("Successfully created and added to system");
							
							break;			
						case 2: // delete a course //
							// prompts user for information regarding course
							System.out.println("Enter the Course Name: ");
							hold = scan.nextLine();
							cName = scan.nextLine();
							System.out.println("Enter the Course Section Number: ");
							section = scan.nextInt();
							
							try {
								// finds the course using Course Name and Section Number
								c = courses.get(courses.search(cName, section));
								// removes course from list of courses
								courses.removeCourse(c);
								System.out.println("Successfully removed");
							}
							catch (IndexOutOfBoundsException e) {
								System.out.println("Course not found. Please enter the exact Name and Section Number of the course.");
							}
							
							break;	
						case 3: // edit a course //
							// prompts user for information regarding course
							System.out.println("Enter the Course Name for the course you are trying to change: ");
							hold = scan.nextLine();
							cName = scan.nextLine();
							System.out.println("Enter the Course Section Number for the course you are trying to change: ");
							section = scan.nextInt();
							
							try {
								// finds the course
								c = courses.get(courses.search(cName, section));
														
								// declare input variable
								int input;
		
								do {
									// prompts user for information they want to change
									System.out.println("What would you like to change? Enter: ");
									System.out.println("1 for maximum capacity of students");
									System.out.println("2 to add a student to a course");
									System.out.println("3 to remove a student from a course");
									System.out.println("4 to change instructor name");
									System.out.println("5 to change section number");
									System.out.println("6 to change course location");
									System.out.println("7 if you are finished");
									
									input = scan.nextInt();
									
									switch (input) {
									case 1: // change max students
										System.out.println("Enter the new maximum capacity of students");	
										max = scan.nextInt();
										if (max < c.getCurrStudents()) {
											System.out.println("Cannot set capacity to less than the number of students already registered in the course");
										}
										else {
											c.setMaxStudents(max);
											System.out.println("Successfully changed");
										}
										
										break;
									case 2: // add student to a course
										System.out.println("Enter new student's first name");
										fname = scan.next();
										System.out.println("Enter new student's last name");
										lname = scan.next();
										
										try {
											s = students.get(students.search(fname, lname));
											
											// if student is not currently registered in the course and course is not full
											if ((c.getCurrStudents() != c.getMaxStudents()) && !(s.searchCourse(cName, section))) {									
												// add student to course's list of students
												c.addStudent(fname, lname);
												// increase current number of students in course
												c.incCurrStudents();							
												// add course to student's list of courses										
												s.addCourse(cName, section);
													
												System.out.println("Successfully added");
												}
											else {
												System.out.println("ACTION INCOMPLETE: Course is full or student is already in the course.");
											}
										}
										catch (IndexOutOfBoundsException e) {
											System.out.println("Student not found.");
										}
										
										break;
									case 3: // remove student from a course
										System.out.println("Enter new student's first name");
										fname = scan.next();
										System.out.println("Enter new student's last name");
										lname = scan.next();
										
										// make sure student exists
										try {
											s = students.get(students.search(fname, lname));
										
											// if student is registered in the course
											if (s.searchCourse(cName, section)) {
												// remove student from course's list of students
												c.removeStudent(fname, lname);
												// decrease current number of students in course
												c.decCurrStudents();
												// remove course from student's list of courses
												s.removeCourse(cName, section);
												
												System.out.println("Successfully removed");
											}		
											else {
												System.out.println("Student is not currently registered in the course.");
											}
										}
										catch (IndexOutOfBoundsException e) {
											System.out.println("Student not found");
										}
			
										break;
									case 4: // change instructor name
										System.out.println("Enter new instructor name");
										hold = scan.nextLine();
										instructor = scan.nextLine();
										
										c.setInstructorName(instructor);
										System.out.println("Successfully changed");
										
										break;
									case 5: // change section number
										System.out.println("Enter new section number");
										section = scan.nextInt();
										
										c.setSectionNum(section);
										System.out.println("Successfully changed");
										
										break;
									case 6: // change course location
										System.out.println("Enter new course location");
										hold = scan.nextLine();
										loc = scan.nextLine();
										
										c.setCourseLocation(loc);
										System.out.println("Successfully changed");
										
										break;
									case 7: // finished
										input = 7;
										break;
									default: System.out.println("Invalid Selection. Please select a number from 1 to 7.");
										break;
									}
								} while (input != 7);
							}
							catch (IndexOutOfBoundsException e) {
								System.out.println("Course not found. Please enter the exact Name and Section Number of the course.");
							}
		
							break;
						case 4: // display information for given course //
							// prompts user for information
							System.out.println("Enter the Course ID of the Course you would like to view: ");
							hold = scan.nextLine();
							id = scan.nextLine();
							
							// finds the course
							try {
								c = courses.get(courses.search(id));
								// displays course information
								c.view();
							}
							catch (IndexOutOfBoundsException e) {
								System.out.println("Course not found. Please enter the exact ID of the course.");
							}
							
							break;
						case 5: // register a student
							int input;
							do {
								// prompts user for information regarding student
								System.out.println("Enter the Student's First Name: ");
								hold = scan.nextLine();
								fname = scan.nextLine();
								System.out.println("Enter the Student's Last Name: ");
								lname = scan.nextLine();
								
								// if student is found
								if (students.search(fname, lname) != -1) {
									System.out.println("Error: Student is already in the system.");
								}
								else {
									System.out.println("Set the username: ");
									username = scan.nextLine();
									
									// if username is already used
									if (students.search(username) != -1) {
										System.out.println("There is already a student with that username");
									}
									else {
										System.out.println("Set the password: ");
										password = scan.nextLine();
										
										// create new student object
										s = new Student(fname, lname, username, password);
										// add student to list of students
										students.addStudent(s);
										System.out.println("Successfully registered");
									}
								}
								
								// asks if user would like to register another student
								do {
									System.out.println("Would you like to register another student? Yes (1) or No (0)");
									input = scan.nextInt();
									
									if (input == 0) {
										break;
									}
									else if (input == 1) {
										break;
									}
									else {
									System.out.println("Invalid input. Please enter a (1) for yes or a (0) for no");
									}
								} while (true);
								
							} while (input == 1);
							
							break;
						case 6: // back to main menu
							menu = 0;
							break;
						case 7: // exit
							user = 2;
							break;
						default: 
							System.out.println("Invalid Selection. Please select an integer from 1 to 7.");
							break;
						} 
					} while (menu == 1 && user != 2); // break out of loop
				} // end course management
				
				else if (menu == 2) { // reports
					do {
						admin.displayMenu2();
						int selection = scan.nextInt();
						
						switch (selection) {
						case 1: // view all courses 
							admin.viewCourse(courses, students);				
							break;				
						case 2: // view all courses that are full
							System.out.println("All Full Courses: ");
							admin.viewCourseFull(courses, students);					
							break;					
						case 3: // write to a file all full courses
							ArrayList<Course> courseFull = courses.getCourseFull();
							
							try {
								FileWriter fileWriter = new FileWriter("CoursesFull.txt");
								BufferedWriter writer = new BufferedWriter(fileWriter);
								
								for (int i = 0; i < courseFull.size(); i++) {
									c = courseFull.get(i);
									writer.write(c.getCourseName() + " ");
									writer.write(c.getCourseID() + " ");
									writer.write(c.getCurrStudents() + "/");
									writer.write(c.getMaxStudents() + " ");			
									writer.write(c.getInstructorName() + " ");
									writer.write(c.getSectionNum() + " ");
									writer.write(c.getCourseLocation() + "\n");
								}
								writer.close();
								System.out.println("Successfully exported");
							}
							catch (IOException e) {
								System.out.println("Error writing file 'CoursesFull.txt'");
								e.printStackTrace();
							}
							
							break;					
						case 4: // view names of students registered to a specific course
							// prompts user for information
							System.out.println("Enter the Course Name: ");
							String hold = scan.nextLine();
							cName = scan.nextLine();
							System.out.println("Enter the Course Section Number: ");
							section = scan.nextInt();
							
							try {
								// finds the course
								c = courses.get(courses.search(cName, section));
								
								System.out.println("Students registered in " + cName + " Sec " + section + ": ");
								for (int i = 0; i < c.getSize(); i++) {
									System.out.println(c.get(i));
								}
							}
							catch (IndexOutOfBoundsException e) {
								System.out.println("Course not found. Please enter the exact Name and Section Number of the course.");
							}
							
							break;					
						case 5: // view all courses a student is registered in
							// prompts user for information
							System.out.println("Enter Student's First Name: ");
							hold = scan.nextLine();
							fname = scan.nextLine();
							System.out.println("Enter Student's Last Name: ");
							lname = scan.nextLine();
							
							try {
								// finds the student
								s = students.get(students.search(fname, lname));
								
								System.out.println("All Registered Courses for " + fname + " " + lname + ": ");
								s.displayRegCourses();
							}
							catch (IndexOutOfBoundsException e) {
								System.out.println("Student not found.");
							}
							
							break;
						case 6: // sort courses based on current number of students registered
							courses.sortCourses();
							
							System.out.println("Sorting complete");
							break;
						case 7: // back to main menu
							menu = 0;
							break;
						case 8: // exit
							user = 2;
							break; 
						default: 
							System.out.println("Invalid Selection. Please select an integer from 1 to 8.");
							break;
						} // end switch
					} while (menu == 2 && user != 2); // break out of loop
				} // end reports menu
				else if (menu == 3) { // exit
					user = 2;
				}
				else {
					System.out.println("Invalid input. Please enter a (1) for Course Management, a (2) for Reports, or a (3) to exit");
					continue;
				}
			} while (user != 2); // end both menus
		} // end admin
		
		// student
		if (user == 1) {
			s = studentUser.get(0);
			fname = s.getFirstName();
			lname = s.getLastName();
			System.out.println("Welcome " + fname + " " + lname + "!");
		
			do {
				s.displayMenu();
				int selection = scan.nextInt();
				
				switch (selection) {
				case 1: // view all available courses
					s.viewCourse(courses);
					break;
				case 2: // view all courses that are not full
					System.out.println("All available courses: ");
					s.viewCourseNotFull(courses);
					break;
				case 3: // register a course
					System.out.println("Please enter the Course Name: ");
					String hold = scan.nextLine();
					cName = scan.nextLine();
					System.out.println("Please enter the Course Section Number: ");				
					section = scan.nextInt();
					
					try {
						// find the course, will give IndexOutOfBoundsException if course is not found
						c = courses.get(courses.search(cName, section));
							
						// if student is not currently registered in the course and course is not full
						if ((c.getCurrStudents() != c.getMaxStudents()) && !(s.searchCourse(cName, section))) {								
							// add course to student's list of courses						
							s.addCourse(cName, section);
							// add student to course's list of students							
							c.addStudent(fname, lname);
							// inc current number of students registered in course
							c.incCurrStudents();
							
							System.out.println("Successfully registered");
						}
						else { 								
							System.out.println("INCOMPLETE ACTION: Course is full or you are already registered in the course.");
						}
					}
					catch (IndexOutOfBoundsException e) {
						System.out.println("Error: Course not found.");
					}
						
					break;
				case 4: // withdraw from a course 
					System.out.println("Please enter the Course Name: ");
					hold = scan.nextLine();
					cName = scan.nextLine();
					System.out.println("Please enter the Course Section Number: ");
					section = scan.nextInt();
						
					try {
						// find the course
						c = courses.get(courses.search(cName, section));
						
						// if student is registered in the course
						if (s.searchCourse(cName, section)) {
							// remove course from student's list of courses
							s.removeCourse(cName, section);
							// remove student from course's list of students
							c.removeStudent(fname, lname);
							// decrease current number of students registered in course
							c.decCurrStudents();
							
							System.out.println("Successfully withdrawn");
						}
						else {
							System.out.println("You are not currently registered in this course.");
						}
					}
					catch (IndexOutOfBoundsException e) {
						System.out.println("Error: Course not found.");
					}
						
					break;
				case 5: // view all currently registered course(s)					
					System.out.println("Your courses: ");
		
					// display all courses for this student
					for (int i = 0; i < s.getSize(); i ++) {
						System.out.println(s.get(i));
					}
					
					break;
				case 6: // exit 
					user = 2;
					break;
				default: 
					System.out.println("Invalid Selection. Please select a number from 1 to 6.");
				} // end switch
			} while (user != 2); // break out of loop

		} // end student

		// write final versions of students list (students) and courses list (courses) to a serialized file
		try {
			FileOutputStream fos = new FileOutputStream(serFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// write storage object to .ser file
			oos.writeObject(stor);
			
			oos.close();
			fos.close();
			
			System.out.println("Serialization Complete");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// close the scanner
		scan.close();
		
	} // end main

} // end class

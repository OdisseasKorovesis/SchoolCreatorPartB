package privateschoolassignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import static privateschoolassignment.Assignment.clearListOfDoubleAssignments;
import static privateschoolassignment.Course.clearListOfDoubleCourses;
import static privateschoolassignment.PrivateSchoolAssignment.lists;

public class Student {

    private static Scanner sc;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private double tuitionFees;

    private ArrayList<Course> studentEnrolledCourses = new ArrayList();
    private ArrayList<Assignment> studentUndertakenAssignments = new ArrayList();

    public Student() {

    }

    public Student(String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    public ArrayList<Course> getStudentEnrolledCourses() {
        return studentEnrolledCourses;
    }

    public ArrayList<Assignment> getStudentUndertakenAssignments() {
        return studentUndertakenAssignments;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return firstName + " " + lastName + ", date of birth is " + dateOfBirth.format(formatter) + ", fees are " + tuitionFees;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.firstName);
        hash = 47 * hash + Objects.hashCode(this.lastName);
        hash = 47 * hash + Objects.hashCode(this.dateOfBirth);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        return true;
    }

    public static void createSyntheticStudents() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfBirth = LocalDate.parse("17/07/1989", dtf);
        Student student1 = new Student("Tina", "Danon", dateOfBirth, 1000);
        Database.insertIntoStudentsSingle(student1);
        lists.getListOfStudents().add(student1);
        lists.getListOfCourses().get(0).getCourselistOfStudents().add(student1);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(0));
        student1.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(0).getCourselistOfAssignments().get(0));
        student1.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(0).getCourselistOfAssignments().get(1));
        lists.getListOfCourses().get(1).getCourselistOfStudents().add(student1);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(1));
        student1.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(0));
        student1.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(1));
        lists.getListOfMultipleCourseStudents().add(student1);        

        dateOfBirth = LocalDate.parse("15/02/1990", dtf);
        Student student2 = new Student("Dimitris", "Korovesis", dateOfBirth, 1500);
        Database.insertIntoStudentsSingle(student2);
        lists.getListOfStudents().add(student2);
        lists.getListOfCourses().get(1).getCourselistOfStudents().add(student2);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(1));
        student2.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(0));
        student2.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(1));
        lists.getListOfCourses().get(2).getCourselistOfStudents().add(student2);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(2));
        student2.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(2).getCourselistOfAssignments().get(0));
        student2.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(2).getCourselistOfAssignments().get(1));
        lists.getListOfMultipleCourseStudents().add(student2);

        dateOfBirth = LocalDate.parse("31/07/1995", dtf);
        Student student3 = new Student("Odisseas", "Korovesis", dateOfBirth, 500);
        Database.insertIntoStudentsSingle(student3);
        lists.getListOfStudents().add(student3);
        lists.getListOfCourses().get(2).getCourselistOfStudents().add(student3);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(2));
        student3.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(2).getCourselistOfAssignments().get(0));
        student3.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(2).getCourselistOfAssignments().get(1));
        lists.getListOfCourses().get(3).getCourselistOfStudents().add(student3);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(3));
        student3.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(3).getCourselistOfAssignments().get(0));
        student3.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(3).getCourselistOfAssignments().get(1));
        lists.getListOfMultipleCourseStudents().add(student3);

        dateOfBirth = LocalDate.parse("23/05/1994", dtf);
        Student student4 = new Student("Anastasia", "Kounoupi", dateOfBirth, 500);
        Database.insertIntoStudentsSingle(student4);
        lists.getListOfStudents().add(student4);
        lists.getListOfCourses().get(0).getCourselistOfStudents().add(student4);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(0));
        student4.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(0).getCourselistOfAssignments().get(0));
        student4.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(0).getCourselistOfAssignments().get(1));
        lists.getListOfCourses().get(1).getCourselistOfStudents().add(student4);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(1));
        student4.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(0));
        student4.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(1));
        lists.getListOfMultipleCourseStudents().add(student4);

        dateOfBirth = LocalDate.parse("29/05/1995", dtf);
        Student student5 = new Student("Konstantina", "Sinevrioti", dateOfBirth, 500);
        Database.insertIntoStudentsSingle(student5);
        lists.getListOfStudents().add(student5);
        lists.getListOfCourses().get(0).getCourselistOfStudents().add(student5);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(0));
        student5.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(0).getCourselistOfAssignments().get(0));
        student5.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(0).getCourselistOfAssignments().get(1));

        dateOfBirth = LocalDate.parse("11/09/1992", dtf);
        Student student6 = new Student("Konstantinos", "Oikonomidis", dateOfBirth, 800);
        Database.insertIntoStudentsSingle(student6);
        lists.getListOfStudents().add(student6);
        lists.getListOfCourses().get(1).getCourselistOfStudents().add(student6);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(1));
        student6.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(0));
        student6.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(1).getCourselistOfAssignments().get(1));

        dateOfBirth = LocalDate.parse("15/02/1992", dtf);
        Student student7 = new Student("Alkistis", "Bageri", dateOfBirth, 800);
        Database.insertIntoStudentsSingle(student7);
        lists.getListOfStudents().add(student7);
        lists.getListOfCourses().get(2).getCourselistOfStudents().add(student7);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(2));
        student7.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(2).getCourselistOfAssignments().get(0));
        student7.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(2).getCourselistOfAssignments().get(1));

        dateOfBirth = LocalDate.parse("20/09/1993", dtf);
        Student student8 = new Student("Lucia", "Binioli", dateOfBirth, 700);
        Database.insertIntoStudentsSingle(student8);
        lists.getListOfStudents().add(student8);
        lists.getListOfCourses().get(3).getCourselistOfStudents().add(student8);
        Database.insertIntoCourseStudents(lists.getListOfCourses().get(3));
        student8.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(3).getCourselistOfAssignments().get(0));
        student8.getStudentUndertakenAssignments().add(lists.getListOfCourses().get(3).getCourselistOfAssignments().get(1));

        
    }

    public static Student createStudent() {
        sc = new Scanner(System.in);
        System.out.println("What is the first name of the student?");
        String firstName = sc.nextLine();
        System.out.println("What is the last name of the student?");
        String lastName = sc.nextLine();

        System.out.println("What is the birth date of this student? (dd/mm/yyyy)");

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean flag = true;
        String dateOfBirthInput;
        LocalDate dateOfBirth = null;

        while (flag) {

            dateOfBirthInput = sc.nextLine();
            try {
                dateOfBirth = LocalDate.parse(dateOfBirthInput, formatter);
                boolean secondFlag = true;
                while (secondFlag) {
                    if (dateOfBirth.isAfter(today)) {
                        System.out.println("Birth date must be in the past and in the correct format (dd/mm/yyyy).");
                        dateOfBirthInput = sc.nextLine();
                        dateOfBirth = LocalDate.parse(dateOfBirthInput, formatter);
                    } else if (dateOfBirth.isBefore(today.minusYears(100))) {
                        System.out.println("Birth date can't be that far back and has to be in the correct format (dd/mm/yyyy).");
                        dateOfBirthInput = sc.nextLine();
                        dateOfBirth = LocalDate.parse(dateOfBirthInput, formatter);
                    } else {
                        secondFlag = false;
                    }
                }
                flag = false;
            } catch (DateTimeParseException ex) {
                System.out.println("Please enter a date in the correct format: (dd/mm/yyyy)");
            }
        }

        int tuitionFees;
        do {
            System.out.println("What are the tuition fees paid by the student? (0-5000)");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number.");
                sc.nextLine();
            }
            tuitionFees = sc.nextInt();
        } while (tuitionFees < 0 || tuitionFees > 5000);

        Student newStudent = new Student(firstName, lastName, dateOfBirth, tuitionFees);
        return newStudent;

    }

    public static void addStudent(Student student) {
        sc = new Scanner(System.in);
        boolean flag = true;

        lists.getListOfStudents().add(student);
        if(Database.insertIntoStudentsSingle(student)) {;
        //εδώ πρέπει να μπει η μέθοδος που προσθέτει τον μαθητή στον πίνακά του
        int counter = 1;
        System.out.println("Which courses will this student follow?");
        for (Course course : lists.getListOfCourses()) {
            System.out.println(counter + ". " + course.getName());
            counter++;
            System.out.println("Type Y to enroll or any key to view next course.");
            String choice = sc.nextLine();
            if (choice.equals("Y")) {
                student.getStudentEnrolledCourses().add(course);
                course.getCourselistOfStudents().add(student);                
                Database.insertIntoCourseStudents(course);
                
                for (Assignment assignment : course.getCourselistOfAssignments()) {
                    student.getStudentUndertakenAssignments().add(assignment);
                }
            }
            if (student.getStudentEnrolledCourses().size() > 1
                && !lists.getListOfMultipleCourseStudents().contains(student)) {
                lists.getListOfMultipleCourseStudents().add(student);
            }
        }
        } else {
            System.out.println("Duplicate entry, was not created.");
        }

        ArrayList<Student> tempList = new ArrayList(lists.getListOfStudents());
        tempList = clearListOfDoubleStudents(tempList);
        lists.getListOfStudents().clear();
        lists.getListOfStudents().addAll(tempList);

        for (Course course1 : lists.getListOfCourses()) {
            tempList = new ArrayList(course1.getCourselistOfStudents());
            tempList = clearListOfDoubleStudents(tempList);
            course1.getCourselistOfStudents().clear();
            course1.getCourselistOfStudents().addAll(tempList);
        }

    }

    public static void printStudentsWithCourses() {
        System.out.println("Below is a list of the students along with the courses they follow:");
        int counter = 1;
        for (Student student : lists.getListOfStudents()) {
            System.out.println(counter + ". " + student.getFirstName() + " " + student.getLastName());
            counter++;
            for (Course course : student.getStudentEnrolledCourses()) {
                System.out.println(course.getName());
            }
        }
    }

    public static ArrayList<Student> clearListOfDoubleStudents(ArrayList<Student> list) {
        HashSet<Student> set = new HashSet(list);
        ArrayList<Student> clearedList = new ArrayList(set);
        clearedList.sort(Comparator.comparing(Student::getLastName));
        return clearedList;
    }

}

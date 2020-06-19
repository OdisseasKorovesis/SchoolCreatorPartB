package privateschoolassignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import static privateschoolassignment.PrivateSchoolAssignment.lists;

public class Course {

    private static Scanner sc;

    private String title;
    private String stream;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;

    private ArrayList<Trainer> courselistOfTrainers = new ArrayList();
    private ArrayList<Student> courselistOfStudents = new ArrayList();
    private ArrayList<Assignment> courselistOfAssignments = new ArrayList();

    public Course() {

    }

    public Course(String title, String stream, String type, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return this.title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStream() {
        return stream;
    }    

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
    
    

    public ArrayList<Assignment> getCourselistOfAssignments() {
        return courselistOfAssignments;
    }

    public ArrayList<Student> getCourselistOfStudents() {
        return courselistOfStudents;
    }

    public ArrayList<Trainer> getCourselistOfTrainers() {
        return courselistOfTrainers;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return title + ", " + stream + ", " + type + ", starts at " + startDate.format(formatter) + ", ends at " + endDate.format(formatter);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.title);
        hash = 67 * hash + Objects.hashCode(this.stream);
        hash = 67 * hash + Objects.hashCode(this.type);
        hash = 67 * hash + Objects.hashCode(this.startDate);
        hash = 67 * hash + Objects.hashCode(this.endDate);
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.stream, other.stream)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        return true;
    }

    public static void createSyntheticCourses() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate1 = LocalDate.parse("15/06/2020", dtf);
        LocalDate startDate2 = LocalDate.parse("15/09/2020", dtf);
        Course course1 = new Course("Java", "Full-Time", "Back-End", startDate1, startDate2);
        lists.getListOfCourses().add(course1);
        startDate1 = LocalDate.parse("15/06/2020", dtf);
        startDate2 = LocalDate.parse("15/12/2020", dtf);
        Course course2 = new Course("Java", "Part-Time", "Back-End", startDate1, startDate2);
        lists.getListOfCourses().add(course2);
        startDate1 = LocalDate.parse("15/06/2020", dtf);
        startDate2 = LocalDate.parse("15/09/2020", dtf);
        Course course3 = new Course("C#", "Full-Time", "Back-End", startDate1, startDate2);
        lists.getListOfCourses().add(course3);
        startDate1 = LocalDate.parse("20/06/2020", dtf);
        startDate2 = LocalDate.parse("20/10/2020", dtf);
        Course course4 = new Course("HTML", "Full-Time", "Front-End", startDate1, startDate2);
        lists.getListOfCourses().add(course4);
        
        Database.insertIntoCourses();
    }

    public static Course createCourse() {
        sc = new Scanner(System.in);
        System.out.println("What is the name of the course?");
        String title = sc.nextLine();
        System.out.println("What is the stream of the course?");
        String stream = sc.nextLine();
        System.out.println("What is the type of the course?");
        String type = sc.nextLine();

        System.out.println("What is the start date of the course? (dd/mm/yyyy)");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean flag = true;
        String startDateInput;
        LocalDate startDate = null;

        while (flag) {

            startDateInput = sc.nextLine();
            try {
                startDate = LocalDate.parse(startDateInput, formatter);
                boolean secondFlag = true;
                while (secondFlag) {
                    if (startDate.isBefore(today)) {
                        System.out.println("Start date must be in the future and in the correct format (dd/mm/yyyy).");
                        startDateInput = sc.nextLine();
                        startDate = LocalDate.parse(startDateInput, formatter);
                    } else {
                        
                        secondFlag = false;
                    }
                }
                flag = false;
            } catch (DateTimeParseException ex) {
                System.out.println("Please enter a date in the correct format: (dd/mm/yyyy)");
            }
        }

        

        System.out.println("What is the end date of the course? (dd/mm/yyyy)");
        flag = true;
        String endDateInput;
        LocalDate endDate = null;

        while (flag) {

            endDateInput = sc.nextLine();
            try {
                endDate = LocalDate.parse(endDateInput, formatter);
                boolean secondFlag = true;
                while (secondFlag) {
                    if (endDate.isBefore(startDate)) {
                        System.out.println("End date must be after start date and in the correct format (dd/mm/yyyy).");
                        endDateInput = sc.nextLine();
                        endDate = LocalDate.parse(endDateInput, formatter);
                    } else {
                        secondFlag = false;
                    }
                }
                flag = false;
            } catch (DateTimeParseException ex) {
                System.out.println("Please enter a date in the correct format: (dd/mm/yyyy)");
            }
        }        

        Course course = new Course(title, stream, type, startDate, endDate);
        return course;
    }

    public static void addCourses() {
        sc = new Scanner(System.in);
        String continues = "";
        while (!"N".equals(continues)) {
            lists.getListOfCourses().add(Course.createCourse());
            System.out.println("Do you want to create another course? If no type N, else press any key...");
            continues = sc.nextLine();
        }
        ArrayList<Course> tempList = new ArrayList(lists.getListOfCourses());
        tempList = clearListOfDoubleCourses(tempList);
        lists.getListOfCourses().clear();
        lists.getListOfCourses().addAll(tempList);
        Database.insertIntoCourses();
    }

    public static void printCourses() {
        System.out.println("Below is a list of the courses created, sorted alphabetically:");
        int counter = 1;
        for (Course course : lists.getListOfCourses()) {

            System.out.println(counter + ". " + course.getName());
            counter++;
        }
    }

    public static ArrayList<Course> clearListOfDoubleCourses(ArrayList<Course> list) {
        HashSet<Course> set = new HashSet(list);
        ArrayList<Course> clearedList = new ArrayList(set);
        clearedList.sort(Comparator.comparing(Course::getName));
        return clearedList;
    }

}

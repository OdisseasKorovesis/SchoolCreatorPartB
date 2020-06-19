package privateschoolassignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import static privateschoolassignment.Course.clearListOfDoubleCourses;
import static privateschoolassignment.PrivateSchoolAssignment.lists;

public class Assignment {

    private static Scanner sc;

    private String title;
    private String description;
    private LocalDate subDateTime;
    private double totalMark;

    public Assignment() {
    }

    public Assignment(String title, String description, LocalDate subDateTime, double totalMark) {
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.totalMark = totalMark;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getSubDateTime() {
        return subDateTime;
    }

    public String getDescription() {
        return description;
    }

    public double getTotalMark() {
        return totalMark;
    }
    
    

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return title + ", " + description + ", to be submitted until " + subDateTime.format(formatter) + ", total possible mark " + totalMark;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.subDateTime);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.totalMark) ^ (Double.doubleToLongBits(this.totalMark) >>> 32));
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
        final Assignment other = (Assignment) obj;
        if (Double.doubleToLongBits(this.totalMark) != Double.doubleToLongBits(other.totalMark)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.subDateTime, other.subDateTime)) {
            return false;
        }
        return true;
    }

    public static void createSyntheticAssignments() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate subdate = LocalDate.parse("22/06/2020", dtf);
        Assignment assignment1 = new Assignment("PlayWithClasses", "Hard", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment1);
        lists.getListOfAssignments().add(assignment1);
        lists.getListOfCourses().get(0).getCourselistOfAssignments().add(assignment1);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(0));
        subdate = LocalDate.parse("20/08/2020", dtf);
        Assignment assignment2 = new Assignment("PlayWithMethods", "Easy", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment2);
        lists.getListOfAssignments().add(assignment2);
        lists.getListOfCourses().get(0).getCourselistOfAssignments().add(assignment2);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(0));

        subdate = LocalDate.parse("20/08/2020", dtf);
        Assignment assignment3 = new Assignment("PlayWithInheritance", "Hard", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment3);
        lists.getListOfAssignments().add(assignment3);
        lists.getListOfCourses().get(1).getCourselistOfAssignments().add(assignment3);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(1));
        subdate = LocalDate.parse("20/10/2020", dtf);
        Assignment assignment4 = new Assignment("PlayWithPrimitives", "Easy", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment4);
        lists.getListOfAssignments().add(assignment4);
        lists.getListOfCourses().get(1).getCourselistOfAssignments().add(assignment4);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(1));

        subdate = LocalDate.parse("20/07/2020", dtf);
        Assignment assignment5 = new Assignment("PlayWithInterfaces", "Hard", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment5);
        lists.getListOfAssignments().add(assignment5);
        lists.getListOfCourses().get(2).getCourselistOfAssignments().add(assignment5);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(2));
        subdate = LocalDate.parse("20/08/2020", dtf);
        Assignment assignment6 = new Assignment("PlayWithExceptions", "Easy", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment6);
        lists.getListOfAssignments().add(assignment6);
        lists.getListOfCourses().get(2).getCourselistOfAssignments().add(assignment6);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(2));

        subdate = LocalDate.parse("25/07/2020", dtf);
        Assignment assignment7 = new Assignment("PlayWithAppearance", "Hard", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment7);        
        lists.getListOfAssignments().add(assignment7);
        lists.getListOfCourses().get(3).getCourselistOfAssignments().add(assignment7);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(3));
        subdate = LocalDate.parse("25/09/2020", dtf);
        Assignment assignment8 = new Assignment("PlayWithWidgets", "Easy", subdate, 100);
        Database.insertIntoAssignmentsSingle(assignment8);
        lists.getListOfAssignments().add(assignment8);
        lists.getListOfCourses().get(3).getCourselistOfAssignments().add(assignment8);
        Database.insertIntoCourseAssignments(lists.getListOfCourses().get(3));     

    }

    public static Assignment createAssignment() {
        sc = new Scanner(System.in);
        System.out.println("What is the name of the assignment?");
        String title = sc.nextLine();
        System.out.println("What is the description of the assignment?");
        String description = sc.nextLine();
        System.out.println("What is the submission date of the assignment? (dd/mm/yyyy)");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String subDateInput;
        LocalDate subDateTime = null;
        while (true) {

            subDateInput = sc.nextLine();
            try {
                subDateTime = LocalDate.parse(subDateInput, formatter);
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Please enter a date in the correct format: (dd/mm/yyyy)");
            }
        }

        while (true) {
            DayOfWeek myDayOfWeek = subDateTime.getDayOfWeek();
            LocalDate firstDateOfWeek = subDateTime;
            while (firstDateOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
                firstDateOfWeek = firstDateOfWeek.minusDays(1);
            }
            LocalDate lastDateOfWeek = firstDateOfWeek.plusDays(4);

            if (subDateTime.isAfter(lastDateOfWeek.plusDays(1)) || subDateTime.isBefore(firstDateOfWeek.minusDays(1))
                    || subDateTime.equals(lastDateOfWeek.plusDays(1)) || subDateTime.isBefore(firstDateOfWeek.minusDays(1))) {
                System.out.println(subDateTime.format(formatter) + "is in a weekend. "
                        + "Assignments must be submitted on a weekday. Enter another date.");
                while (true) {
                    subDateInput = sc.nextLine();
                    try {
                        subDateTime = LocalDate.parse(subDateInput, formatter);
                        break;
                    } catch (DateTimeParseException ex) {
                        System.out.println("Please enter a date in the correct format: (dd/mm/yyyy)");
                    }
                }
            } else {
                break;
            }

        }

        int totalMark;
        do {
            System.out.println("What is the total mark of the assignment (0-100)?");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number.");
                sc.nextLine();
            }
            totalMark = sc.nextInt();
        } while (totalMark < 0 || totalMark > 100);
        
        Assignment newAssignment = new Assignment(title, description, subDateTime, totalMark);
        return newAssignment;

    }

    public static void addAssignments() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int counter = 1;
        for (Course course : lists.getListOfCourses()) {
            System.out.println(counter + ". " + course.getName());
            System.out.println("Course starts: " + course.getStartDate().format(formatter) + " and ends: " + course.getEndDate().format(formatter));
            System.out.println("Assignments must be within these dates and on a weekday.");
            counter++;
            boolean flag = true;

            String continues = "";
            while (!"N".equals(continues)) {
                Assignment assignment = Assignment.createAssignment();

                boolean secondFlag = true;
                while (secondFlag) {
                    if (assignment.getSubDateTime().isBefore(course.getStartDate()) || assignment.getSubDateTime().isAfter(course.getEndDate())) {
                        System.out.println("Submission date must be between the start and end date of the specific course. Did not create assignment, try again.");
                        assignment.getSubDateTime();
                        secondFlag = false;
                    } else {
                        lists.getListOfAssignments().add(assignment);
                        Database.insertIntoAssignmentsSingle(assignment);
                        course.getCourselistOfAssignments().add(assignment);
                        Database.insertIntoCourseAssignments(course);
                        secondFlag = false;
                    }
                }
                System.out.println("Do you want to create another assignment for this course? If no type N, else press any key...");
                continues = sc.nextLine();
            }
            ArrayList<Assignment> tempList = new ArrayList(lists.getListOfAssignments());
            tempList = clearListOfDoubleAssignments(tempList);
            lists.getListOfAssignments().clear();
            lists.getListOfAssignments().addAll(tempList);
            
            for(Course course1 : lists.getListOfCourses()) {
            tempList = new ArrayList(course1.getCourselistOfAssignments());
            tempList = clearListOfDoubleAssignments(tempList);
            course1.getCourselistOfAssignments().clear();
            course1.getCourselistOfAssignments().addAll(tempList);
            }
            

        }
    }

    public static void printAssignmentsPerCourse() {
        System.out.println("Below is a list of the assignments per course:");
        int counter = 1;
        for (Course course : lists.getListOfCourses()) {

            System.out.println(counter + ". " + course.getName());
            counter++;
            for (Assignment assignment : course.getCourselistOfAssignments()) {
                System.out.println(assignment.getTitle());
            }
        }
    }

    public static ArrayList<Assignment> clearListOfDoubleAssignments(ArrayList<Assignment> list) {
        HashSet<Assignment> set = new HashSet(list);
        ArrayList<Assignment> clearedList = new ArrayList(set);
        clearedList.sort(Comparator.comparing(Assignment::getTitle));
        return clearedList;
    }

}

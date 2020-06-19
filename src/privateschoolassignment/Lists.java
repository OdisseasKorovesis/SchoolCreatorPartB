package privateschoolassignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import static privateschoolassignment.PrivateSchoolAssignment.lists;

public class Lists {

    private static Scanner sc;

    private ArrayList<Assignment> listOfAssignments = new ArrayList();
    private ArrayList<Course> listOfCourses = new ArrayList();
    private ArrayList<Student> listOfStudents = new ArrayList();
    private ArrayList<Trainer> listOfTrainers = new ArrayList();
    private ArrayList<Student> listOfMultipleCourseStudents = new ArrayList();

    public Lists() {
    }

    public ArrayList<Assignment> getListOfAssignments() {
        return listOfAssignments;
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    public ArrayList<Student> getListOfStudents() {
        return listOfStudents;
    }

    public ArrayList<Trainer> getListOfTrainers() {
        return listOfTrainers;
    }

    public ArrayList<Student> getListOfMultipleCourseStudents() {
        return listOfMultipleCourseStudents;
    }

    public void printListOfStudents() {
        for (Student student : listOfStudents) {
            System.out.println(student.toString());
        }
    }

    public void printListOfTrainers() {
        for (Trainer trainer : listOfTrainers) {
            System.out.println(trainer.toString());
        }
    }

    public void printListOfCourses() {
        for (Course course : listOfCourses) {
            System.out.println(course.toString());
        }
    }

    public void printListOfAssignments() {
        for (Assignment assignment : listOfAssignments) {
            System.out.println(assignment.toString());
        }
    }

    public void printListOfMultipleCourseStudents() {
        for (Student student : listOfMultipleCourseStudents) {
            System.out.println(student.toString());
        }
    }

    public void printStudentsWithAssignmentsWithinGivenWeek() {
        sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean flag = true;
        String subDateInput;
        LocalDate subDateTime = null;
        System.out.println("Enter a date to find out which students have assignments due within the week. (dd/mm/yyyy)");
        while (flag) {

            subDateInput = sc.nextLine();
            try {
                subDateTime = LocalDate.parse(subDateInput, formatter);
                boolean secondFlag = true;
                flag = false;
            } catch (DateTimeParseException ex) {
                System.out.println("Please enter a date in the correct format: (dd/mm/yyyy)");
            }
        }

        DayOfWeek myDayOfWeek = subDateTime.getDayOfWeek();
        LocalDate firstDateOfWeek = subDateTime;
        while (firstDateOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstDateOfWeek = firstDateOfWeek.minusDays(1);
        }
        System.out.println("First day of the week " + firstDateOfWeek.format(formatter));

        LocalDate lastDateOfWeek = firstDateOfWeek.plusDays(4);
        System.out.println("Last day of the week " + lastDateOfWeek.format(formatter));
        ArrayList<Student> studentsWithAssignmentsWithinGivenWeek = new ArrayList();
        int counter = 0;
        for (int i = 0; i < lists.getListOfStudents().size(); i++) {
            for (int j = 0; j < lists.getListOfStudents().get(i).getStudentUndertakenAssignments().size(); j++) {
                if ((lists.getListOfStudents().get(i).getStudentUndertakenAssignments().get(j).getSubDateTime().isAfter(firstDateOfWeek)
                        || lists.getListOfStudents().get(i).getStudentUndertakenAssignments().get(j).getSubDateTime().equals(firstDateOfWeek))
                        && (lists.getListOfStudents().get(i).getStudentUndertakenAssignments().get(j).getSubDateTime().isBefore(lastDateOfWeek)
                        || lists.getListOfStudents().get(i).getStudentUndertakenAssignments().get(j).getSubDateTime().equals(lastDateOfWeek))) {
                    if (!studentsWithAssignmentsWithinGivenWeek.contains(lists.getListOfStudents().get(i))) {
                        studentsWithAssignmentsWithinGivenWeek.add(lists.getListOfStudents().get(i));
                        counter++;
                    }
                }
            }
        }
        if (counter == 0) {
            System.out.println("No students have to submit assignments within the given week.");
        } else if (counter >= 1) {
            counter = 1;
            for (Student student : studentsWithAssignmentsWithinGivenWeek) {
                System.out.println(counter + ". " + student.getFirstName() + " " + student.getLastName());
                counter++;
            }
        }

    }   

}

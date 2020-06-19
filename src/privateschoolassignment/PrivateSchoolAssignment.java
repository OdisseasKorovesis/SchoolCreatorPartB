package privateschoolassignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.DriverManager;

//στο Part A ζητούνταν κάποιες λειτουργίες που δεν είναι απαραίτητες στο Part B
//αυτές δεν αφαιρέθηκαν, συνεχίζουν να λειτουργούν αλλά δεν έχει γίνει σύνδεσή τους με την βάση καθώς δεν ζητήθηκε

public class PrivateSchoolAssignment {
    
    static Scanner sc = new Scanner(System.in);
    static Lists lists = new Lists();
    static Database db = new Database();

    public static void main(String[] args) {
 
        
        System.out.println("Starting the creation of the private school...");        
        System.out.println("Do you want to create assignments, courses, students, trainers manually?\n"
                + "Or would you prefer to create a sample school?\n"
                + "(Type Manually or Sample to choose the method of creation.)");

        String choice = sc.nextLine();
        while(true) {
        if (choice.equals("Manually")) {
            System.out.println("Let's start with the school courses.");
            System.out.println("Keep in mind, any identical entry will ultimately be ignored.");
            Course.addCourses();
            Course.printCourses();
            System.out.println("Let's continue with the assignments of each course.");
            System.out.println("As before, any identical entry will ultimately be ignored.");
            Assignment.addAssignments();
            Assignment.printAssignmentsPerCourse();
            System.out.println("Now let's create the students of our school.");
            System.out.println("As before, any identical entry will ultimately be ignored.");

            String continues = "";
            while (!"N".equals(continues)) {
                Student.addStudent(Student.createStudent());
                System.out.println("Do you want to create another student? If no type N, else press any key...");
                continues = sc.nextLine();
            }
            Student.printStudentsWithCourses();

            System.out.println("Finally let's create the trainers of our school.");
            System.out.println("Don't forget, any identical entry will ultimately be ignored.");

            continues = "";
            while (!"N".equals(continues)) {
                Trainer.addTrainer(Trainer.createTrainer());
                System.out.println("Do you want to create another trainer? If no type N, else press any key...");
                continues = sc.nextLine();
            }
            Trainer.printTrainersWithCourses();
            System.out.println("You have created a fully functioning school.");          
            printMenuOfChoices();
            break;
        } else if (choice.equals("Sample")) {

            Course.createSyntheticCourses();
            Assignment.createSyntheticAssignments();
            Student.createSyntheticStudents();
            Trainer.createSyntheticTrainers();
            System.out.println("A fully functioning school has been generated for you.");
            printMenuOfChoices();
            break;

        } else {
            System.out.println("Please type Manually or Sample.");
            choice = sc.nextLine();
        }
        }

    }       

    public static void printMenuOfChoices() {
        
        boolean flag = true;
        Database.insertIntoStudentCourseAssignments();
        while (flag) {
            System.out.println("What information would you like to view now?");
            System.out.println("1. A list of all the students");
            System.out.println("2. A list of all the trainers");
            System.out.println("3. A list of all the assignments");
            System.out.println("4. A list of all the courses");
            System.out.println("5. A list of students per course");
            System.out.println("6. A list of trainers per course");
            System.out.println("7. A list of assignments per student");
            System.out.println("8. A list of assignments per course.");
            System.out.println("9. A list of assignments per student per course.");            
            System.out.println("10. A list of students that belong to more than one course");
            System.out.println("11. A list of students that need to submit an assignment within a given week");
            System.out.println("12. Nothing more. Exit the programm.");
            
            

            int choice;
            do {
                System.out.println("Enter the appropriate number (1-10) to continue...");
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a number.");
                    sc.nextLine();
                }
                choice = sc.nextInt();
            } while (choice < 0 || choice > 12);

            switch (choice) {
                case 1:
                    System.out.println("Below is a list of all the students.");            
                    db.selectFromStudents();
                    break;
                case 2:
                    System.out.println("Below is a list of all the trainers.");                    
                    db.selectFromTrainers();
                    break;
                case 3:
                    System.out.println("Below is a list of all the assignments.");                    
                    db.selectFromAssignments();
                    break;
                case 4:
                    System.out.println("Below is a list of all the courses.");                    
                    db.selectFromCourses();
                    break;
                case 5:                   
                    db.selectStudentsPerCourseCorrect();
                    break;
                case 6:                    
                    db.selectTrainersPerCourseCorrect();
                    break;
                case 7:
                    //δεν ζητείται στο part b, δεν εντάχθηκε στην σύνδεση με βάση
                    System.out.println("Below is a list of assignments per student:");
                    int counter = 1;
                    for (Student student : lists.getListOfStudents()) {
                        System.out.println(counter + ". " + student.getFirstName() + " " + student.getLastName());
                        for (Assignment assignment : student.getStudentUndertakenAssignments()) {
                            System.out.println(assignment.getTitle());
                        }
                        counter++;
                    }
                    break;
                case 8:                    
                    db.selectAssignmentsPerCourseCorrect();
                    break;                    
                case 9:                 
                    db.selectAssignmentsPerStudentPerCourseCorrect();
                    break;
                case 10:
                    System.out.println("Below is a list of students that belong to more than one course.");
                    if(lists.getListOfMultipleCourseStudents().size() > 0) {                    
                    db.selectMultipleCourseStudents();
                    } else {
                        System.out.println("There are no students that belong to more than one course.");
                    }
                    break;
                case 11:
                    //δεν ζητείται από το part b, δεν εντάχθηκε στην σύνδεση με βάση
                    lists.printStudentsWithAssignmentsWithinGivenWeek();
                    break;
                case 12:
                    flag = false;
                    break;                    
            }
        }
    }
    
    
    
    
}

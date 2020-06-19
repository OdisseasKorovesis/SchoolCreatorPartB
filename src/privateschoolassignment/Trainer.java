package privateschoolassignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import static privateschoolassignment.PrivateSchoolAssignment.lists;
import static privateschoolassignment.Student.clearListOfDoubleStudents;

public class Trainer {

    private static Scanner sc;

    private String firstName;
    private String lastName;
    private String subject;

    private ArrayList<Course> trainerEnrolledCourses = new ArrayList();

    public Trainer() {

    }
    
    public Trainer(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public ArrayList<Course> getTrainerEnrolledCourses() {
        return trainerEnrolledCourses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }
    
    

    @Override
    public String toString() {
        return firstName + " " + lastName + ", teaches " + subject;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.firstName);
        hash = 29 * hash + Objects.hashCode(this.lastName);
        hash = 29 * hash + Objects.hashCode(this.subject);
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
        final Trainer other = (Trainer) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }

    public static void createSyntheticTrainers() {
        Trainer trainer1 = new Trainer("Giorgos", "Papadakis", "Back-End");
        lists.getListOfTrainers().add(trainer1);
        Database.insertIntoTrainersSingle(trainer1);
        lists.getListOfCourses().get(0).getCourselistOfTrainers().add(trainer1);
        Database.insertIntoCourseTrainers(lists.getListOfCourses().get(0));

        Trainer trainer2 = new Trainer("Iasonas", "Michalopoulos", "Back-End");
        lists.getListOfTrainers().add(trainer2);
        Database.insertIntoTrainersSingle(trainer2);
        lists.getListOfCourses().get(1).getCourselistOfTrainers().add(trainer2);
        Database.insertIntoCourseTrainers(lists.getListOfCourses().get(1));

        Trainer trainer3 = new Trainer("Maria", "Panagiotidou", "Back-End");
        lists.getListOfTrainers().add(trainer3);
        Database.insertIntoTrainersSingle(trainer3);
        lists.getListOfCourses().get(2).getCourselistOfTrainers().add(trainer3);
        Database.insertIntoCourseTrainers(lists.getListOfCourses().get(2));

        Trainer trainer4 = new Trainer("Ismini", "Aleksiadou", "Front-End");
        lists.getListOfTrainers().add(trainer4);
        Database.insertIntoTrainersSingle(trainer4);
        lists.getListOfCourses().get(3).getCourselistOfTrainers().add(trainer4);
        Database.insertIntoCourseTrainers(lists.getListOfCourses().get(3));        
    }

    public static Trainer createTrainer() {
        sc = new Scanner(System.in);
        System.out.println("What is the first name of the trainer?");
        String firstName = sc.nextLine();
        System.out.println("What is the last name of the trainer?");
        String lastName = sc.nextLine();
        System.out.println("What subject does he/she teach?");
        String subject = sc.nextLine();
        
        Trainer trainer = new Trainer(firstName, lastName, subject);
        return trainer;
    }

    public static void addTrainer(Trainer trainer) {
        sc = new Scanner(System.in);
        boolean flag = true;

        lists.getListOfTrainers().add(trainer);
        if(Database.insertIntoTrainersSingle(trainer)) {
        int counter = 1;
        System.out.println("Which courses will this trainer teach?");
        for (Course course : lists.getListOfCourses()) {
            System.out.println(counter + ". " + course.getName());
            counter++;
            System.out.println("Type Y to make trainer teach this course or any key to view next course.");
            String choice = sc.nextLine();
            if (choice.equals("Y")) {
                trainer.getTrainerEnrolledCourses().add(course);
                course.getCourselistOfTrainers().add(trainer);
                Database.insertIntoCourseTrainers(course);
            }
        }
        } else {
            System.out.println("Duplicate entry, was not created.");
        }
        ArrayList<Trainer> tempList = new ArrayList(lists.getListOfTrainers());
        tempList = clearListOfDoubleTrainers(tempList);
        lists.getListOfTrainers().clear();
        lists.getListOfTrainers().addAll(tempList);
        
        for(Course course1 : lists.getListOfCourses()) {
            tempList = new ArrayList(course1.getCourselistOfTrainers());
            tempList = clearListOfDoubleTrainers(tempList);
            course1.getCourselistOfTrainers().clear();
            course1.getCourselistOfTrainers().addAll(tempList);
            }
    }

    public static void printTrainersWithCourses() {
        System.out.println("Below is a list of the trainers along with the courses they teach:");
        int counter = 1;
        for (Trainer trainer : lists.getListOfTrainers()) {
            System.out.println(counter + ". " + trainer.getFirstName() + " " + trainer.getLastName());
            counter++;
            for (Course course : trainer.getTrainerEnrolledCourses()) {
                System.out.println(course.getName());
            }
        }
    }

    public static ArrayList<Trainer> clearListOfDoubleTrainers(ArrayList<Trainer> list) {
        HashSet<Trainer> set = new HashSet(list);
        ArrayList<Trainer> clearedList = new ArrayList(set);
        clearedList.sort(Comparator.comparing(Trainer::getLastName));
        return clearedList;
    }

}

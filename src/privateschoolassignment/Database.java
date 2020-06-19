package privateschoolassignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import static privateschoolassignment.PrivateSchoolAssignment.lists;

public class Database {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "root";

    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement prepStatement = null;
    private static ResultSet resultSet = null;

    public Database() {
        getConnection();
        createSchema();
        createSchemaTables();
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            return connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Statement getStatement() {
        return statement;
    }

    public static PreparedStatement getPrepStatement() {
        return prepStatement;
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }

    public static void setConnection(Connection connection) {
        Database.connection = connection;
    }

    public static void setStatement() {
        Database.statement = statement;
    }

    public static void setPrepStatement(String querry) {
        Database.prepStatement = prepStatement;
    }

    public static void setResultSet(ResultSet resultSet) {
        Database.resultSet = resultSet;
    }

    //καλείται από τον constructor για να δημιουργηθεί το schema εφόσον δεν είναι ήδη έτοιμο
    public void createSchema() {
        String sql = "CREATE SCHEMA IF NOT EXISTS `privateschool`";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
            connection.setCatalog("privateschool");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //καλείται επίσης από τον constructor για να δημιουργήθουν τα tables του schema    
    public static void createSchemaTables() {

        String sql = "CREATE TABLE IF NOT EXISTS `courses` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`title` VARCHAR(45) NOT NULL,"
                + "`stream` VARCHAR(45) NOT NULL,"
                + "`type` VARCHAR(45) NOT NULL,"
                + "`start_date` DATE NOT NULL,"
                + "`end_date` DATE NOT NULL,"
                + "PRIMARY KEY(`id`));";

        String sql2 = "CREATE TABLE IF NOT EXISTS `assignments` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`title` VARCHAR(45) NOT NULL,"
                + "`description` VARCHAR(45) NOT NULL,"
                + "`submission_date` DATE NOT NULL,"
                + "`total_mark` INT NOT NULL,"
                + "PRIMARY KEY(`id`));";

        String sql3 = "CREATE TABLE IF NOT EXISTS `students` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`first_name` VARCHAR(45) NOT NULL,"
                + "`last_name` VARCHAR(45) NOT NULL,"
                + "`date_of_birth` DATE NOT NULL,"
                + "`tuition_fees` DECIMAL(10,2) NOT NULL,"
                + "PRIMARY KEY(`id`));";

        String sql4 = "CREATE TABLE IF NOT EXISTS `trainers` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`first_name` VARCHAR(45) NOT NULL,"
                + "`last_name` VARCHAR(45) NOT NULL,"
                + "`subject` VARCHAR(45) NOT NULL,"
                + "PRIMARY KEY(`id`));";

        String sql5 = "CREATE TABLE IF NOT EXISTS `course_assignment` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`course_id` INT NOT NULL,"
                + "`assignment_id` INT NOT NULL,"
                + "PRIMARY KEY(`id`),"
                + "CONSTRAINT `fk_assignment_id__assignments_id` "
                + "FOREIGN KEY (`assignment_id`) "
                + "REFERENCES `assignments`(`id`), "
                + "CONSTRAINT `fk_course_id__courses3_id` "
                + "FOREIGN KEY (`course_id`) "
                + "REFERENCES `courses`(`id`));";

        String sql6 = "CREATE TABLE IF NOT EXISTS `course_students` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`course_id` INT NOT NULL,"
                + "`student_id` INT NOT NULL,"
                + "PRIMARY KEY(`id`),"
                + "CONSTRAINT `fk_student_id__students_id` "
                + "FOREIGN KEY (`student_id`) "
                + "REFERENCES `students`(`id`), "
                + "CONSTRAINT `fk_course_id__courses_id` "
                + "FOREIGN KEY (`course_id`) "
                + "REFERENCES `courses`(`id`));";

        String sql7 = "CREATE TABLE IF NOT EXISTS `course_trainers` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`course_id` INT NOT NULL,"
                + "`trainer_id` INT NOT NULL,"
                + "PRIMARY KEY(`id`),"
                + "CONSTRAINT `fk_trainer_id__trainers_id` "
                + "FOREIGN KEY (`trainer_id`) "
                + "REFERENCES `trainers`(`id`), "
                + "CONSTRAINT `fk_course_id__courses2_id` "
                + "FOREIGN KEY (`course_id`) "
                + "REFERENCES `courses`(`id`));";

        String sql8 = "CREATE TABLE IF NOT EXISTS `student_course_assignments` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`student_id` INT NOT NULL,"
                + "`course_id` INT NOT NULL,"
                + "`assignment_id` INT NOT NULL,"
                + "PRIMARY KEY(`id`),"
                + "CONSTRAINT `fk_student_id__students2_id` "
                + "FOREIGN KEY (`student_id`) "
                + "REFERENCES `students`(`id`), "
                + "CONSTRAINT `fk_assignment_id__assignments2_id` "
                + "FOREIGN KEY (`assignment_id`) "
                + "REFERENCES `assignments`(`id`), "
                + "CONSTRAINT `fk_course_id__courses4_id` "
                + "FOREIGN KEY (`course_id`) "
                + "REFERENCES `courses`(`id`));";

        String sql9;

        try {
            Statement st = connection.createStatement();

            st.executeUpdate(sql);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);
            st.executeUpdate(sql4);
            st.executeUpdate(sql5);
            st.executeUpdate(sql6);
            st.executeUpdate(sql7);
            st.executeUpdate(sql8);

            //σε αυτό το σημείο γίνονται truncate τα table ώστε σε περίπτωση που
            //το πρόγραμμα τρέξει δεύτερη φορά να μην κρατήσει δεδομένα από προηγούμενη
            //φορά που έχει τρέξει
            sql9 = "SET FOREIGN_KEY_CHECKS=0;";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `student_course_assignments`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `course_trainers`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `course_assignment`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `course_students`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `trainers`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `assignments`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `courses`; ";
            st.executeUpdate(sql9);
            sql9 = "TRUNCATE table `students`;";
            st.executeUpdate(sql9);
            sql9 = "SET FOREIGN_KEY_CHECKS=1;";
            st.executeUpdate(sql9);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //οι παρακάτω μέθοδοι καλούνται από την PrintMenuOfChoices στην main,
    //επιστρέφουν τα αντίστοιχα στοιχεία από την βάση δεδομένων και τα τυπώνουν
    public void selectFromCourses() {
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT * FROM `courses`;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            while (rs.next()) {
                System.out.println(rs.getString(2)
                        + ", "
                        + rs.getString(3)
                        + ", "
                        + rs.getString(4)
                        + ", "
                        + rs.getString(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectFromStudents() {
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT * FROM `students`;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            while (rs.next()) {
                System.out.println(rs.getString(2)
                        + " "
                        + rs.getString(3)
                        + ", "
                        + rs.getString(4)
                        + ", "
                        + rs.getString(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectFromAssignments() {
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT * FROM `assignments`;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            while (rs.next()) {
                System.out.println(rs.getString(2)
                        + " "
                        + rs.getString(3)
                        + ", "
                        + rs.getString(4)
                        + ", "
                        + rs.getString(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectFromTrainers() {
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT * FROM `trainers`;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            while (rs.next()) {
                System.out.println(rs.getString(2)
                        + " "
                        + rs.getString(3)
                        + ", "
                        + rs.getString(4));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectMultipleCourseStudents() {
        try {
            connection.setCatalog("privateschool");
            String querry = "create or replace view count_of_enrolled_courses_view as "
                    + "select students.first_name as StudentFirstName, "
                    + "students.last_name as StudentLastName, "
                    + "count(course_students.student_id) NumberOfEnrolledCourses "
                    + "from students "
                    + "join course_students on students.id = course_students.student_id "
                    + "group by course_students.student_id;";
            Statement st = connection.createStatement();
            st.executeUpdate(querry);

            String querry2 = "select * from  count_of_enrolled_courses_view "
                    + "where NumberOfEnrolledCourses > 1;";

            Statement st2 = connection.createStatement();
            ResultSet rs = st.executeQuery(querry2);
            while (rs.next()) {
                System.out.println(rs.getString(1)
                        + " "
                        + rs.getString(2)
                        + ", courses following: "
                        + rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectStudentsPerCourseCorrect() {
        try {
            connection.setCatalog("privateschool");

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the id of the desired course.");
            int courseId = sc.nextInt();

            String querry1 = "select courses.title as CourseTitle, courses.stream as CourseStream "
                    + "from courses "
                    + "join course_students on courses.id = course_students.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry2 = "select count(course_students.id) as CountOfStudents "
                    + "from courses "
                    + "join course_students on courses.id = course_students.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry3 = "select courses.title as CourseTitle, courses.stream as CourseStream, "
                    + "students.first_name as StudentFirstName, students.last_name as StudentLastName "
                    + "from students "
                    + "join course_students on students.id = course_students.student_id "
                    + "join courses on courses.id = course_students.course_id and courses.id = " + courseId
                    + " order by courses.id;";

            System.out.println("Below is a list of the students of the specified course.");

            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            ResultSet rs1 = st1.executeQuery(querry1);
            ResultSet rs2 = st2.executeQuery(querry2);
            ResultSet rs3 = st3.executeQuery(querry3);
            int counter = 1;
            int countOfStudents = 0;
            while (rs1.next()) {
                System.out.println(counter + ". " + rs1.getString(1) + ", " + rs1.getString(2));
                counter++;

                rs2.next();
                countOfStudents = rs2.getInt(1);
                for (int i = 1; i <= countOfStudents; i++) {
                    rs3.next();
                    System.out.println(rs3.getString(3) + " " + rs3.getString(4));
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectTrainersPerCourseCorrect() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id of the desired course.");
        int courseId = sc.nextInt();

        try {
            connection.setCatalog("privateschool");
            String querry1 = "select courses.title as CourseTitle, courses.stream as CourseStream "
                    + "from courses "
                    + "join course_trainers on courses.id = course_trainers.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry2 = "select count(course_trainers.id) as CountOfTrainers "
                    + "from courses "
                    + "join course_trainers on courses.id = course_trainers.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry3 = "select courses.title as CourseTitle, courses.stream as CourseStream, "
                    + "trainers.first_name as TrainerFirstName, trainers.last_name as TrainerLastName "
                    + "from trainers "
                    + "join course_trainers on trainers.id = course_trainers.trainer_id "
                    + "join courses on courses.id = course_trainers.course_id and courses.id = " + courseId
                    + " order by courses.id;";

            System.out.println("Below is a list of the trainers of the specified course.");

            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            ResultSet rs1 = st1.executeQuery(querry1);
            ResultSet rs2 = st2.executeQuery(querry2);
            ResultSet rs3 = st3.executeQuery(querry3);
            int counter = 1;
            int countOfStudents = 0;
            while (rs1.next()) {
                System.out.println(counter + ". " + rs1.getString(1) + ", " + rs1.getString(2));
                counter++;

                rs2.next();
                countOfStudents = rs2.getInt(1);
                for (int i = 1; i <= countOfStudents; i++) {
                    rs3.next();
                    System.out.println(rs3.getString(3) + " " + rs3.getString(4));
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectAssignmentsPerCourseCorrect() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id of the desired course.");
        int courseId = sc.nextInt();

        try {
            connection.setCatalog("privateschool");
            String querry1 = "select courses.title as CourseTitle, courses.stream as CourseStream "
                    + "from courses "
                    + "join course_assignment on courses.id = course_assignment.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry2 = "select count(course_assignment.id) as CountOfAssignments "
                    + "from courses "
                    + "join course_assignment on courses.id = course_assignment.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry3 = "select courses.title as CourseTitle, courses.stream as CourseStream, "
                    + "assignments.title as AssignmentTitle "
                    + "from assignments "
                    + "join course_assignment on assignments.id = course_assignment.assignment_id "
                    + "join courses on courses.id = course_assignment.course_id and courses.id = " + courseId
                    + " order by courses.id;";

            System.out.println("Below is a list of the assignments of the specified course.");

            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            ResultSet rs1 = st1.executeQuery(querry1);
            ResultSet rs2 = st2.executeQuery(querry2);
            ResultSet rs3 = st3.executeQuery(querry3);
            int counter = 1;
            int countOfStudents = 0;
            while (rs1.next()) {
                System.out.println(counter + ". " + rs1.getString(1) + ", " + rs1.getString(2));
                counter++;

                rs2.next();
                countOfStudents = rs2.getInt(1);
                for (int i = 1; i <= countOfStudents; i++) {
                    rs3.next();
                    System.out.println(rs3.getString(3));
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void selectAssignmentsPerStudentPerCourseCorrect() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id of the desired course.");
        int courseId = sc.nextInt();

        try {
            connection.setCatalog("privateschool");
            String querry1 = "select courses.title as CourseTitle, courses.stream as CourseStream "
                    + "from courses "
                    + "join course_students on courses.id = course_students.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry2 = "select count(course_students.id) as CountOfStudents "
                    + "from courses "
                    + "join course_students on courses.id = course_students.course_id and courses.id = " + courseId
                    + " group by courses.id;";

            String querry3 = "select courses.title as CourseTitle, courses.stream as CourseStream, "
                    + "students.first_name as StudentFirstName, students.last_name as StudentLastName "
                    + "from students "
                    + "join course_students on students.id = course_students.student_id "
                    + "join courses on courses.id = course_students.course_id and courses.id = " + courseId
                    + " order by courses.id;";

            String querry4 = "select count(student_course_assignments.assignment_id) as CountOfAssignmentsPerStudents "
                    + "from assignments "
                    + "join student_course_assignments on assignments.id = student_course_assignments.assignment_id "
                    + "join courses on courses.id = student_course_assignments.course_id and courses.id = " + courseId
                    + " group by student_course_assignments.student_id, student_course_assignments.course_id";

            String querry5 = "select assignments.title as AssignmentTitle, courses.title as CourseTitle, "
                    + "students.first_name as StudentFirstName, students.last_name as StudentLastName "
                    + "from assignments "
                    + "join student_course_assignments on assignments.id = student_course_assignments.assignment_id "
                    + "join courses on courses.id = student_course_assignments.course_id and courses.id = " + courseId
                    + " join students on students.id = student_course_assignments.student_id"
                    + " order by students.id;";

            System.out.println("Below is a list of the specified course with its students and their respective assignments.");

            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            Statement st4 = connection.createStatement();
            Statement st5 = connection.createStatement();
            ResultSet rs1 = st1.executeQuery(querry1);
            ResultSet rs2 = st2.executeQuery(querry2);
            ResultSet rs3 = st3.executeQuery(querry3);
            ResultSet rs4 = st4.executeQuery(querry4);
            ResultSet rs5 = st5.executeQuery(querry5);

            int counter = 1;
            int countOfStudents = 0;
            int countOfAssignments = 0;
            while (rs1.next()) {
                System.out.println(counter + ". " + rs1.getString(1) + ", " + rs1.getString(2));
                counter++;

                rs2.next();
                countOfStudents = rs2.getInt(1);
                for (int i = 1; i <= countOfStudents; i++) {
                    rs3.next();
                    System.out.println(rs3.getString(3) + " " + rs3.getString(4));
                    rs4.next();
                    countOfAssignments = rs4.getInt(1);
                    for (int j = 1; j <= countOfAssignments; j++) {
                        rs5.next();
                        System.out.println(rs5.getString(1));
                    }
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //οι παρακάτω μέθοδοι καλούνται από τις αντίστοιχες μεθόδους της αντίστοιχης κλάσης
    //προκειμένου να προσθέσουν στη βάση κάθε αντικείμενο που δημιουργείται -
    //επιστρέφουν boolean για να αποκλείουν τα διπλά στοιχεία 
    //εκτός από το course που χρησιμοποιεί λειτουργικότητα του part a για να αποκλείσει τα διπλά
    //και το assignment που λόγω δομής του part a δεν κατάφερα να το προσαρμόσω
    public static void insertIntoCourses() {
        String courseData;
        String sql;
        for (int i = 0; i < lists.getListOfCourses().size(); i++) {
            Course course = lists.getListOfCourses().get(i);

            courseData = "'" + course.getName() + "', '" + course.getStream() + "', '"
                    + course.getType() + "', '" + course.getStartDate().toString() + "', '"
                    + course.getEndDate().toString() + "'";

            sql = "INSERT INTO `courses`(`title`, `stream`, `type`, `start_date`, `end_date`) "
                    + "VALUES(" + courseData + ");";

            try {
                Statement st = connection.createStatement();
                st.executeUpdate(sql);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static boolean insertIntoStudentsSingle(Student student) {
        String studentData;
        String sql;
        String sql2;

        studentData = "'" + student.getFirstName() + "', '" + student.getLastName() + "', '"
                + student.getDateOfBirth().toString() + "', '"
                + student.getTuitionFees() + "'";

        sql = "INSERT INTO `students`(`first_name`, `last_name`, `date_of_birth`, `tuition_fees`) "
                + "VALUES(" + studentData + ");";

        sql2 = "SELECT COUNT(1)from `students` WHERE `first_name` = '" + student.getFirstName()
                + "' AND `last_name` = '" + student.getLastName()
                + "' AND `date_of_birth` = '" + student.getDateOfBirth()
                + "' AND tuition_fees = " + student.getTuitionFees();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql2);
            rs.first();
            int count = rs.getInt(1);
            if (count == 0) {
                st.executeUpdate(sql);
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean insertIntoAssignmentsSingle(Assignment assignment) {
        String assignmentData;
        String sql;
        String sql2;

        assignmentData = "'" + assignment.getTitle() + "', '" + assignment.getDescription() + "', '"
                + assignment.getSubDateTime().toString() + "', '"
                + assignment.getTotalMark() + "'";

        sql = "INSERT INTO `assignments`(`title`, `description`, `submission_date`, `total_mark`) "
                + "VALUES(" + assignmentData + ");";

        sql2 = "SELECT COUNT(1)from `assignments` WHERE `title` = '" + assignment.getTitle()
                + "' AND `description` = '" + assignment.getDescription()
                + "' AND `submission_date` = '" + assignment.getSubDateTime()
                + "' AND `total_mark` = " + assignment.getTotalMark();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql2);
            rs.first();
            int count = rs.getInt(1);
            if (count == 0) {
                st.executeUpdate(sql);
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public static boolean insertIntoTrainersSingle(Trainer trainer) {
        String trainerData;
        String sql;
        String sql2;

        trainerData = "'" + trainer.getFirstName() + "', '" + trainer.getLastName() + "', '"
                + trainer.getSubject() + "'";

        sql = "INSERT INTO `trainers`(`first_name`, `last_name`, `subject`) "
                + "VALUES(" + trainerData + ");";

        sql2 = "SELECT COUNT(1) from `trainers` WHERE `first_name` = '" + trainer.getFirstName()
                + "' AND `last_name` = '" + trainer.getLastName()
                + "' AND `subject` = '" + trainer.getSubject() + "';";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql2);
            rs.first();
            int count = rs.getInt(1);
            if (count == 0) {
                st.executeUpdate(sql);
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
    

    //οι παρακάτω μέθοδοι φτιάχνουν τα tables που συσχετίζουν τα entities 
    //καλούνται κατά την δημιουργία οποιουδήποτε Object
    public static void insertIntoCourseStudents(Course course) {
        int courseID = lists.getListOfCourses().indexOf(course) + 1;
        int studentID = 0;
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT `id` FROM `students` ORDER BY `id` DESC LIMIT 1";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            rs.next();
            studentID = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String sql = "INSERT INTO `course_students`(`course_id`, `student_id`) "
                + "VALUES( '" + courseID + "', '" + studentID + "');";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertIntoCourseAssignments(Course course) {
        int courseID = lists.getListOfCourses().indexOf(course) + 1;
        int assignmentID = 0;
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT `id` FROM `assignments` ORDER BY `id` DESC LIMIT 1";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            rs.next();
            assignmentID = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String sql = "INSERT INTO `course_assignment`(`course_id`, `assignment_id`) "
                + "VALUES( '" + courseID + "', '" + assignmentID + "');";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertIntoCourseTrainers(Course course) {
        int courseID = lists.getListOfCourses().indexOf(course) + 1;
        int trainerID = 0;
        try {
            connection.setCatalog("privateschool");
            String querry = "SELECT `id` FROM `trainers` ORDER BY `id` DESC LIMIT 1";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(querry);
            rs.next();
            trainerID = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String sql = "INSERT INTO `course_trainers`(`course_id`, `trainer_id`) "
                + "VALUES( '" + courseID + "', '" + trainerID + "');";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertIntoStudentCourseAssignments() {

        try {
            connection.setCatalog("privateschool");
            String sql = "insert into student_course_assignments(student_id, course_id, assignment_id) "
                    + "select course_students.student_id, course_students.course_id,  course_assignment.assignment_id "
                    + "from course_students "
                    + "join course_assignment on course_students.course_id = course_assignment.course_id "
                    + "order by course_students.student_id;";
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

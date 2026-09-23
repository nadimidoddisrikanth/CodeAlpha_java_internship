package Task1;
import java.util.*;

class Student {
    String name;
    double score;

    Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    String getGrade() {
        if (score >= 90)
            return "A";
        else if (score >= 80)
            return "B";
        else if (score >= 70)
            return "C";
        else if (score >= 60)
            return "D";
        else
            return "F";
    }
}

public class StudentGradeManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("======================================");
        System.out.println("     STUDENT GRADE MANAGEMENT SYSTEM");
        System.out.println("======================================");

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            double score;

            // Validate score
            while (true) {
                System.out.print("Enter score (0 - 100): ");
                score = sc.nextDouble();

                if (score >= 0 && score <= 100) {
                    break;
                }

                System.out.println("Invalid score! Enter a value between 0 and 100.");
            }

            sc.nextLine();

            students.add(new Student(name, score));
        }

        // Calculate statistics
        double total = 0;
        double highest = students.get(0).score;
        double lowest = students.get(0).score;

        String highestStudent = students.get(0).name;
        String lowestStudent = students.get(0).name;

        for (Student student : students) {

            total += student.score;

            if (student.score > highest) {
                highest = student.score;
                highestStudent = student.name;
            }

            if (student.score < lowest) {
                lowest = student.score;
                lowestStudent = student.name;
            }
        }

        double average = total / students.size();

        // Display  progress report
        System.out.println("\n\n======================================");
        System.out.println("           STUDENT SUMMARY REPORT");
        System.out.println("======================================");

        System.out.printf("%-20s %-10s %-10s%n",
                "Student Name", "Score", "Grade");

        System.out.println("--------------------------------------");

        for (Student student : students) {
            System.out.printf("%-20s %-10.2f %-10s%n",
                    student.name,
                    student.score,
                    student.getGrade());
        }

        System.out.println("--------------------------------------");

        System.out.printf("Average Score : %.2f%n", average);
        System.out.printf("Highest Score : %.2f (%s)%n",
                highest, highestStudent);
        System.out.printf("Lowest Score  : %.2f (%s)%n",
                lowest, lowestStudent);

        System.out.println("======================================");

        sc.close();
    }
}
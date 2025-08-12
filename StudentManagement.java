/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentmanagement;

/**
 *
 * @author Studio2013
 */
import java.util.*;
//the main that holds all the information.
public class StudentManagement {
    
    private ArrayList<Student> students = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

   
    public static void main(String[] args) {
        new StudentManagement().run();
    }

    // loop through cases to displays menu and processes user choices
    private void run() {
        while (true) {
            System.out.println("\n1.Add 2.Edit 3.Remove 4.List 5.Average 6.Clear 7.Exit");
            int choice = getInt("Choice: ");
            switch (choice) {
                case 1: add(); break;       // Add new student
                case 2: edit(); break;      // Modify existing student
                case 3: remove(); break;     // Delete student
                case 4: list(); break;      // Display all students
                case 5: average(); break;    // Calculate class average
                case 6: clear(); break;      // Remove all students
                case 7: return;              // Exit program
                default: System.out.println("Invalid");
            }
        }
    }

    // method to get and adds new student
    private void add() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        int id = getInt("ID: ");
        if (find(id) != null) { System.out.println("ID exists!"); return; }
        int age = getInt("Age: ");
        System.out.print("Grades : ");
        ArrayList<Double> grades = new ArrayList<>();
        for (String s : sc.nextLine().split(" ")) {
            try { grades.add(Double.parseDouble(s)); } catch (Exception e) {}
        }
        students.add(new Student(name, id, age, grades));
        System.out.println("Added!");
    }

    // method to edits student details
    private void edit() {
        if (students.isEmpty()) { System.out.println("Empty!"); return; }
        int id = getInt("Student ID: ");
        Student s = find(id);
        if (s == null) { System.out.println("Not found!"); return; }
        System.out.println("1.Name 2.Age 3.Grades");
        switch (getInt("Edit: ")) {
            case 1: s.name = sc.nextLine(); break;       // Edit name
            case 2: s.age = getInt("New age: "); break;  // Edit age
            case 3:                                      // Edit grades
                System.out.print("New grades: ");
                s.grades.clear();
                for (String g : sc.nextLine().split(" ")) {
                    try { s.grades.add(Double.parseDouble(g)); } catch (Exception e) {}
                }
                break;
            default: System.out.println("Invalid");
        }
    }

    // method to remove a student by ID
    private void remove() {
    if (students.isEmpty()) {
        System.out.println("No students to remove - list is empty!");
        return;
    }
    
    int idToRemove = getInt("Enter student ID to remove: ");
    Student studentToRemove = find(idToRemove);
    
    if (studentToRemove == null) {
        System.out.println("No student found with ID: " + idToRemove);
        return;
    }
    
    // Confirm removal
    System.out.print("Remove " + studentToRemove.name + " (ID: " + studentToRemove.id + ")? (y/n): ");
    String confirmation = sc.nextLine();
    
    if (confirmation.equalsIgnoreCase("y")) {
        students.remove(studentToRemove);
        System.out.println("Successfully removed student: " + studentToRemove.name);
    } else {
        System.out.println("Removal cancelled.");
    }
}

    // method to Lists all students with details
    private void list() {
        if (students.isEmpty()) { System.out.println("Empty!"); return; }
        for (Student s : students) {
            System.out.printf("%s (ID:%d, Age:%d) Grades:%s Avg:%.1f\n",
                s.name, s.id, s.age, s.grades, s.getAverage());
        }
    }

    // method to calculate class average
    private void average() {
        if (students.isEmpty()) { System.out.println("Empty!"); return; }
        double total = 0, count = 0;
        for (Student s : students) {
            double avg = s.getAverage();
            if (avg >= 0) { total += avg; count++; }
        }
        System.out.printf("Class average: %.1f\n", count > 0 ? total/count : 0);
    }

    // method to Clears all student records
    private void clear() {
    if (students.isEmpty()) {
        System.out.println("List is already empty!");
        return;
    }
    
    System.out.print("Are you sure you want to clear all students? (y/n): ");
    String confirmation = sc.nextLine();
    
    if (confirmation.equalsIgnoreCase("y")) {
        students.clear();
        System.out.println("Cleared!");
    }
}

    // method to finds student by ID
    private Student find(int id) {
        for (Student s : students) if (s.id == id) return s;
        return null;
    }

    // method to catch any errors using a while loop
    private int getInt(String prompt) {
        while (true) {
            try { System.out.print(prompt); return sc.nextInt(); }
            catch (Exception e) { sc.nextLine(); System.out.println("Numbers only!"); }
        }
    }

    // class for student listing all the information that needs to be accessed by the user of the program. 
    class Student {
        String name; int id, age; ArrayList<Double> grades;
        
        // constructor to Initializes student properties
        public Student(String n, int i, int a, ArrayList<Double> g) {
            name = n; id = i; age = a; grades = g;
        }
        
        // Calculates individual average
double getAverage() {
            return grades.isEmpty() ? -1 : 
                grades.stream().mapToDouble(d -> d).average().orElse(0);
        }
    }
}

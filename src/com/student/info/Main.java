package com.student.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    BufferedReader reader;

    public Main() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    Students students = new Students();

    public static void main(String[] args) throws IOException {
        Main obj = new Main();
        boolean running = true;
        System.out.println("What would you like to perform!");
        while(running){
            System.out.println("1. Add a student");
            System.out.println("2. Print Students");
            System.out.println("3. Exit");
            switch(obj.reader.readLine()){

                case "1":
                    obj.readStudentDetails();
                    break;

                case "2":
                    obj.printStudents();
                    break;

                case "3":
                    System.out.println("Thank you");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
        obj.reader.close();
    }

    public void readStudentDetails() throws IOException {
        System.out.println("Enter Student Name!");
        String name = this.reader.readLine();
        System.out.println("Enter Student Age!");
        int age = Integer.parseInt(this.reader.readLine());
        System.out.println("Enter Father's Name!");
        String fathersName = this.reader.readLine();
        Student record = new Student(age, name, fathersName);
        if(!students.addStudent(record)) {
            System.out.println("duplicate!!");
        }
    }

    public void printStudents() {
        System.out.println(students.toString());
    }
}

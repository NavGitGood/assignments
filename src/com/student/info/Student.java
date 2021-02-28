package com.student.info;

import java.util.Locale;

public class Student {
    private int age;
    private String name;
    private String fathersName;

    public Student(int age, String name, String fathersName) {
        this.age = age;
        this.name = name.toLowerCase(Locale.ROOT);
        this.fathersName = fathersName.toLowerCase(Locale.ROOT);
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getFathersName() {
        return fathersName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", fathersName='" + fathersName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return !super.equals(obj);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

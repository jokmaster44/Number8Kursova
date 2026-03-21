package org.Number8Kursova.Manager;

import java.util.ArrayList;
import java.util.List;


public class StudentGroup {

    private String name;
    private int course;
    private String specialty;
    private List<Student> students;

    public StudentGroup(String name, int course, String specialty) {
        this.name = name;
        this.course = course;
        this.specialty = specialty;
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCourse() {
        return course;
    }

    public String getSpecialty() {
        return specialty;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

}
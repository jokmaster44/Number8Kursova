package org.Number8Kursova.Manager;

import java.util.ArrayList;
import java.util.List;

public class DeaneryManager {

    private List<Student> students;
    private List<StudentGroup> groups;
    private List<ClassRoom> rooms;
    private int nextStudentId;

    public DeaneryManager() {
        students = new ArrayList<>();
        groups = new ArrayList<>();
        rooms = new ArrayList<>();
        nextStudentId = 1;
    }

    public int generateStudentId() {
        return nextStudentId++;
    }

    // ===== СТУДЕНТИ =====

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public List<Student> findStudentsByLastName(String lastName) {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName)) {
                result.add(student);
            }
        }

        return result;
    }

    public List<Student> getStudentsWithoutGroup() {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getGroupName() == null || student.getGroupName().trim().isEmpty()) {
                result.add(student);
            }
        }

        return result;
    }

    public List<Student> getStudentsByGroupName(String groupName) {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getGroupName() != null &&
                    student.getGroupName().equalsIgnoreCase(groupName)) {
                result.add(student);
            }
        }

        return result;
    }

    public List<Student> getDormitoryStudents() {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.isLivingInDormitory()) {
                result.add(student);
            }
        }

        return result;
    }

    public List<Student> getStudentsNotInDormitory() {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (!student.isLivingInDormitory()) {
                result.add(student);
            }
        }

        return result;
    }

    // ===== ГРУПИ =====

    public void addGroup(StudentGroup group) {
        groups.add(group);
    }

    public void removeGroup(StudentGroup group) {
        groups.remove(group);
    }

    public List<StudentGroup> getAllGroups() {
        return groups;
    }

    public StudentGroup findGroupByName(String name) {
        for (StudentGroup group : groups) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    // ===== КІМНАТИ / ГУРТОЖИТОК =====

    public void addRoom(ClassRoom room) {
        rooms.add(room);
    }

    public void removeRoom(ClassRoom room) {
        rooms.remove(room);
    }

    public List<ClassRoom> getAllRooms() {
        return rooms;
    }

    public ClassRoom findRoomByNumber(int roomNumber) {
        for (ClassRoom room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public boolean settleStudent(int studentId, int roomNumber) {
        Student student = findStudentById(studentId);
        ClassRoom room = findRoomByNumber(roomNumber);

        if (student == null || room == null) {
            return false;
        }

        if (student.isLivingInDormitory()) {
            return false;
        }

        return room.addResident(student);
    }

    public void evictStudent(int studentId) {
        Student student = findStudentById(studentId);

        if (student == null) {
            return;
        }

        for (ClassRoom room : rooms) {
            if (room.getResidents().contains(student)) {
                room.removeResident(student);
                return;
            }
        }
    }
}
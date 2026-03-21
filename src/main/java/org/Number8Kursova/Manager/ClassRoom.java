package org.Number8Kursova.Manager;

import java.util.ArrayList;
import java.util.List;


public class ClassRoom {
    private int roomNumber;
    private int capacity;
    private List<Student> residents;

    public ClassRoom(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.residents = new ArrayList<>();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Student> getResidents() {
        return residents;
    }

    public boolean hasFreeSpace() {
        return residents.size() < capacity;
    }

    public int getFreePlaces() {
        return capacity - residents.size();
    }

    public boolean addResident(Student student) {
        if (!hasFreeSpace()) {
            return false;
        }

        residents.add(student);
        student.setLivingInDormitory(true);
        student.setRoomNumber(roomNumber);
        return true;
    }

    public void removeResident(Student student) {
        residents.remove(student);
        student.setLivingInDormitory(false);
        student.setRoomNumber(-1);
    }

}
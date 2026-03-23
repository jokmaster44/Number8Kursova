package org.Number8Kursova.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dormitory room.
 *
 * Stores room number, capacity and list of current residents.
 */
public class ClassRoom {

    private int roomNumber;
    private int capacity;
    private List<Student> residents;

    /**
     * Creates a new room with given number and capacity.
     *
     * @param roomNumber room identifier
     * @param capacity maximum number of residents
     */
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

    /**
     * Returns current residents of the room.
     *
     * @return list of students living in the room
     */
    public List<Student> getResidents() {
        return residents;
    }

    /**
     * Checks whether the room still has free places.
     *
     * @return true if capacity is not reached
     */
    public boolean hasFreeSpace() {
        return residents.size() < capacity;
    }

    /**
     * Calculates number of available places.
     *
     * @return free places count
     */
    public int getFreePlaces() {
        return capacity - residents.size();
    }
}
package org.Number8Kursova.Manager;


public class Student {
    private int id;
    private int age;
    private int roomNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String groupName;
    private boolean livingInDormitory;

    public Student(int id, String firstName, String lastName, String middleName,
                   int age, String gender, String groupName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
        this.gender = gender;
        this.groupName = groupName;
        this.livingInDormitory = false;
        this.roomNumber = -1;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isLivingInDormitory() {
        return livingInDormitory;
    }

    public void setLivingInDormitory(boolean livingInDormitory) {
        this.livingInDormitory = livingInDormitory;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " (ID: " + id + ")";
    }
}


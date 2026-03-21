package org.Number8Kursova.Manager;

/**
 * Represents a student of the educational institution.
 *
 * Contains personal data, group assignment and dormitory status.
 */
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

    /**
     * Returns assigned group name.
     *
     * @return group name or empty/null if not assigned
     */
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Indicates whether student lives in dormitory.
     *
     * @return true if student is settled in dormitory
     */
    public boolean isLivingInDormitory() {
        return livingInDormitory;
    }

    public void setLivingInDormitory(boolean livingInDormitory) {
        this.livingInDormitory = livingInDormitory;
    }

    /**
     * Returns dormitory room number.
     *
     * @return room number or -1 if not assigned
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Returns short textual representation of student.
     *
     * Used in lists and dialogs.
     */
    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " (ID: " + id + ")";
    }
}
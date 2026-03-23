package org.Number8Kursova.Manager;

import org.Number8Kursova.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Central manager responsible for application data operations.
 *
 * All main entities are loaded and updated through SQLite.
 */
public class DeaneryManager {

    /**
     * Generates a new student ID.
     *
     * @return next available ID
     */
    public int generateStudentId() {
        String sql = "SELECT MAX(id) FROM students";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int maxId = resultSet.getInt(1);
            return maxId + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * Adds a new student to database.
     *
     * @param student student to save
     */
    public void addStudent(Student student) {
        String sql = """
                INSERT INTO students
                (id, first_name, last_name, middle_name, age, gender, group_name, living_in_dormitory, room_number)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, student.getId());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getMiddleName());
            ps.setInt(5, student.getAge());
            ps.setString(6, student.getGender());
            ps.setString(7, student.getGroupName());
            ps.setInt(8, student.isLivingInDormitory() ? 1 : 0);
            ps.setInt(9, student.getRoomNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a student from database.
     *
     * @param student student to delete
     */
    public void removeStudent(Student student) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, student.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates student data in database.
     *
     * @param student updated student
     */
    public void updateStudent(Student student) {
        String sql = """
                UPDATE students
                SET first_name = ?, last_name = ?, middle_name = ?, age = ?, gender = ?, group_name = ?,
                    living_in_dormitory = ?, room_number = ?
                WHERE id = ?
                """;

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getMiddleName());
            ps.setInt(4, student.getAge());
            ps.setString(5, student.getGender());
            ps.setString(6, student.getGroupName());
            ps.setInt(7, student.isLivingInDormitory() ? 1 : 0);
            ps.setInt(8, student.getRoomNumber());
            ps.setInt(9, student.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all students from database.
     *
     * @return list of students
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    /**
     * Finds a student by ID.
     *
     * @param id student ID
     * @return found student or null
     */
    public Student findStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns students from a specific group.
     *
     * @param groupName group name
     * @return list of students
     */
    public List<Student> getStudentsByGroupName(String groupName) {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE LOWER(group_name) = LOWER(?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, groupName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapStudent(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Returns students living in dormitory.
     *
     * @return list of dormitory students
     */
    public List<Student> getDormitoryStudents() {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE living_in_dormitory = 1";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Returns students not living in dormitory.
     *
     * @return list of students
     */
    public List<Student> getStudentsNotInDormitory() {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE living_in_dormitory = 0";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Returns students without assigned group.
     *
     * @return list of students
     */
    public List<Student> getStudentsWithoutGroup() {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE group_name IS NULL OR TRIM(group_name) = ''";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Adds a group to the database.
     *
     * @param group group to save
     */
    public void addGroup(StudentGroup group) {
        String sql = "INSERT INTO student_groups (name, course, specialty) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, group.getName());
            ps.setInt(2, group.getCourse());
            ps.setString(3, group.getSpecialty());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a group from the database.
     *
     * Group references in students are also cleared.
     *
     * @param group group to remove
     */
    public void removeGroup(StudentGroup group) {
        String clearStudentsSql = "UPDATE students SET group_name = '' WHERE LOWER(group_name) = LOWER(?)";
        String deleteGroupSql = "DELETE FROM student_groups WHERE name = ?";

        try (Connection connection = DatabaseManager.connect()) {
            connection.setAutoCommit(false);

            try (PreparedStatement clearPs = connection.prepareStatement(clearStudentsSql);
                 PreparedStatement deletePs = connection.prepareStatement(deleteGroupSql)) {

                clearPs.setString(1, group.getName());
                clearPs.executeUpdate();

                deletePs.setString(1, group.getName());
                deletePs.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates group data in database.
     *
     * If the group name changes, student references are updated too.
     *
     * @param oldName previous group name
     * @param group updated group
     */
    public void updateGroup(String oldName, StudentGroup group) {
        String updateGroupSql = """
                UPDATE student_groups
                SET name = ?, course = ?, specialty = ?
                WHERE name = ?
                """;

        String updateStudentsSql = """
                UPDATE students
                SET group_name = ?
                WHERE LOWER(group_name) = LOWER(?)
                """;

        try (Connection connection = DatabaseManager.connect()) {
            connection.setAutoCommit(false);

            try (PreparedStatement groupPs = connection.prepareStatement(updateGroupSql);
                 PreparedStatement studentsPs = connection.prepareStatement(updateStudentsSql)) {

                groupPs.setString(1, group.getName());
                groupPs.setInt(2, group.getCourse());
                groupPs.setString(3, group.getSpecialty());
                groupPs.setString(4, oldName);
                groupPs.executeUpdate();

                studentsPs.setString(1, group.getName());
                studentsPs.setString(2, oldName);
                studentsPs.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns a student to the specified group.
     *
     * @param studentId student ID
     * @param groupName target group name
     */
    public void assignStudentToGroup(int studentId, String groupName) {
        String sql = "UPDATE students SET group_name = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, groupName);
            ps.setInt(2, studentId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a student from the assigned group.
     *
     * @param studentId student ID
     */
    public void removeStudentFromGroup(int studentId) {
        String sql = "UPDATE students SET group_name = '' WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all groups from the database.
     *
     * Each group is additionally filled with its students.
     *
     * @return list of groups
     */
    public List<StudentGroup> getAllGroups() {
        List<StudentGroup> groups = new ArrayList<>();
        String sql = "SELECT * FROM student_groups";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                StudentGroup group = new StudentGroup(
                        rs.getString("name"),
                        rs.getInt("course"),
                        rs.getString("specialty")
                );

                List<Student> studentsInGroup = getStudentsByGroupName(group.getName());
                group.getStudents().addAll(studentsInGroup);

                groups.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    /**
     * Finds a group by its name.
     *
     * Found group is additionally filled with its students.
     *
     * @param name group name
     * @return found group or null
     */
    public StudentGroup findGroupByName(String name) {
        String sql = "SELECT * FROM student_groups WHERE LOWER(name) = LOWER(?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StudentGroup group = new StudentGroup(
                            rs.getString("name"),
                            rs.getInt("course"),
                            rs.getString("specialty")
                    );

                    List<Student> studentsInGroup = getStudentsByGroupName(group.getName());
                    group.getStudents().addAll(studentsInGroup);

                    return group;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a room to the database.
     *
     * @param room room to save
     */
    public void addRoom(ClassRoom room) {
        String sql = "INSERT INTO rooms (room_number, capacity) VALUES (?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, room.getRoomNumber());
            ps.setInt(2, room.getCapacity());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a room from the database.
     *
     * Students from this room are also marked as not living in dormitory.
     *
     * @param room room to remove
     */
    public void removeRoom(ClassRoom room) {
        String clearStudentsSql = """
                UPDATE students
                SET living_in_dormitory = 0, room_number = -1
                WHERE room_number = ?
                """;

        String deleteRoomSql = "DELETE FROM rooms WHERE room_number = ?";

        try (Connection connection = DatabaseManager.connect()) {
            connection.setAutoCommit(false);

            try (PreparedStatement clearPs = connection.prepareStatement(clearStudentsSql);
                 PreparedStatement deletePs = connection.prepareStatement(deleteRoomSql)) {

                clearPs.setInt(1, room.getRoomNumber());
                clearPs.executeUpdate();

                deletePs.setInt(1, room.getRoomNumber());
                deletePs.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates room data in the database.
     *
     * If the room number changes, student references are updated too.
     *
     * @param oldRoomNumber previous room number
     * @param room updated room data
     */
    public void updateRoom(int oldRoomNumber, ClassRoom room) {
        String updateRoomSql = """
                UPDATE rooms
                SET room_number = ?, capacity = ?
                WHERE room_number = ?
                """;

        String updateStudentsSql = """
                UPDATE students
                SET room_number = ?
                WHERE room_number = ?
                """;

        try (Connection connection = DatabaseManager.connect()) {
            connection.setAutoCommit(false);

            try (PreparedStatement roomPs = connection.prepareStatement(updateRoomSql);
                 PreparedStatement studentsPs = connection.prepareStatement(updateStudentsSql)) {

                roomPs.setInt(1, room.getRoomNumber());
                roomPs.setInt(2, room.getCapacity());
                roomPs.setInt(3, oldRoomNumber);
                roomPs.executeUpdate();

                studentsPs.setInt(1, room.getRoomNumber());
                studentsPs.setInt(2, oldRoomNumber);
                studentsPs.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all rooms from the database.
     *
     * Each room is additionally filled with current residents.
     *
     * @return list of rooms
     */
    public List<ClassRoom> getAllRooms() {
        List<ClassRoom> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                ClassRoom room = new ClassRoom(
                        rs.getInt("room_number"),
                        rs.getInt("capacity")
                );

                List<Student> residents = getStudentsByRoomNumber(room.getRoomNumber());
                room.getResidents().addAll(residents);

                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    /**
     * Finds a room by its number.
     *
     * Found room is additionally filled with current residents.
     *
     * @param roomNumber room number
     * @return found room or null
     */
    public ClassRoom findRoomByNumber(int roomNumber) {
        String sql = "SELECT * FROM rooms WHERE room_number = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roomNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClassRoom room = new ClassRoom(
                            rs.getInt("room_number"),
                            rs.getInt("capacity")
                    );

                    List<Student> residents = getStudentsByRoomNumber(room.getRoomNumber());
                    room.getResidents().addAll(residents);

                    return room;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns students living in the specified room.
     *
     * @param roomNumber room number
     * @return list of residents
     */
    public List<Student> getStudentsByRoomNumber(int roomNumber) {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE room_number = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roomNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapStudent(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Settles a student into the specified room.
     *
     * @param studentId student ID
     * @param roomNumber room number
     * @return true if settlement was successful
     */
    public boolean settleStudent(int studentId, int roomNumber) {
        Student student = findStudentById(studentId);
        ClassRoom room = findRoomByNumber(roomNumber);

        if (student == null || room == null) {
            return false;
        }

        if (student.isLivingInDormitory()) {
            return false;
        }

        if (!room.hasFreeSpace()) {
            return false;
        }

        String sql = """
                UPDATE students
                SET living_in_dormitory = 1, room_number = ?
                WHERE id = ?
                """;

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roomNumber);
            ps.setInt(2, studentId);
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Evicts a student from dormitory.
     *
     * @param studentId student ID
     */
    public void evictStudent(int studentId) {
        String sql = """
                UPDATE students
                SET living_in_dormitory = 0, room_number = -1
                WHERE id = ?
                """;

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts current result set row into Student object.
     *
     * @param rs result set positioned on student row
     * @return mapped student object
     * @throws SQLException if reading fails
     */
    private Student mapStudent(ResultSet rs) throws SQLException {
        Student student = new Student(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("middle_name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("group_name")
        );

        student.setLivingInDormitory(rs.getInt("living_in_dormitory") == 1);
        student.setRoomNumber(rs.getInt("room_number"));

        return student;
    }
}
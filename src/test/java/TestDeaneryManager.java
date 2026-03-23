package org.Number8Kursova;

import org.Number8Kursova.Manager.ClassRoom;
import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.Manager.StudentGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for DeaneryManager.
 *
 * These tests verify that:
 * - students can be added, updated, found and removed
 * - groups can be added, updated, found and removed
 * - students can be assigned to and removed from groups
 * - rooms can be added, updated, found and removed
 * - students can be settled into and evicted from dormitory rooms
 * - search-related methods return correct results
 *
 * Tests use a real SQLite database, but the database is reset
 * before each test to ensure clean and isolated state.
 */
public class TestDeaneryManager {

    private DeaneryManager manager;

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetDatabase();
        manager = new DeaneryManager();
    }

    /**
     * Testcase: Should generate positive student ID.
     *
     * Steps:
     * 1. Call generateStudentId().
     *
     * Result state:
     * - Returned ID is greater than zero.
     */
    @Test
    void testGenerateStudentId_should_return_positive_id() {

        // Arrange

        // Act
        int id = manager.generateStudentId();

        // Assert
        assertTrue(id > 0);
    }

    /**
     * Testcase: Should add student and return it in student list.
     *
     * Steps:
     * 1. Create a student.
     * 2. Add student using DeaneryManager.
     * 3. Request all students.
     *
     * Result state:
     * - Student list contains one student.
     * - Added student data is stored correctly.
     */
    @Test
    void testAddStudent_should_store_student_in_database() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Іван",
                "Петренко",
                "Олегович",
                18,
                "Чоловіча",
                ""
        );

        // Act
        manager.addStudent(student);
        List<Student> students = manager.getAllStudents();

        // Assert
        assertEquals(1, students.size());
        assertEquals("Іван", students.get(0).getFirstName());
        assertEquals("Петренко", students.get(0).getLastName());
    }

    /**
     * Testcase: Should find student by ID.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Search student using ID.
     *
     * Result state:
     * - Student is found.
     * - Returned student has expected ID.
     */
    @Test
    void testFindStudentById_should_return_existing_student() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Марія",
                "Коваленко",
                "Іванівна",
                19,
                "Жіноча",
                ""
        );
        manager.addStudent(student);

        // Act
        Student foundStudent = manager.findStudentById(student.getId());

        // Assert
        assertNotNull(foundStudent);
        assertEquals(student.getId(), foundStudent.getId());
        assertEquals("Марія", foundStudent.getFirstName());
    }

    /**
     * Testcase: Should update student data.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Change student fields.
     * 3. Save updated student.
     * 4. Load student again.
     *
     * Result state:
     * - Updated values are stored in database.
     */
    @Test
    void testUpdateStudent_should_change_student_data() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Олег",
                "Сидоренко",
                "Петрович",
                20,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);

        student.setFirstName("Андрій");
        student.setAge(21);

        // Act
        manager.updateStudent(student);
        Student updatedStudent = manager.findStudentById(student.getId());

        // Assert
        assertNotNull(updatedStudent);
        assertEquals("Андрій", updatedStudent.getFirstName());
        assertEquals(21, updatedStudent.getAge());
    }

    /**
     * Testcase: Should remove student from database.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Remove student.
     * 3. Request all students.
     *
     * Result state:
     * - Student list becomes empty.
     */
    @Test
    void testRemoveStudent_should_delete_student_from_database() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Ірина",
                "Шевченко",
                "Олексіївна",
                18,
                "Жіноча",
                ""
        );
        manager.addStudent(student);

        // Act
        manager.removeStudent(student);
        List<Student> students = manager.getAllStudents();

        // Assert
        assertTrue(students.isEmpty());
    }

    /**
     * Testcase: Should add group and return it in group list.
     *
     * Steps:
     * 1. Create group.
     * 2. Add group.
     * 3. Request all groups.
     *
     * Result state:
     * - Group list contains one group.
     */
    @Test
    void testAddGroup_should_store_group_in_database() {

        // Arrange
        StudentGroup group = new StudentGroup("ІПЗ-21", 2, "Інженерія програмного забезпечення");

        // Act
        manager.addGroup(group);
        List<StudentGroup> groups = manager.getAllGroups();

        // Assert
        assertEquals(1, groups.size());
        assertEquals("ІПЗ-21", groups.get(0).getName());
    }

    /**
     * Testcase: Should find group by name.
     *
     * Steps:
     * 1. Create and save group.
     * 2. Search group by name.
     *
     * Result state:
     * - Group is found.
     * - Group data matches expected values.
     */
    @Test
    void testFindGroupByName_should_return_existing_group() {

        // Arrange
        StudentGroup group = new StudentGroup("КН-22", 3, "Комп'ютерні науки");
        manager.addGroup(group);

        // Act
        StudentGroup foundGroup = manager.findGroupByName("КН-22");

        // Assert
        assertNotNull(foundGroup);
        assertEquals("КН-22", foundGroup.getName());
        assertEquals(3, foundGroup.getCourse());
    }

    /**
     * Testcase: Should update group data.
     *
     * Steps:
     * 1. Create and save group.
     * 2. Change group data.
     * 3. Save updated group.
     * 4. Load group again.
     *
     * Result state:
     * - Updated group data is stored.
     */
    @Test
    void testUpdateGroup_should_change_group_data() {

        // Arrange
        StudentGroup group = new StudentGroup("ІПЗ-21", 2, "ІПЗ");
        manager.addGroup(group);

        group.setName("ІПЗ-22");
        group.setCourse(3);
        group.setSpecialty("Оновлена спеціальність");

        // Act
        manager.updateGroup("ІПЗ-21", group);
        StudentGroup updatedGroup = manager.findGroupByName("ІПЗ-22");

        // Assert
        assertNotNull(updatedGroup);
        assertEquals("ІПЗ-22", updatedGroup.getName());
        assertEquals(3, updatedGroup.getCourse());
        assertEquals("Оновлена спеціальність", updatedGroup.getSpecialty());
    }

    /**
     * Testcase: Should remove group from database.
     *
     * Steps:
     * 1. Create and save group.
     * 2. Remove group.
     * 3. Request all groups.
     *
     * Result state:
     * - Group list becomes empty.
     */
    @Test
    void testRemoveGroup_should_delete_group_from_database() {

        // Arrange
        StudentGroup group = new StudentGroup("ІПЗ-21", 2, "ІПЗ");
        manager.addGroup(group);

        // Act
        manager.removeGroup(group);
        List<StudentGroup> groups = manager.getAllGroups();

        // Assert
        assertTrue(groups.isEmpty());
    }

    /**
     * Testcase: Should assign student to group.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Create and save group.
     * 3. Assign student to group.
     * 4. Request students of that group.
     *
     * Result state:
     * - Group contains assigned student.
     */
    @Test
    void testAssignStudentToGroup_should_update_student_group_name() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Сергій",
                "Бондар",
                "Ігорович",
                19,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);

        StudentGroup group = new StudentGroup("ІПЗ-21", 2, "ІПЗ");
        manager.addGroup(group);

        // Act
        manager.assignStudentToGroup(student.getId(), group.getName());
        List<Student> studentsInGroup = manager.getStudentsByGroupName("ІПЗ-21");

        // Assert
        assertEquals(1, studentsInGroup.size());
        assertEquals(student.getId(), studentsInGroup.get(0).getId());
    }

    /**
     * Testcase: Should remove student from group.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Create and save group.
     * 3. Assign student to group.
     * 4. Remove student from group.
     * 5. Request students without group.
     *
     * Result state:
     * - Student is no longer assigned to any group.
     */
    @Test
    void testRemoveStudentFromGroup_should_clear_group_name() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Анна",
                "Мельник",
                "Юріївна",
                18,
                "Жіноча",
                ""
        );
        manager.addStudent(student);

        StudentGroup group = new StudentGroup("ІПЗ-21", 2, "ІПЗ");
        manager.addGroup(group);
        manager.assignStudentToGroup(student.getId(), group.getName());

        // Act
        manager.removeStudentFromGroup(student.getId());
        List<Student> studentsWithoutGroup = manager.getStudentsWithoutGroup();

        // Assert
        assertEquals(1, studentsWithoutGroup.size());
        assertEquals(student.getId(), studentsWithoutGroup.get(0).getId());
    }

    /**
     * Testcase: Should add room and return it in room list.
     *
     * Steps:
     * 1. Create room.
     * 2. Add room.
     * 3. Request all rooms.
     *
     * Result state:
     * - Room list contains one room.
     */
    @Test
    void testAddRoom_should_store_room_in_database() {

        // Arrange
        ClassRoom room = new ClassRoom(101, 3);

        // Act
        manager.addRoom(room);
        List<ClassRoom> rooms = manager.getAllRooms();

        // Assert
        assertEquals(1, rooms.size());
        assertEquals(101, rooms.get(0).getRoomNumber());
    }

    /**
     * Testcase: Should find room by number.
     *
     * Steps:
     * 1. Create and save room.
     * 2. Search room by number.
     *
     * Result state:
     * - Room is found.
     * - Room number matches expected value.
     */
    @Test
    void testFindRoomByNumber_should_return_existing_room() {

        // Arrange
        ClassRoom room = new ClassRoom(202, 2);
        manager.addRoom(room);

        // Act
        ClassRoom foundRoom = manager.findRoomByNumber(202);

        // Assert
        assertNotNull(foundRoom);
        assertEquals(202, foundRoom.getRoomNumber());
        assertEquals(2, foundRoom.getCapacity());
    }

    /**
     * Testcase: Should update room data.
     *
     * Steps:
     * 1. Create and save room.
     * 2. Change room data.
     * 3. Save updated room.
     * 4. Load room again.
     *
     * Result state:
     * - Updated room values are stored.
     */
    @Test
    void testUpdateRoom_should_change_room_data() {

        // Arrange
        ClassRoom room = new ClassRoom(101, 2);
        manager.addRoom(room);

        room.setRoomNumber(102);
        room.setCapacity(4);

        // Act
        manager.updateRoom(101, room);
        ClassRoom updatedRoom = manager.findRoomByNumber(102);

        // Assert
        assertNotNull(updatedRoom);
        assertEquals(102, updatedRoom.getRoomNumber());
        assertEquals(4, updatedRoom.getCapacity());
    }

    /**
     * Testcase: Should remove room from database.
     *
     * Steps:
     * 1. Create and save room.
     * 2. Remove room.
     * 3. Request all rooms.
     *
     * Result state:
     * - Room list becomes empty.
     */
    @Test
    void testRemoveRoom_should_delete_room_from_database() {

        // Arrange
        ClassRoom room = new ClassRoom(101, 3);
        manager.addRoom(room);

        // Act
        manager.removeRoom(room);
        List<ClassRoom> rooms = manager.getAllRooms();

        // Assert
        assertTrue(rooms.isEmpty());
    }

    /**
     * Testcase: Should settle student into room.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Create and save room.
     * 3. Settle student into room.
     * 4. Load student again.
     *
     * Result state:
     * - Settlement returns true.
     * - Student is marked as living in dormitory.
     * - Student room number is updated.
     */
    @Test
    void testSettleStudent_should_update_dormitory_status() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Олександр",
                "Гнатюк",
                "Вікторович",
                20,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);
        manager.addRoom(new ClassRoom(101, 2));

        // Act
        boolean settled = manager.settleStudent(student.getId(), 101);
        Student updatedStudent = manager.findStudentById(student.getId());

        // Assert
        assertTrue(settled);
        assertNotNull(updatedStudent);
        assertTrue(updatedStudent.isLivingInDormitory());
        assertEquals(101, updatedStudent.getRoomNumber());
    }

    /**
     * Testcase: Should not settle student into full room.
     *
     * Steps:
     * 1. Create two students.
     * 2. Create room with capacity 1.
     * 3. Settle first student.
     * 4. Try to settle second student into same room.
     *
     * Result state:
     * - Second settlement returns false.
     */
    @Test
    void testSettleStudent_should_fail_when_room_is_full() {

        // Arrange
        Student firstStudent = new Student(
                manager.generateStudentId(),
                "Іван",
                "Петренко",
                "Олегович",
                18,
                "Чоловіча",
                ""
        );

        Student secondStudent = new Student(
                manager.generateStudentId(),
                "Марія",
                "Коваленко",
                "Іванівна",
                19,
                "Жіноча",
                ""
        );

        manager.addStudent(firstStudent);
        manager.addStudent(secondStudent);
        manager.addRoom(new ClassRoom(101, 1));
        manager.settleStudent(firstStudent.getId(), 101);

        // Act
        boolean secondSettlementResult = manager.settleStudent(secondStudent.getId(), 101);

        // Assert
        assertFalse(secondSettlementResult);
    }

    /**
     * Testcase: Should evict student from dormitory.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Create and save room.
     * 3. Settle student.
     * 4. Evict student.
     * 5. Load student again.
     *
     * Result state:
     * - Student is no longer living in dormitory.
     * - Room number becomes -1.
     */
    @Test
    void testEvictStudent_should_clear_dormitory_status() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Юлія",
                "Савчук",
                "Ігорівна",
                18,
                "Жіноча",
                ""
        );
        manager.addStudent(student);
        manager.addRoom(new ClassRoom(101, 2));
        manager.settleStudent(student.getId(), 101);

        // Act
        manager.evictStudent(student.getId());
        Student updatedStudent = manager.findStudentById(student.getId());

        // Assert
        assertNotNull(updatedStudent);
        assertFalse(updatedStudent.isLivingInDormitory());
        assertEquals(-1, updatedStudent.getRoomNumber());
    }

    /**
     * Testcase: Should return students living in dormitory.
     *
     * Steps:
     * 1. Create and save student.
     * 2. Create room and settle student.
     * 3. Request dormitory students.
     *
     * Result state:
     * - Returned list contains settled student.
     */
    @Test
    void testGetDormitoryStudents_should_return_only_residents() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Артем",
                "Лисенко",
                "Васильович",
                20,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);
        manager.addRoom(new ClassRoom(301, 2));
        manager.settleStudent(student.getId(), 301);

        // Act
        List<Student> dormitoryStudents = manager.getDormitoryStudents();

        // Assert
        assertEquals(1, dormitoryStudents.size());
        assertEquals(student.getId(), dormitoryStudents.get(0).getId());
    }

    /**
     * Testcase: Should return students without group.
     *
     * Steps:
     * 1. Create and save one student without group.
     * 2. Request students without group.
     *
     * Result state:
     * - Returned list contains the created student.
     */
    @Test
    void testGetStudentsWithoutGroup_should_return_unassigned_students() {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Наталія",
                "Ткаченко",
                "Вікторівна",
                17,
                "Жіноча",
                ""
        );
        manager.addStudent(student);

        // Act
        List<Student> studentsWithoutGroup = manager.getStudentsWithoutGroup();

        // Assert
        assertEquals(1, studentsWithoutGroup.size());
        assertEquals(student.getId(), studentsWithoutGroup.get(0).getId());
    }
}
package dao;


import org.junit.*;
import pojo.Student;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StudentDaoTest {

    private static final StudentDaoImpl studentDao = new StudentDaoImpl();
    private static final Student testStudent = new Student("Ivan", "Petrenko", "A3");
    private static final Student anotherStudent = new Student("Grisha", "Far", "A1");
    private static final String VALID_NAME = "Ivan";
    private static final String INVALID_NAME = "Vitya";
    private static final int ID_OF_TEST_STUDENT = 1;
    private static final String DROP_TABLE = "DROP table students";
    private static final String CREATE_TABLE = "CREATE TABLE students(" + "id int Auto_increment " +
            "primary key," + "name varchar (255) not null," + "second_name varchar(255) not null," +
            "group_name varchar(255) not null" + ");";

    @Before
    public void setUpBefore() {
        ConnectionPool.executeUpdate(CREATE_TABLE);
    }

    @After
    public void setUpAfter() {
        ConnectionPool.executeUpdate(DROP_TABLE);
    }

    @Test
    public void insertStudentMethodShouldAddStudentToDataBase() {
        testStudent.setId(ID_OF_TEST_STUDENT);
        studentDao.insertStudent(testStudent);
        assertEquals(testStudent, studentDao.getStudentsByName(VALID_NAME).get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertStudentMethodShouldThrowIAEX() {
        studentDao.insertStudent(null);
    }

    @Test
    public void deleteMethodShouldDeleteStudentFromDataBase() {
        studentDao.insertStudent(testStudent);
        studentDao.deleteStudent(testStudent);
    }

    @Test
    public void getIdMethodShouldReturnIdByNameAndSecondNameOfStudent() {
        studentDao.insertStudent(testStudent);
        assertEquals(ID_OF_TEST_STUDENT, studentDao.getStudentIdByStudent(testStudent));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getIdMethodShouldThrowIlAEIfNoStudentWithCurrentParameters() {
        assertNotNull(studentDao.getStudentIdByStudent(anotherStudent));
    }

    @Test
    public void identificationMethodShouldReturnTrueIfStudentIsPresentInDataBase() {
        testStudent.setId(ID_OF_TEST_STUDENT);
        studentDao.insertStudent(testStudent);
        assertTrue(studentDao.identification(testStudent));
    }

    @Test
    public void getStudentsByNameShouldReturnListOfStudents() {
        studentDao.insertStudent(testStudent);
        assertNotNull(studentDao.getStudentsByName(VALID_NAME));
    }

    @Test(expected = NoSuchElementException.class)
    public void getStudentsByNameShouldThrowNoSuchElExIfNoStudentsWithSuchName() {
        studentDao.insertStudent(testStudent);
        studentDao.getStudentsByName(INVALID_NAME);
    }

    @Test
    public void getStudentByIdShouldReturnStudent(){
        testStudent.setId(ID_OF_TEST_STUDENT);
        studentDao.insertStudent(testStudent);
        assertEquals(testStudent,studentDao.getStudentById(ID_OF_TEST_STUDENT));
    }

    @Test(expected = NoSuchElementException.class)
    public void getStudentByIdShouldThrowINoSElExIfNoStudentWithSuchId(){
        testStudent.setId(ID_OF_TEST_STUDENT);
        studentDao.insertStudent(testStudent);
        studentDao.getStudentById(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStudentByIdShouldThrowIlExIfIdLessZero(){
        studentDao.getStudentById(0);
    }
}

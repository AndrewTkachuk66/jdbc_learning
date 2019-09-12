package dao;

import pojo.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StudentDaoImpl implements StudentDao {

    public void insertStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Your student is null - " + student);
        String name = student.getName();
        String secondName = student.getSecondName();
        String group = student.getGroup();
        String insertQuery = "INSERT INTO students (name, second_name, group_name)" +
                "Values ('" + name + "','" + secondName + "','" + group + "')";
        ConnectionPool.executeUpdate(insertQuery);
    }

    public void deleteStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Your student is null" + student);
        if (identification(student)) {
            int id = getStudentIdByStudent(student);
            String deleteQuery = "DELETE FROM students WHERE id = " + id;
            ConnectionPool.executeUpdate(deleteQuery);
        }
    }

    public Student getStudentById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id should be more then zero: " + id);
        String getStudentQuery = "SELECT * FROM students WHERE id = " + id;
        ResultSet resultSet = ConnectionPool.executeQuery(getStudentQuery);
        Student student = null;
        try {
            while (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSecondName(resultSet.getString("second_name"));
                student.setGroup(resultSet.getString("group_name"));
            }
            if (student == null) throw new NoSuchElementException("No student with such id - " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public int getStudentIdByStudent(Student student) {
        String getIdQuery = "SELECT id FROM students WHERE name = '" + student.getName() + "'" +
                " and second_name = '" + student.getSecondName() + "'";
        int id = 0;
        try (ResultSet resultSet = ConnectionPool.executeQuery(getIdQuery)) {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            if (id == 0) throw new IllegalArgumentException("No student with such name -" + student.getName()
                    + "second_name - " + student.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean identification(Student student) {
        if (student.getId() == getStudentIdByStudent(student))
            return true;
        return false;
    }

    public List<Student> getStudentsByName(String name) {
        String getStudentsByNameQuery = "SELECT * FROM students WHERE name = '" + name + "'";
        List<Student> students = new ArrayList<>();
        try (ResultSet resultSet = ConnectionPool.executeQuery(getStudentsByNameQuery)) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSecondName(resultSet.getString("second_name"));
                student.setGroup(resultSet.getString("group_name"));
                students.add(student);
            }
            if (students.isEmpty())
                throw new NoSuchElementException("No students with such name - " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

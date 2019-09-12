package dao;

import pojo.Student;

import java.util.List;

public interface StudentDao {
    void insertStudent(Student student);

    void deleteStudent(Student student);

    Student getStudentById(Integer id);

    List<Student> getStudentsByName(String name);
}

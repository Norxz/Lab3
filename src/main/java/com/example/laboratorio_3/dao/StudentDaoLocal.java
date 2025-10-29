package com.example.laboratorio_3.dao;

import com.example.laboratorio_3.model.Student;
import java.util.List;
import jakarta.ejb.Local;

@Local
public interface StudentDaoLocal {
    void addStudent(Student student);
    void editStudent(Student student);
    void deleteStudent(int studentId);
    Student getStudent(int studentId);
    List<Student> getAllStudents();
}

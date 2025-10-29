package com.example.laboratorio_3.dao;

import com.example.laboratorio_3.model.Course;
import java.util.List;
import jakarta.ejb.Local;

@Local
public interface CourseDaoLocal {
    void addCourse(Course course);
    void editCourse(Course course);
    void deleteCourse(int courseCode);
    Course getCourse(int courseCode);
    List<Course> getAllCourses();
}

package com.example.laboratorio_3.dao;

import com.example.laboratorio_3.model.Course;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class CourseDao implements CourseDaoLocal {
    
    @PersistenceContext(unitName = "Lab3PU")
    private EntityManager em;
    
    @Override
    public void addCourse(Course course) { em.persist(course); }
    
    @Override
    public void editCourse(Course course) { em.merge(course); }
    
    @Override
    public void deleteCourse(int courseCode) {
        Course course = getCourse(courseCode);
        if (course != null) {
            // Vaciar la lista de estudiantes asociados para romper la relación
            course.getStudents().clear();
            
            // Ahora sí eliminar el curso
            em.remove(em.contains(course) ? course : em.merge(course));
        }
    }
    
    @Override
    public Course getCourse(int courseCode) {
        return em.find(Course.class, courseCode);
    }
    
    @Override
    public List<Course> getAllCourses() {
        return em.createNamedQuery("Course.getAll", Course.class).getResultList();
    }
}

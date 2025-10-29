package com.example.laboratorio_3.dao;

import com.example.laboratorio_3.model.Student;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StudentDao implements StudentDaoLocal {
    
    @PersistenceContext(unitName = "Lab3PU")
    private EntityManager em;
    
    @Override
    public void addStudent(Student student) {
        // ✅ persist es para nuevas entidades
        em.persist(student);
    }
    
    @Override
    public void editStudent(Student student) {
        // ✅ merge solo para actualizar entidades existentes
        em.merge(student);
    }
    
    @Override
    public void deleteStudent(int studentId) {
        Student s = em.find(Student.class, studentId);
        if (s != null) {
            em.remove(s);
        }
    }
    
    @Override
    public Student getStudent(int studentId) {
        return em.find(Student.class, studentId);
    }
    
    @Override
    public List<Student> getAllStudents() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }
}

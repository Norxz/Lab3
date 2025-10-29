package com.example.laboratorio_3.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSE")
@NamedQueries({
        @NamedQuery(name = "Course.getAll", query = "SELECT c FROM Course c")
})
public class Course implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_code")
    private int courseCode;
    
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @Column(name = "credits")
    private int credits;
    
    @Column(name = "semester")
    private int semester;
    
    @Column(name = "num_students")
    private int numStudents;
    
    // Relación inversa muchos a muchos
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<> ();
    
    public Course() {}
    
    public Course(String courseName, int credits, int semester, int numStudents) {
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.numStudents = numStudents;
    }
    
    // --- Getters & Setters ---
    public int getCourseCode() { return courseCode; }
    public void setCourseCode(int courseCode) { this.courseCode = courseCode; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
    
    public int getNumStudents() { return numStudents; }
    public void setNumStudents(int numStudents) { this.numStudents = numStudents; }
    
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}

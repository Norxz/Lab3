package com.example.laboratorio_3.controller;

import com.example.laboratorio_3.dao.CourseDaoLocal;
import com.example.laboratorio_3.dao.StudentDaoLocal;
import com.example.laboratorio_3.model.Course;
import com.example.laboratorio_3.model.Student;
import jakarta.ejb.EJB;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "/StudentServlet")
public class StudentServlet extends HttpServlet {
    
    @EJB
    private StudentDaoLocal studentDao; // <-- inyectamos EJB
    
    @EJB
    private CourseDaoLocal courseDao; // Para obtener lista de cursos
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Student> allStudents = studentDao.getAllStudents();
        List<Course> allCourses = courseDao.getAllCourses();
        
        request.setAttribute("allStudents", allStudents);
        request.setAttribute("allCourses", allCourses);
        
        RequestDispatcher rd = request.getRequestDispatcher("studentinfo.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "Add":
                    addStudent(request);
                    break;
                case "Edit":
                    editStudent(request);
                    break;
                case "Delete":
                    deleteStudent(request);
                    break;
                case "Search":
                    searchStudent(request);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
        response.sendRedirect("StudentServlet");
    }
    
    private void addStudent(HttpServletRequest request) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        int yearLevel = Integer.parseInt(request.getParameter("yearLevel"));
        
        Student s = new Student();
        s.setFirstname(firstname);
        s.setLastname(lastname);
        s.setYearLevel(yearLevel);
        
        // Asociar cursos seleccionados
        String[] courseIds = request.getParameterValues("courseIds");
        if (courseIds != null) {
            List<Course> courses = new ArrayList<>();
            for (String id : courseIds) {
                Course c = courseDao.getCourse(Integer.parseInt(id));
                if (c != null) courses.add(c);
            }
            s.setCourses(courses);
        }
        
        studentDao.addStudent(s); // ✅ Persistencia via EJB
    }
    
    private void editStudent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("studentId"));
        Student s = studentDao.getStudent(id);
        if (s != null) {
            s.setFirstname(request.getParameter("firstname"));
            s.setLastname(request.getParameter("lastname"));
            s.setYearLevel(Integer.parseInt(request.getParameter("yearLevel")));
            
            String[] courseIds = request.getParameterValues("courseIds");
            List<Course> courses = new ArrayList<>();
            if (courseIds != null) {
                for (String cid : courseIds) {
                    Course c = courseDao.getCourse(Integer.parseInt(cid));
                    if (c != null) courses.add(c);
                }
            }
            s.setCourses(courses);
            
            studentDao.editStudent(s);
        }
    }
    
    private void deleteStudent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("studentId"));
        studentDao.deleteStudent(id);
    }
    
    private void searchStudent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("studentId"));
        Student s = studentDao.getStudent(id);
        if (s != null) {
            request.setAttribute("studentFound", s);
        }
    }
}


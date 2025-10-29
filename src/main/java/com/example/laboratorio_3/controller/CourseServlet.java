package com.example.laboratorio_3.controller;

import com.example.laboratorio_3.dao.CourseDaoLocal;
import com.example.laboratorio_3.model.Course;
import jakarta.ejb.EJB;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CourseServlet", value = "/CourseServlet")
public class CourseServlet extends HttpServlet {
    
    @EJB
    private CourseDaoLocal courseDao; // <-- inyectamos el EJB
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Course> allCourses = courseDao.getAllCourses();
        request.setAttribute("allCourses", allCourses);
        
        RequestDispatcher rd = request.getRequestDispatcher("courseinfo.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "Add":
                    addCourse(request);
                    break;
                case "Edit":
                    editCourse(request);
                    break;
                case "Delete":
                    deleteCourse(request);
                    break;
                case "Search":
                    searchCourse(request, response);
                    return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
        response.sendRedirect("CourseServlet");
    }
    
    private void addCourse(HttpServletRequest request) {
        String courseName = request.getParameter("courseName");
        int credits = Integer.parseInt(request.getParameter("credits"));
        int semester = Integer.parseInt(request.getParameter("semester"));
        int numStudents = Integer.parseInt(request.getParameter("numStudents"));
        
        Course c = new Course(courseName, credits, semester, numStudents);
        courseDao.addCourse(c); // ✅ delegamos al DAO EJB
    }
    
    private void editCourse(HttpServletRequest request) {
        int courseCode = Integer.parseInt(request.getParameter("courseCode"));
        Course c = courseDao.getCourse(courseCode);
        if (c != null) {
            c.setCourseName(request.getParameter("courseName"));
            c.setCredits(Integer.parseInt(request.getParameter("credits")));
            c.setSemester(Integer.parseInt(request.getParameter("semester")));
            c.setNumStudents(Integer.parseInt(request.getParameter("numStudents")));
            courseDao.editCourse(c); // ✅ persistencia via EJB
        }
    }
    
    private void deleteCourse(HttpServletRequest request) {
        int courseCode = Integer.parseInt(request.getParameter("courseCode"));
        courseDao.deleteCourse(courseCode); // ✅ delegamos al DAO EJB
    }
    
    private void searchCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseCode = Integer.parseInt(request.getParameter("courseCode"));
        Course c = courseDao.getCourse(courseCode);
        if (c != null) {
            request.setAttribute("courseFound", c);
        } else {
            request.setAttribute("courseFound", null);
        }
        
        // Hacemos forward a la JSP para mostrar el resultado
        List<Course> allCourses = courseDao.getAllCourses();
        request.setAttribute("allCourses", allCourses);
        
        RequestDispatcher rd = request.getRequestDispatcher("courseinfo.jsp");
        rd.forward(request, response);
    }
}



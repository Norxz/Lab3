<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.laboratorio_3.model.Course" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Gestión de Cursos</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 40px; background-color: #f7f7f7; }
    h1 { color: #333; }
    table { border-collapse: collapse; width: 90%; margin-top: 20px; background-color: white; }
    th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
    th { background-color: #28a745; color: white; }
    form { background-color: white; padding: 20px; border-radius: 10px; width: 400px; }
    input, button { padding: 8px; margin-top: 5px; width: 100%; }
    .actions { display: flex; gap: 10px; margin-top: 10px; }
    .container { display: flex; gap: 60px; }
  </style>
</head>
<body>

<h1>Gestión de Cursos</h1>

<div class="container">
  <!-- Formulario -->
  <form action="CourseServlet" method="post">
    <label>Código del Curso:</label>
    <input type="text" name="courseCode" placeholder="Solo para editar o eliminar">

    <label>Nombre del Curso:</label>
    <input type="text" name="courseName" placeholder="Ej: Programación I" required>

    <label>Créditos:</label>
    <input type="number" name="credits" required>

    <label>Semestre:</label>
    <input type="number" name="semester" required>

    <label>Número de Estudiantes:</label>
    <input type="number" name="numStudents" required>

    <div class="actions">
      <button type="submit" name="action" value="Add">Agregar</button>
      <button type="submit" name="action" value="Edit">Editar</button>
      <button type="submit" name="action" value="Delete">Eliminar</button>
      <button type="submit" name="action" value="Search">Buscar</button>
    </div>
  </form>

  <!-- Curso encontrado -->
  <c:if test="${not empty courseFound}">
    <h3>Curso Encontrado</h3>
    <table>
      <tr>
        <th>Código</th>
        <th>Nombre</th>
        <th>Créditos</th>
        <th>Semestre</th>
        <th>Estudiantes</th>
      </tr>
      <tr>
        <td>${courseFound.courseCode}</td>
        <td>${courseFound.courseName}</td>
        <td>${courseFound.credits}</td>
        <td>${courseFound.semester}</td>
        <td>${courseFound.numStudents}</td>
      </tr>
    </table>
  </c:if>

  <!-- Tabla de cursos -->
  <div>
    <h3>Lista de Cursos</h3>
    <%
      List<Course> courses = (List<Course>) request.getAttribute("allCourses");
      if (courses != null && !courses.isEmpty()) {
    %>
    <table>
      <tr>
        <th>Código</th>
        <th>Nombre</th>
        <th>Créditos</th>
        <th>Semestre</th>
        <th>Estudiantes</th>
      </tr>
      <% for (Course c : courses) { %>
      <tr>
        <td><%= c.getCourseCode() %></td>
        <td><%= c.getCourseName() %></td>
        <td><%= c.getCredits() %></td>
        <td><%= c.getSemester() %></td>
        <td><%= c.getNumStudents() %></td>
      </tr>
      <% } %>
    </table>
    <% } else { %>
    <p>No hay cursos registrados.</p>
    <% } %>
  </div>
</div>

<br>
<a href="StudentServlet">➡ Ir a gestión de estudiantes</a>

</body>
</html>

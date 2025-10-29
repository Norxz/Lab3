<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.laboratorio_3.model.Student" %>
<%@ page import="com.example.laboratorio_3.model.Course" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Gestión de Estudiantes</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f7f7f7; }
        h1 { color: #333; }
        table { border-collapse: collapse; width: 90%; margin-top: 20px; background-color: white; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #007bff; color: white; }
        form { background-color: white; padding: 20px; border-radius: 10px; width: 400px; }
        input, button, select { padding: 8px; margin-top: 5px; width: 100%; }
        .actions { display: flex; gap: 10px; margin-top: 10px; }
        .container { display: flex; gap: 60px; }
    </style>
</head>
<body>

<h1>Gestión de Estudiantes</h1>

<div class="container">
    <!-- Formulario -->
    <form action="StudentServlet" method="post">
        <label>ID:</label>
        <input type="text" name="studentId" placeholder="ID del estudiante">

        <label>Nombre:</label>
        <input type="text" name="firstname" placeholder="Nombre" required>

        <label>Apellido:</label>
        <input type="text" name="lastname" placeholder="Apellido" required>

        <label>Año:</label>
        <input type="number" name="yearLevel" placeholder="Ej: 1, 2, 3..." required>

        <label>Cursos:</label>
        <select name="courseIds" multiple size="5">
            <c:choose>
                <c:when test="${not empty allCourses}">
                    <c:forEach var="c" items="${allCourses}">
                        <option value="${c.courseCode}">${c.courseName} - ${c.credits} créditos</option>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <option disabled>No hay cursos disponibles</option>
                </c:otherwise>
            </c:choose>
        </select>
        <small>Puedes seleccionar varios con Ctrl + clic</small>

        <div class="actions">
            <button type="submit" name="action" value="Add">Agregar</button>
            <button type="submit" name="action" value="Edit">Editar</button>
            <button type="submit" name="action" value="Delete">Eliminar</button>
            <button type="submit" name="action" value="Search">Buscar</button>
        </div>
    </form>

    <!-- Tabla de resultados -->
    <div>
        <h3>Lista de Estudiantes</h3>
        <%
            List<Student> students = (List<Student>) request.getAttribute("allStudents");
            if (students != null && !students.isEmpty()) {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Año</th>
                <th>Cursos</th>
            </tr>
            <%
                for (Student s : students) {
            %>
            <tr>
                <td><%= s.getStudentId() %></td>
                <td><%= s.getFirstname() %></td>
                <td><%= s.getLastname() %></td>
                <td><%= s.getYearLevel() %></td>
                <td>
                    <%
                        List<Course> cursos = s.getCourses();
                        if (cursos != null && !cursos.isEmpty()) {
                    %>
                    <ul>
                        <% for (Course c : cursos) { %>
                        <li><%= c.getCourseName() %></li>
                        <% } %>
                    </ul>
                    <% } else { %>
                    <i>Sin cursos</i>
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>No hay estudiantes registrados.</p>
        <% } %>
    </div>
</div>

<br>
<a href="courseinfo.jsp">➡ Ir a gestión de cursos</a>

</body>
</html>

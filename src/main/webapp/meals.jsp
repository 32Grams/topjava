
<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Meals Directory</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

</head>
<body>

<div class = "container">

    <h1>Meals Directory</h1>
    <hr/>

    <p>
        <button class = "btn btn-primary" onclick="window.location.href = '/meals-form.jsp'">Add Meal</button>
    </p>

    <table class = "table table-striped table-bordered">

        <tr class = "thead-dark">
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Actions</th>
        </tr>
        <jsp:useBean id="mealsList" class="ru.javawebinar.topjava.web.MealServlet" scope="page">
            <c:forEach var="list" items="${mealsList}">

                <tr>
                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${list.dateTime}" /></td>
                    <td>${list.description}</td>
                    <td>${list.calories}</td>
                    <td>
                        <a href = "${pageContext.request.contextPath}/MealServlet?action=EDIT&id=${list.id}">Edit</a>
                        |
                        <a href = "${pageContext.request.contextPath}/MealServlet?action=DELETE&id=${list.id}">Delete</a>
                    </td>
                </tr>

            </c:forEach>
        </jsp:useBean>
    </table>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
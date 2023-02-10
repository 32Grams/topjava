
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <title>Insert title here</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<div class = "container">

  <h1>Meals Directory</h1>
  <hr/>

  <div class = "row">
    <div class = "col-md-4">
      <form action = "${pageContext.request.contextPath}/MealServlet" method="POST">
        <jsp:useBean id="Meal" scope="session" type="ru.javawebinar.topjava.model.Meal"/>
        <c:set var="meal" value="${Meal}" />
        <div class = "form-group">
          <label>
            <input type = "text" class = "form-control" name = "dateTime" placeholder = "Enter dateTime in form yyyy-MM-dd"
                   value = "${meal.dateTime}"/>
          </label>
        </div>

        <div class="form-group">
          <label>
            <input type = "text" class = "form-control" name = "description" placeholder = "Enter description" value = "${meal.description}"/>
          </label>
        </div>

        <div class = "form-group">
          <label>
            <input type = "date" class = "form-control" name = "calories" value = "${meal.calories}"/>
          </label>
        </div>

        <input type = "hidden" name = "id" value = "${meal.id}"/>

        <button type = "submit" class = "btn btn-primary">Save</button>
      </form>
    </div>
  </div>
  <a href = "${pageContext.request.contextPath}/MealServlet?action=LIST">Back to List</a>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
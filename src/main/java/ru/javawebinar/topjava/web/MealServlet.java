package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.dao.MealsDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.RequestDispatcher;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private List<MealTo> mealsList = MealsUtil.getListMealTo((List<Meal>) new MealsDaoImpl().getMeals());
    private static final long serialVersionUID = 1L;
    RequestDispatcher dispatcher = null;
    MealsDao mealsDao = null;

    public MealServlet() {
        mealsDao = new MealsDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("show list");
            action = "LIST";
        }
        switch (action) {
            case "LIST":
                listMeals(request, response);
                break;
            case "EDIT":
                getSingleMeal(request, response);
                break;
            case "DELETE":
                deleteMeal(request, response);
                break;
            default:
                listMeals(request, response);
                break;
        }
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (mealsDao.deleteMeal(Integer.parseInt(id))) {
            request.setAttribute("NOTIFICATION", "Meal Deleted Successfully!");
        }
        listMeals(request, response);
    }

    private void getSingleMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Meal meal = mealsDao.get(Integer.parseInt(id));
        request.setAttribute("employee", meal);
        dispatcher = request.getRequestDispatcher("/meals-form.jsp");
        dispatcher.forward(request, response);
    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("list", mealsList);
        dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        Meal e = new Meal();
        e.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        e.setDescription(request.getParameter("description"));
        e.setCalories(Integer.parseInt(request.getParameter("calories")));

        if (id.isEmpty() || id == null) {
            if (mealsDao.addMeal(e)) {
                request.setAttribute("NOTIFICATION", "Meal Saved Successfully!");
            }
        } else {
            e.setId(Integer.parseInt(id));
            if (mealsDao.updateMeal(e)) {
                request.setAttribute("NOTIFICATION", "Meal Updated Successfully!");
            }
        }
        listMeals(request, response);
    }
}

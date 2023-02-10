package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealsDao {
    boolean addMeal(Meal meal);
    boolean updateMeal(Meal meal);
    boolean deleteMeal(Integer id);
    Meal get(int parseInt);
}

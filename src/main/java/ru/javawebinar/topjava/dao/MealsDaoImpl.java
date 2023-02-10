package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.*;

public class MealsDaoImpl implements MealsDao{

    private List<Meal> meals;

    {
        meals = new CopyOnWriteArrayList<Meal>(){};

        meals.add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public boolean addMeal(Meal meal) {
        return meals.add(meal);
    }

    @Override
    public boolean updateMeal(Meal meal) {
        int index = 0;
        for(Meal findMeal : meals) {
            if(meal.getId().equals(findMeal.getId())) {
                index = meals.indexOf(findMeal);
                break;
            }
        }
        if(index != 0) {
            meals.add(index, meal);
        }
        return index != 0;
    }

    @Override
    public boolean deleteMeal(Integer id) {
        int index = -1;
        for(Meal meal : meals) {
            if(meal.getId().equals(id)) {
                index = meals.indexOf(meal);
            }
        }
        if(index >= 0) {
            meals.remove(index);
        }
        return index >= 0;
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

//    public static List<Meal> getMeals() {
//        return meals;
//    }

    public Object getMeals() {
        return meals;
    }
}

package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<LocalDate, Integer> mapOfCalories = new HashMap<>();
        List<Integer> indexes = new ArrayList<>();

        for (UserMeal meal : meals) {
            if (!mapOfCalories.containsKey(meal.getDateTime().toLocalDate())) {
                mapOfCalories.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            } else {
                mapOfCalories.put(meal.getDateTime().toLocalDate(),
                        mapOfCalories.get(meal.getDateTime().toLocalDate()) + meal.getCalories());
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                indexes.add(meals.indexOf(meal));
            }
        }
        for (Integer index : indexes) {
            UserMeal buffer = meals.get(index);
            if (mapOfCalories.get(buffer.getDateTime().toLocalDate()) > caloriesPerDay) {
                userMealWithExcessList.add(new UserMealWithExcess(buffer.getDateTime(),
                        buffer.getDescription(), buffer.getCalories(), true));
            } else {
                userMealWithExcessList.add(new UserMealWithExcess(buffer.getDateTime(),
                        buffer.getDescription(), buffer.getCalories(), false));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcessList;
        userMealWithExcessList = meals.stream()
                .map((e) -> {
                    UserMealWithExcess UMWE;
                    if ((meals.stream()
                            .filter(s -> s.getDateTime().toLocalDate().equals(e.getDateTime().toLocalDate()))
                            .map(UserMeal::getCalories))
                            .mapToInt(Integer::intValue)
                            .sum() > caloriesPerDay) {
                        UMWE = new UserMealWithExcess(e.getDateTime(),
                                e.getDescription(), e.getCalories(), true);
                    } else {
                        UMWE = new UserMealWithExcess(e.getDateTime(),
                                e.getDescription(), e.getCalories(), false);
                    }
                    return UMWE;
                })
                .filter((e) -> TimeUtil.isBetweenHalfOpen(e.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        return userMealWithExcessList;
    }
}

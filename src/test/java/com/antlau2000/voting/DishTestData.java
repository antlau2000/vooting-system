package com.antlau2000.voting;

import com.antlau2000.voting.model.Dish;

import java.time.LocalDate;

import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_1;
import static com.antlau2000.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class, "restaurant");

    public static final LocalDate DISH_TEST_DATE = LocalDate.of(2020,8,10);
    public static final int DISH_1_ID = START_SEQ + 13;
    public static final int DISH_2_ID = START_SEQ + 14;
    public static final int DISH_3_ID = START_SEQ + 15;

    public static final Dish DISH_1 = new Dish(DISH_1_ID, DISH_TEST_DATE, "Caprese Burger", 500L, RESTAURANT_1);
    public static final Dish DISH_2 = new Dish(DISH_2_ID, DISH_TEST_DATE, "Borsch", 300L, RESTAURANT_1);
    public static final Dish DISH_3 = new Dish(DISH_3_ID, DISH_TEST_DATE, "New York Cheesecake", 350L, RESTAURANT_1);

    public static Dish getNewDish() {
        return new Dish(null, LocalDate.now(), "New", 1000L, RESTAURANT_1);
    }

    public static Dish getUpdatedDish() {
        Dish updated = new Dish(DISH_1);
        updated.setName("UpdatedName");
        return updated;
    }
}

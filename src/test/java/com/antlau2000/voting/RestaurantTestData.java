package com.antlau2000.voting;

import com.antlau2000.voting.model.Restaurant;

import static com.antlau2000.voting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Restaurant.class, "registered", "dishes", "votes");

    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = START_SEQ + 3;
    public static final int RESTAURANT_3_ID = START_SEQ + 4;
    public static final int RESTAURANT_4_ID = START_SEQ + 5;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "Corner Grill");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "Bluefin");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_3_ID, "McDonalds");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_4_ID, "Caesars Palace");

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdatedRestaurant() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        return updated;
    }
}

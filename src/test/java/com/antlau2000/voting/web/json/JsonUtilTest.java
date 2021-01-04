package com.antlau2000.voting.web.json;

import com.antlau2000.voting.UserTestData;
import com.antlau2000.voting.model.Dish;
import com.antlau2000.voting.model.Restaurant;
import com.antlau2000.voting.model.User;
import com.antlau2000.voting.model.Vote;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.antlau2000.voting.DishTestData.DISH_1;
import static com.antlau2000.voting.DishTestData.DISH_MATCHER;
import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_1;
import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_MATCHER;
import static com.antlau2000.voting.VoteTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {
    @Test
    void readWriteValue() throws Exception {
        String voteJson = JsonUtil.writeValue(VOTE_1);
        System.out.println(voteJson);
        Vote vote = JsonUtil.readValue(voteJson, Vote.class);
        VOTE_LAZY_MATCHER.assertMatch(vote, VOTE_1);

        String dishJson = JsonUtil.writeValue(DISH_1);
        System.out.println(dishJson);
        Dish dish = JsonUtil.readValue(dishJson, Dish.class);
        DISH_MATCHER.assertMatch(dish, DISH_1);

        String restaurantJson = JsonUtil.writeValue(RESTAURANT_1);
        System.out.println(restaurantJson);
        Restaurant restaurant = JsonUtil.readValue(restaurantJson, Restaurant.class);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(VOTES);
        System.out.println(json);
        List<Vote> votes = JsonUtil.readValues(json, Vote.class);
        VOTE_LAZY_MATCHER.assertMatch(votes, VOTES);
    }

    @Test
    void writeOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}

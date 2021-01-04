package com.antlau2000.voting.web.restaurant;

import com.antlau2000.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.antlau2000.voting.DishTestData.DISH_1;
import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_1;
import static com.antlau2000.voting.TestUtil.userHttpBasic;
import static com.antlau2000.voting.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserRestaurantController.REST_URL + '/';

    private static final String DISHES_FULL_REST_URL = UserRestaurantController.REST_URL + UserRestaurantController.DISHES_REST_URL + '/';

    @Test
    @WithAnonymousUser
    void getAllRestaurantsForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(UserRestaurantController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getDishesByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(DISHES_FULL_REST_URL + "by?date=" + DISH_1.getLocalDate(), RESTAURANT_1.id())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getDish() throws Exception {
        perform(MockMvcRequestBuilders.get(DISHES_FULL_REST_URL + DISH_1.id(), RESTAURANT_1.id())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getRestaurantsByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by?date=" + DISH_1.getLocalDate())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}

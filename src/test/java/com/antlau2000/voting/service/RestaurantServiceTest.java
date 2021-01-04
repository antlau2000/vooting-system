package com.antlau2000.voting.service;

import com.antlau2000.voting.to.RestaurantTo;
import com.antlau2000.voting.model.Restaurant;
import com.antlau2000.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static com.antlau2000.voting.DishTestData.DISH_1;
import static com.antlau2000.voting.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;

    @Test
    void create() throws Exception {
        Restaurant created = service.create(getNewRestaurant());
        int newId = created.id();
        Restaurant newRestaurant = getNewRestaurant();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "Corner Grill")));
    }

    @Test
    void delete() throws Exception {
        service.delete(RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT_2_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT_2);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_1_ID), getUpdatedRestaurant());
    }

    @Test
    void getAllByDishesDate() throws Exception {
        List<RestaurantTo> allByDate = service.getAllByDishesDate(DISH_1.getLocalDate());

        allByDate.forEach(System.out::println);
    }
}

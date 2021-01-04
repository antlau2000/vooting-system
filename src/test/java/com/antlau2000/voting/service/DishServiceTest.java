package com.antlau2000.voting.service;

import com.antlau2000.voting.model.Dish;
import com.antlau2000.voting.util.exception.NotFoundException;
import org.hsqldb.HsqlException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.antlau2000.voting.DishTestData.*;
import static com.antlau2000.voting.RestaurantTestData.*;
import static com.antlau2000.voting.UserTestData.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DishServiceTest extends AbstractServiceTest {
    @Autowired
    protected DishService service;

    @Test
    void delete() throws Exception {
        service.delete(DISH_1_ID, RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(DISH_1_ID, RESTAURANT_2_ID));
    }

    @Test
    void create() throws Exception {
        Dish created = service.create(getNewDish(), RESTAURANT_1_ID);
        int newId = created.id();
        Dish newDish = getNewDish();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, RESTAURANT_1_ID), newDish);
    }

    @Test
    void get() throws Exception {
        Dish actual = service.get(DISH_1_ID, RESTAURANT_1_ID);
        DISH_MATCHER.assertMatch(actual, DISH_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_1_ID));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(DISH_1_ID, RESTAURANT_2_ID));
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdatedDish();
        service.update(updated, RESTAURANT_1_ID);
        DISH_MATCHER.assertMatch(service.get(DISH_1_ID, RESTAURANT_1_ID), getUpdatedDish());
    }

    @Test
    void updateNotOwn() throws Exception {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(DISH_1, RESTAURANT_3_ID));
        assertEquals("Not found entity with id=" + DISH_1_ID, exception.getMessage());
    }

    @Test
    void getByRestaurantIdAndDate() throws Exception {
        DISH_MATCHER.assertMatch(service.getByDate(DISH_TEST_DATE, RESTAURANT_1_ID), DISH_3, DISH_1, DISH_2);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, DISH_1.getLocalDate(), DISH_1.getName(), 1000L, DISH_1.getRestaurant()), RESTAURANT_1_ID), HsqlException.class);
    }
}

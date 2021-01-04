package com.antlau2000.voting.web.restaurant;

import com.antlau2000.voting.model.Dish;
import com.antlau2000.voting.service.DishService;
import com.antlau2000.voting.service.RestaurantService;
import com.antlau2000.voting.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserRestaurantController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/restaurants";

    public static final String DISHES_REST_URL = "/{restaurantId}/dishes";

    private final RestaurantService restaurantService;
    private final DishService dishService;

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("get all");
        return restaurantService.getAllByDishesDate(LocalDate.now());
    }

    @GetMapping("/by")
    public List<RestaurantTo> getByDate(@RequestParam LocalDate date) {
        log.info("get by date {}", date);
        return restaurantService.getAllByDishesDate(date);
    }

    @GetMapping(value = DISHES_REST_URL + "/by")
    public List<Dish> getDishesByDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("get dishes for {} on {}", restaurantId, date);
        return dishService.getByDate(date, restaurantId);
    }

    @GetMapping(value = DISHES_REST_URL + "/{dishId}")
    public Dish getDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("get dish {} for {}", dishId, restaurantId);
        return dishService.get(dishId, restaurantId);
    }
}

package com.antlau2000.voting.service;

import com.antlau2000.voting.repository.DishRepository;
import com.antlau2000.voting.model.Dish;
import com.antlau2000.voting.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.antlau2000.voting.util.RepositoryUtil.findById;
import static com.antlau2000.voting.util.ValidationUtil.checkNew;
import static com.antlau2000.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @CacheEvict(value = "restaurantTos", allEntries = true)
    @Transactional
    public Dish create(@Valid Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        dish.setRestaurant(findById(restaurantRepository, restaurantId));
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurantTos", allEntries = true)
    @Transactional
    public Dish update(@Valid Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(get(dish.id(), restaurantId), dish.id());
        dish.setRestaurant(findById(restaurantRepository, restaurantId));
        return checkNotFoundWithId(dishRepository.save(dish), dish.id());
    }

    @CacheEvict(value = "restaurantTos", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(dishRepository.delete(id, restaurantId) != 0, id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(dishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null), id);
    }

    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    public List<Dish> getByDate(LocalDate date, int restaurantId) {
        return dishRepository.getByDate(date, restaurantId);
    }
}

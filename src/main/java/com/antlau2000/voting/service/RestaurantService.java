package com.antlau2000.voting.service;

import com.antlau2000.voting.to.RestaurantTo;
import com.antlau2000.voting.model.Restaurant;
import com.antlau2000.voting.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.antlau2000.voting.util.RepositoryUtil.findById;
import static com.antlau2000.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @CacheEvict(value = "restaurantTos", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurantTos", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return findById(restaurantRepository, id);
    }

    @Cacheable("restaurantTos")
    public List<RestaurantTo> getAllByDishesDate(LocalDate date) {
        return restaurantRepository.getAllByDate(date);
    }

    @CacheEvict(value = "restaurantTos", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @CacheEvict(value = "restaurantTos", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        Restaurant restaurant = get(id);
        restaurant.setEnabled(enabled);
    }
}

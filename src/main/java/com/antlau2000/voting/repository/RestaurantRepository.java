package com.antlau2000.voting.repository;

import com.antlau2000.voting.to.RestaurantTo;
import com.antlau2000.voting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);


    /* This methods causes SQL Warning in debug mode, but works correct */
    @Query("SELECT DISTINCT new com.antlau2000.voting.to.RestaurantTo(r.id, r.name, count(vote))  " +
            "FROM Restaurant r LEFT OUTER JOIN Vote vote ON r.id = vote.restaurant.id AND vote.localDate=:date " +
            "GROUP BY r.id ORDER BY count(vote) DESC, r.name ASC")
    List<RestaurantTo> getAllByDate(@Param("date") LocalDate date);
}

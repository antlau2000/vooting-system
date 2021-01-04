package com.antlau2000.voting.model;

import com.antlau2000.voting.HasId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "localdate"}, name = "dish_unique_restaurant_name_date_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Dish extends AbstractNamedEntity implements HasId {
    @Column(name = "price", nullable = false)
    @Positive
    // https://stackoverflow.com/a/43051227
    private Long price;

    @Column(name = "localdate", nullable = false)
    @NotNull
    private LocalDate localDate = LocalDate.now();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = CASCADE)
    @NotNull
    @JsonBackReference(value = "restaurantDishes")
    private Restaurant restaurant;

    public Dish(Integer id, LocalDate localDate, String name, Long price, Restaurant restaurant) {
        super(id, name);
        this.localDate = localDate;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Dish d) {
        this(d.getId(), d.getLocalDate(), d.getName(), d.getPrice(), d.getRestaurant());
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name +
                ", price=" + price +
                ", date=" + localDate +
                '}';
    }
}

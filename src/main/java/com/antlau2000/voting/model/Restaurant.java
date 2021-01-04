package com.antlau2000.voting.model;

import com.antlau2000.voting.HasId;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity implements HasId {
    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @OneToMany(fetch = LAZY, mappedBy = "restaurant")
    @OrderBy("localDate DESC")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "restaurantDishes")
    private Set<Dish> dishes;

    @OneToMany(fetch = LAZY, mappedBy = "restaurant")
    @OrderBy("localDate DESC")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "restaurantVotes")
    private Set<Vote> votes;

    public Restaurant(Integer id, String name, boolean enabled, Date registered) {
        super(id, name);
        this.enabled = enabled;
        this.registered = registered;
    }

    public Restaurant(Integer id, String name) {
        this(id, name, true, new Date());
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.isEnabled(), restaurant.getRegistered());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", enabled=" + enabled +
                '}';
    }
}

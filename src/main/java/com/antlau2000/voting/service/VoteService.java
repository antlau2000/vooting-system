package com.antlau2000.voting.service;

import com.antlau2000.voting.repository.RestaurantRepository;
import com.antlau2000.voting.repository.UserRepository;
import com.antlau2000.voting.repository.VoteRepository;
import com.antlau2000.voting.model.Restaurant;
import com.antlau2000.voting.model.User;
import com.antlau2000.voting.model.Vote;
import com.antlau2000.voting.util.exception.NotFoundException;
import com.antlau2000.voting.util.exception.VoteDeadlineException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.antlau2000.voting.model.Vote.VOTE_DEADLINE;
import static com.antlau2000.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class VoteService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Setter
    private Clock clock = Clock.systemDefaultZone();

    @CacheEvict(value = "restaurantTos", allEntries = true)
    @Transactional
    public Vote vote(int userId, int restaurantId) {
        LocalDateTime votingLocalDateTime = LocalDateTime.now(clock);
        final Restaurant restaurant = checkNotFoundWithId(restaurantRepository.getOne(restaurantId), restaurantId);
        final User user = checkNotFoundWithId(userRepository.getOne(userId), userId);
        Vote vote;
        try {
            vote = getByUserAndLocalDate(user, votingLocalDateTime.toLocalDate());
        } catch (NotFoundException e) {
            log.debug("new vote from user {} for {}", userId, restaurantId);
            return voteRepository.save(new Vote(null, votingLocalDateTime.toLocalDate(), user, restaurant));
        }

        if (votingLocalDateTime.toLocalTime().isBefore(VOTE_DEADLINE)) {
            if (vote.getRestaurant().id() != restaurantId) {
                vote.setRestaurant(restaurant);
                log.debug("vote from user {} for restaurant {} was changed", userId, restaurantId);
                return vote;
            }

            log.debug("vote from user {} for restaurant {} not changed", userId, restaurantId);
            return vote;
        } else {
            throw new VoteDeadlineException("Vote deadline has already passed");
        }
    }

    public Vote getByUserAndLocalDate(User user, LocalDate date) {
        return checkNotFoundWithId(voteRepository.getByUserAndLocalDate(user, date), user.id());
    }

    public Vote getByUserIdAndLocalDate(int userId, LocalDate date) {
        return checkNotFoundWithId(voteRepository.getByUserIdAndLocalDate(userId, date), userId);
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }
}

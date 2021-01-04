package com.antlau2000.voting;

import com.antlau2000.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_1;
import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_3;
import static com.antlau2000.voting.UserTestData.USER;
import static com.antlau2000.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingEqualsAssertions(Vote.class);
    public static TestMatcher<Vote> VOTE_LAZY_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class, "user", "restaurant");

    public static final int VOTE_1_ID = START_SEQ + 6;
    public static final int VOTE_2_ID = START_SEQ + 7;
    public static final int VOTE_3_ID = START_SEQ + 8;

    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, LocalDate.of(2020, 8, 10), USER, RESTAURANT_1);
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, LocalDate.of(2020, 8, 11), USER, RESTAURANT_1);
    public static final Vote VOTE_3 = new Vote(VOTE_3_ID, LocalDate.of(2020, 8, 12), USER, RESTAURANT_3);

    public static final List<Vote> VOTES = List.of(VOTE_3, VOTE_2, VOTE_1);

    public static Vote getNewVote() {
        return new Vote(null, LocalDate.now(), USER, RESTAURANT_1);
    }
}

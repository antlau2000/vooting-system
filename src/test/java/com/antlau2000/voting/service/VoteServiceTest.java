package com.antlau2000.voting.service;

import com.antlau2000.voting.model.Vote;
import com.antlau2000.voting.util.exception.VoteDeadlineException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.util.List;

import static com.antlau2000.voting.UserTestData.USER_ID;
import static com.antlau2000.voting.VoteTestData.*;
import static com.antlau2000.voting.model.Vote.VOTE_DEADLINE;
import static com.antlau2000.voting.util.DateTimeUtil.createClock;

class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService voteService;

    @Test
    void voteSimple() throws Exception {
        Vote newVote = getNewVote();
        Vote created = voteService.vote(newVote.getUser().id(), newVote.getRestaurant().id());
        newVote.setId(created.getId());
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.getByUserAndLocalDate(created.getUser(), created.getLocalDate()), newVote);
    }

    @Test
    void voteAgainBeforeDeadline() throws Exception {
        Clock clock = createClock(VOTE_3.getLocalDate(), VOTE_DEADLINE.minusMinutes(1));
        voteService.setClock(clock);
        Vote newVote = getNewVote();
        Vote created = voteService.vote(newVote.getUser().id(), newVote.getRestaurant().id());
        newVote.setId(created.getId());
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.getByUserAndLocalDate(created.getUser(), created.getLocalDate()), newVote);}

    @Test
    void voteAgainAfterDeadline() throws Exception {
        Clock clock = createClock(VOTE_3.getLocalDate(), VOTE_DEADLINE);
        voteService.setClock(clock);
        Vote newVote = getNewVote();
        validateRootCause(() ->voteService.vote(newVote.getUser().id(), newVote.getRestaurant().id()), VoteDeadlineException.class);
    }

    @Test
    void getAll() throws Exception {
        List<Vote> voteList = voteService.getAll(USER_ID);
        VOTE_LAZY_MATCHER.assertMatch(voteList, VOTE_3, VOTE_2, VOTE_1);
    }

    @Test
    void getByUserIdAndDate() throws  Exception {
        Vote result = voteService.getByUserIdAndLocalDate(USER_ID, VOTE_1.getLocalDate());
        VOTE_LAZY_MATCHER.assertMatch(result, VOTE_1);
    }
}

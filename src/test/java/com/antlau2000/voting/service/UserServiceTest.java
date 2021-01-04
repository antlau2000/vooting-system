package com.antlau2000.voting.service;


import com.antlau2000.voting.VoteTestData;
import com.antlau2000.voting.model.Role;
import com.antlau2000.voting.model.User;
import com.antlau2000.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.antlau2000.voting.UserTestData.*;
import static com.antlau2000.voting.VoteTestData.VOTE_LAZY_MATCHER;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    void create() throws Exception {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdatedUser();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdatedUser());
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)), ConstraintViolationException.class);
        //validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", true, new Date(), Set.of())), ConstraintViolationException.class);
        //validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", true, new Date(), Set.of())), ConstraintViolationException.class);
    }

    @Test
    void getWithVotes() throws Exception {
        User user = service.getWithVotes(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        VOTE_LAZY_MATCHER.assertMatch(user.getVotes(), VoteTestData.VOTES);
    }

    @Test
    void getWithVotesNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.getWithVotes(NOT_FOUND));
    }
}

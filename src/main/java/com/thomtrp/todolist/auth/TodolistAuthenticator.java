package com.thomtrp.todolist.auth;

import java.util.Optional;

import com.thomtrp.todolist.domain.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class TodolistAuthenticator implements Authenticator<String, User> {
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        if ("test_token".equals(token)) {
            return Optional.of(User.builder().build());
        }
        return Optional.empty();
    }
}
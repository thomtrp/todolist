package com.thomtrp.todolist.domain.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@Value
@Builder
public class User implements Principal {
    int id;
    String username;
    String password;

    @Override
    public String getName() {
        return username;
    }
}

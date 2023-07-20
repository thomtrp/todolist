package com.thomtrp.todolist;

import com.codahale.metrics.health.HealthCheck;
import com.thomtrp.todolist.domain.ports.TodoItemService;
import lombok.AllArgsConstructor;

import javax.inject.Inject;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class TodolistApplicationHealthCheck extends HealthCheck {
    private static final String HEALTHY = "The Dropwizard blog Service is healthy for read and write";
    private static final String UNHEALTHY = "The Dropwizard blog Service is not healthy. ";
    private static final String MESSAGE_PLACEHOLDER = "{}";

    private final TodoItemService itemsService;

    @Override
    public Result check() throws Exception {
        String mySqlHealthStatus = itemsService.performHealthCheck();

        if (mySqlHealthStatus == null) {
            return Result.healthy(HEALTHY);
        } else {
            return Result.unhealthy(UNHEALTHY + MESSAGE_PLACEHOLDER, mySqlHealthStatus);
        }
    }
}

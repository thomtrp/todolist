package com.thomtrp.todolist.health;

import com.codahale.metrics.health.HealthCheck;
import com.thomtrp.todolist.service.ItemsService;

public class TodolistApplicationHealthCheck extends HealthCheck {
    private static final String HEALTHY = "The Dropwizard blog Service is healthy for read and write";
    private static final String UNHEALTHY = "The Dropwizard blog Service is not healthy. ";
    private static final String MESSAGE_PLACEHOLDER = "{}";

    private final ItemsService itemsService;

    public TodolistApplicationHealthCheck(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

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

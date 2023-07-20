package com.thomtrp.todolist;

import com.thomtrp.todolist.config.TodolistConfiguration;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Environment;

import javax.sql.DataSource;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

import com.thomtrp.todolist.auth.TodolistAuthenticator;
import com.thomtrp.todolist.auth.TodolistAuthorizer;
import com.thomtrp.todolist.auth.User;
import com.thomtrp.todolist.health.TodolistApplicationHealthCheck;
import com.thomtrp.todolist.resources.ItemsResource;
import com.thomtrp.todolist.service.ItemsService;

public class TodolistApplication extends Application<TodolistConfiguration> {
    private static final String SQL = "sql";
    private static final String DROPWIZARD_BLOG_SERVICE = "Dropwizard blog service";
    private static final String BEARER = "Bearer";

    public static void main(String[] args) throws Exception {
        new TodolistApplication().run(args);
    }

    @Override
    public void run(TodolistConfiguration configuration, Environment environment) {
        // Datasource configuration
        final DataSource dataSource =
                configuration.getDataSourceFactory().build(environment.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        // Register Health Check
        TodolistApplicationHealthCheck healthCheck =
                new TodolistApplicationHealthCheck(dbi.onDemand(ItemsService.class));
        environment.healthChecks().register(DROPWIZARD_BLOG_SERVICE, healthCheck);

        // Register OAuth authentication
        environment.jersey()
                .register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new TodolistAuthenticator())
                        .setAuthorizer(new TodolistAuthorizer()).setPrefix(BEARER).buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        // Register resources
        environment.jersey().register(new ItemsResource(dbi.onDemand(ItemsService.class)));
    }
}

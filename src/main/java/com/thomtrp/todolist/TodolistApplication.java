package com.thomtrp.todolist;

import com.thomtrp.todolist.config.TodolistConfiguration;
import com.thomtrp.todolist.domain.model.TodoItem;
import com.thomtrp.todolist.domain.ports.TodoItemDao;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.thomtrp.todolist.adapters.api.TodoItemEndpoints;
import com.thomtrp.todolist.domain.ports.TodoItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodolistApplication extends Application<TodolistConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodolistApplication.class);

    private final HibernateBundle<TodolistConfiguration> hibernate = new HibernateBundle<TodolistConfiguration>(TodoItem.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TodolistConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<TodolistConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(TodolistConfiguration configuration, Environment environment) {
        LOGGER.info("Registering session");

        final TodoItemDao dao = new TodoItemDao(hibernate.getSessionFactory());

        final TodoItemService todoItemService = new TodoItemService(dao);

        LOGGER.info("Registering REST resources");
        environment.jersey().register(new TodoItemEndpoints(todoItemService));

        LOGGER.info("Registering Application Health Check");
        environment.healthChecks().register("application", new TodolistApplicationHealthCheck(todoItemService));
    }

    public static void main(String[] args) throws Exception {
        new TodolistApplication().run(args);
    }
}

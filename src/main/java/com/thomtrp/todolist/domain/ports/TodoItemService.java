package com.thomtrp.todolist.domain.ports;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.thomtrp.todolist.domain.model.TodoItem;

public class TodoItemService {
    private static final String ITEM_NOT_FOUND = "Item id %s not found.";
    private static final String DATABASE_REACH_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String DATABASE_UNEXPECTED_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting item.";

    private TodoItemDao todoItemDao;
    public TodoItemService(TodoItemDao todoItemDao) {
        this.todoItemDao = todoItemDao;
    }

    public List<TodoItem> getItems() {
        return todoItemDao.getItems();
    }

    public TodoItem getItem(int id) {
        TodoItem item = todoItemDao.getItem(id);
        if (Objects.isNull(item)) {
            throw new WebApplicationException(String.format(ITEM_NOT_FOUND, id), Status.NOT_FOUND);
        }
        return item;
    }

    public TodoItem editItem(TodoItem item) {
        if (Objects.isNull(todoItemDao.getItem(item.getId()))) {
            throw new WebApplicationException(String.format(ITEM_NOT_FOUND, item.getId()),
                    Status.NOT_FOUND);
        }
        todoItemDao.editItem(item.getId(), item);
        return todoItemDao.getItem(item.getId());
    }

    public Integer deleteItem(final int id) {
        todoItemDao.deleteItem(id);
        return id;
    }

    public String performHealthCheck() {
        try {
            System.out.println("Performing health check");
            todoItemDao.getItems();
        } catch (Exception ex) {
            System.out.println("error");

            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }
}

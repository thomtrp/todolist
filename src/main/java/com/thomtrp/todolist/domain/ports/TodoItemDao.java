package com.thomtrp.todolist.domain.ports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.thomtrp.todolist.domain.model.TodoItem;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;

public class TodoItemDao extends AbstractDAO<TodoItem> {
    public TodoItemDao(SessionFactory factory) {
        super(factory);
    }

    public HashMap<Integer, TodoItem> todoItems = new HashMap<>();

    public List<TodoItem> getItems() {
        return list(query("from TodoItem"));
    }

    public TodoItem getItem(Integer id) {
        return get(id);
    }

    public void editItem(Integer id, TodoItem employee) {
        todoItems.put(id, employee);
    }

    public void deleteItem(Integer id) {
        todoItems.remove(id);
    }
}

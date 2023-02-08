package com.thomtrp.todolist.service;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.thomtrp.todolist.dao.ItemsDao;
import com.thomtrp.todolist.model.Item;

public abstract class ItemsService {
    private static final String ITEM_NOT_FOUND = "Item id %s not found.";
    private static final String DATABASE_REACH_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String DATABASE_UNEXPECTED_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting item.";

    @CreateSqlObject
    abstract ItemsDao itemsDao();

    public List<Item> getItems() {
        return itemsDao().getItems();
    }

    public Item getItem(int id) {
        Item item = itemsDao().getItem(id);
        if (Objects.isNull(item)) {
            throw new WebApplicationException(String.format(ITEM_NOT_FOUND, id), Status.NOT_FOUND);
        }
        return item;
    }

    public Item createItem(Item item) {
        itemsDao().createItem(item);
        return itemsDao().getItem(itemsDao().lastInsertId());
    }

    public Item editItem(Item item) {
        if (Objects.isNull(itemsDao().getItem(item.getId()))) {
            throw new WebApplicationException(String.format(ITEM_NOT_FOUND, item.getId()),
                    Status.NOT_FOUND);
        }
        itemsDao().editItem(item);
        return itemsDao().getItem(item.getId());
    }

    public String deleteItem(final int id) {
        int result = itemsDao().deleteItem(id);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(ITEM_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_ERROR, Status.INTERNAL_SERVER_ERROR);
        }
    }

    public String performHealthCheck() {
        try {
            itemsDao().getItems();
        } catch (UnableToObtainConnectionException ex) {
            return checkUnableToObtainConnectionException(ex);
        } catch (UnableToExecuteStatementException ex) {
            return checkUnableToExecuteStatementException(ex);
        } catch (Exception ex) {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

    private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
        if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
            return DATABASE_REACH_ERROR + ex.getCause().getLocalizedMessage();
        } else if (ex.getCause() instanceof java.sql.SQLException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
    }

    private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
        if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
    }
}

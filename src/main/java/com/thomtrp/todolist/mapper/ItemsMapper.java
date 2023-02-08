package com.thomtrp.todolist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.thomtrp.todolist.model.Item;

public class ItemsMapper implements ResultSetMapper<Item> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CODE = "code";

    public Item map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new Item(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(CODE));
    }
}

package com.thomtrp.todolist.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.thomtrp.todolist.mapper.ItemsMapper;
import com.thomtrp.todolist.model.Item;

@RegisterMapper(ItemsMapper.class)
public interface ItemsDao {

    @SqlQuery("select * from parts;")
    public List<Item> getItems();

    @SqlQuery("select * from parts where id = :id")
    public Item getItem(@Bind("id") final int id);

    @SqlUpdate("insert into parts(name, code) values(:name, :code)")
    void createItem(@BindBean final Item part);

    @SqlUpdate("update parts set name = coalesce(:name, name), code = coalesce(:code, code) where id = :id")
    void editItem(@BindBean final Item part);

    @SqlUpdate("delete from parts where id = :id")
    int deleteItem(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}

package com.thomtrp.todolist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Value
@Builder(toBuilder = true)
@Table(name="item")
public class TodoItem {
    public TodoItem() {
        this.id = 0;
        this.content = "";
    }

    public TodoItem(int id, String content) {
        this.id = id;
        this.content = content;
    }

    @Id
    @GeneratedValue
    @Column(name="id")
    int id;

    @Column(name="content", nullable=false)
    @NotNull String content;
}

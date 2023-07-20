package com.thomtrp.todolist.adapters.api;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {
    private long code;

    @Length(max = 3)
    private T data;

    public ApiResponse() {
        // Jackson deserialization
    }

    public ApiResponse(long code, T data) {
        this.code = code;
        this.data = data;
    }

    @JsonProperty
    public long getCode() {
        return code;
    }

    @JsonProperty
    public T getData() {
        return data;
    }
}

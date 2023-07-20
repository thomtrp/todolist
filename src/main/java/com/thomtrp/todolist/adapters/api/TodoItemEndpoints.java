package com.thomtrp.todolist.adapters.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import org.eclipse.jetty.http.HttpStatus;

import com.codahale.metrics.annotation.Timed;
import com.thomtrp.todolist.domain.model.TodoItem;
import com.thomtrp.todolist.domain.ports.TodoItemService;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class TodoItemEndpoints {
    private final TodoItemService itemsService;;

    public TodoItemEndpoints(TodoItemService itemsService) {
        this.itemsService = itemsService;
    }

    @GET
    @Timed
    @UnitOfWork
    public ApiResponse<List<TodoItem>> getItems() {
        return new ApiResponse<List<TodoItem>>(HttpStatus.OK_200, itemsService.getItems());
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("{id}")
    public ApiResponse<TodoItem> getItem(@PathParam("id") final int id) {
        return new ApiResponse<TodoItem>(HttpStatus.OK_200, itemsService.getItem(id));
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("{id}")
    public ApiResponse<TodoItem> editItem(@NotNull @Valid TodoItem item,
                                          @PathParam("id") final int id) {
        return new ApiResponse<TodoItem>(HttpStatus.OK_200, itemsService.editItem(item.toBuilder().id(id).build()));
    }

    @DELETE
    @Timed
    @UnitOfWork
    @Path("{id}")
    public ApiResponse<Integer> deleteItem(@PathParam("id") final int id) {
        return new ApiResponse<>(HttpStatus.OK_200, itemsService.deleteItem(id));
    }
}

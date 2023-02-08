package com.thomtrp.todolist.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.http.HttpStatus;

import com.codahale.metrics.annotation.Timed;
import com.thomtrp.todolist.model.Item;
import com.thomtrp.todolist.service.ItemsService;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class ItemsResource {
    private final ItemsService itemsService;;

    public ItemsResource(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GET
    @Timed
    public Representation<List<Item>> getItems() {
        return new Representation<List<Item>>(HttpStatus.OK_200, itemsService.getItems());
    }

    @GET
    @Timed
    @Path("{id}")
    public Representation<Item> getItem(@PathParam("id") final int id) {
        return new Representation<Item>(HttpStatus.OK_200, itemsService.getItem(id));
    }

    @POST
    @Timed
    public Representation<Item> createItem(@NotNull @Valid final Item item) {
        return new Representation<Item>(HttpStatus.OK_200, itemsService.createItem(item));
    }

    @PUT
    @Timed
    @Path("{id}")
    public Representation<Item> editItem(@NotNull @Valid final Item item,
                                         @PathParam("id") final int id) {
        item.setId(id);
        return new Representation<Item>(HttpStatus.OK_200, itemsService.editItem(item));
    }

    @DELETE
    @Timed
    @Path("{id}")
    public Representation<String> deleteItem(@PathParam("id") final int id) {
        return new Representation<String>(HttpStatus.OK_200, itemsService.deleteItem(id));
    }
}

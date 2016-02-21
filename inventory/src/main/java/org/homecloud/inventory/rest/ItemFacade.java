/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homecloud.inventory.rest;

import org.homecloud.erp.commons.pagination.PageRequest;
import org.homecloud.erp.commons.pagination.PaginationUtil;
import org.homecloud.erp.commons.resource.PageResource;
import org.homecloud.inventory.entity.Item;
import org.homecloud.inventory.resource.ContainerResource;
import org.homecloud.inventory.resource.ItemResource;
import org.homecloud.inventory.service.ItemService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 *
 * @author rene
 */
@Stateless
public class ItemFacade  {

    @EJB
    ItemService itemService;
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Item entity) {
        itemService.create(entity);
        return Response.created(uriInfo.getRequestUriBuilder().path(entity.getId()).build()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Item entity) {
        itemService.edit(id,entity);
    }



    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        itemService.remove(id);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ItemResource find(@PathParam("id") String id) {
        Item entity = itemService.find(id);
        if (entity == null) throw new NotFoundException();
        ItemResource result = new ItemResource(uriInfo.getRequestUri(),entity);
        result.addLink("container",uriInfo.getAbsolutePathBuilder().path(id).path("container").build());
        result.addLink("image",uriInfo.getBaseUriBuilder().path("images").path(id).build());
        return result;
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PageResource findRange(@DefaultValue("1")  @QueryParam("page") Integer page,
                                  @DefaultValue("10") @QueryParam("pageSize") Integer pageSize,
                                  @QueryParam("sort") List<String> sorts) {

        PageRequest pageRequest = PaginationUtil.parsePageRequest(page,pageSize,sorts);
        List<Item> entities = itemService.findRange(pageRequest);
        return PageResource.buildPageResource(uriInfo, entities, ItemResource.class, pageRequest,  itemService.count());
    }
}

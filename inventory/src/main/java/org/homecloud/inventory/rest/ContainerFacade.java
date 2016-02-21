/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homecloud.inventory.rest;

import org.homecloud.erp.commons.pagination.PageRequest;
import org.homecloud.erp.commons.pagination.PaginationUtil;
import org.homecloud.erp.commons.resource.PageResource;
import org.homecloud.inventory.entity.Container;
import org.homecloud.inventory.resource.ContainerResource;
import org.homecloud.inventory.service.ContainerService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author rene
 */
@Stateless
@Path("/container")
public class ContainerFacade  {



    @EJB
    private ContainerService containerService;

    @Context
    UriInfo uriInfo;


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Container entity) {
        containerService.create(entity);
        return Response.created(uriInfo.getRequestUriBuilder().path(entity.getId()).build()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Container entity) {
        containerService.edit(id,entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        containerService.remove(id);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ContainerResource find(@PathParam("id") String id) {
        Container entity = containerService.find(id);
        if (entity == null) throw new NotFoundException();

        UriBuilder ub = uriInfo.getRequestUriBuilder();
        URI uri = ub.path(id).build();

        ContainerResource result = new ContainerResource(uri,entity);

        result.addLink("items",ub.path(id).path("items").build());
        if (entity.getParent() != null) {
            result.addLink("parent",ub.path(entity.getParent().getId()).build());
        }

        return result;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PageResource findRange(@DefaultValue("1")@QueryParam("page") Integer page,
                                  @DefaultValue("10")@QueryParam("pageSize") Integer pageSize,
                                  @QueryParam("sort")List<String> sorts) {
        PageRequest pageRequest = PaginationUtil.parsePageRequest(page,pageSize,sorts);
        List<Container> entities = containerService.findRange(pageRequest);
        return PageResource.buildPageResource(uriInfo, entities, ContainerResource.class, pageRequest, containerService.countContainers());
    }

}

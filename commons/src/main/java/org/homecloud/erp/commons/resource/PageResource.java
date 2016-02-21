package org.homecloud.erp.commons.resource;

import org.homecloud.erp.commons.entity.BaseEntity;
import org.homecloud.erp.commons.pagination.PageRequest;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rene on 19.02.16.
 */
public class PageResource extends  AbstractResource {

  private List<? extends AbstractResource> resources;

  public PageResource(URI self, URI next, URI previous, List<? extends AbstractResource> resources) {
    super(self);
    this.resources = resources;
    if (next != null) super.addLink("next",next);
    if (previous != null) super.addLink("previous",previous);
  }

  public List<? extends AbstractResource> getResources() {
    return resources;
  }

  public void setResources(List<? extends AbstractResource> resources) {
    this.resources = resources;
  }

  public static PageResource buildPageResource(UriInfo uriInfo, List<? extends BaseEntity> entities, Class<? extends AbstractResource> resourceClass, PageRequest pageRequest, int maxItems) {

    List<? extends AbstractResource> resources = entities.stream()
        .map(entity -> createResourceInstance(uriInfo,entity,resourceClass))
        .collect(Collectors.toList());

    URI self = uriInfo.getRequestUriBuilder()
        .queryParam("page",pageRequest.getPage())
        .queryParam("pageSize",pageRequest.getPageSize())
        .queryParam("sort",pageRequest.getSorts()).build();

    URI prev = pageRequest.getPage() > 1
        ? uriInfo.getRequestUriBuilder()
        .queryParam("page",pageRequest.getPage()-1)
        .queryParam("pageSize",pageRequest.getPageSize())
        .queryParam("sort",pageRequest.getSorts()).build()
        : null;

    URI next = pageRequest.getPage() * pageRequest.getPageSize() < maxItems
        ? uriInfo.getRequestUriBuilder()
        .queryParam("page",pageRequest.getPage()+1)
        .queryParam("pageSize",pageRequest.getPageSize())
        .queryParam("sort",pageRequest.getSorts()).build()
        : null;

    return new PageResource(self,next,prev,resources);
  }

  private static<T extends AbstractResource> T createResourceInstance(UriInfo uriInfo, BaseEntity entity, Class<T> resourceClass) {
    Constructor<T> con = null;
    try {
      resourceClass.getConstructor(URI.class,entity.getClass());
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    T result = null;
    try {
      result = con.newInstance(uriInfo.getRequestUriBuilder().path(entity.getId()).build(),entity);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return result;
  }
}

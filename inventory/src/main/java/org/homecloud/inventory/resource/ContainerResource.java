package org.homecloud.inventory.resource;

import org.homecloud.erp.commons.resource.AbstractResource;
import org.homecloud.inventory.entity.Container;

import javax.ws.rs.core.Link;
import java.net.URI;
import java.util.List;

/**
 * Created by rene on 18.02.16.
 */
public class ContainerResource extends AbstractResource {


  List<Link> links;

  public ContainerResource(URI self, Container container) {
    super(self);
  }
}

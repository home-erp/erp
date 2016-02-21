package org.homecloud.erp.commons.resource;

import javax.ws.rs.core.Link;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 19.02.16.
 */
public abstract class AbstractResource {

  private List<Link> links = new ArrayList<>();

  public AbstractResource(URI selfLink){
    this.addLink("self",selfLink);
  }

  public void addLink(String rel, URI uri) {
    this.links.add(Link.fromUri(uri).rel(rel).build());
  }

  public List<Link> getLinks() {
    return links;
  }
}

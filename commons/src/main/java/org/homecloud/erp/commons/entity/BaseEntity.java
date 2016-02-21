package org.homecloud.erp.commons.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by rene on 19.02.16.
 */
@MappedSuperclass
public abstract class BaseEntity {
  @Id
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}

package org.homecloud.inventory.resource;

import org.homecloud.erp.commons.resource.AbstractResource;
import org.homecloud.inventory.entity.Item;

import java.net.URI;

/**
 * Created by rene on 18.02.16.
 */
public class ItemResource extends AbstractResource {

  private String id;
  private String vendorId;
  private int amount;
  private int unitSize;
  private String upc;

  public ItemResource(URI selfLink, Item item) {
    super(selfLink);
    this.id = item.getId();
    this.vendorId = item.getVendorId();
    this.amount = item.getAmount();
    this.unitSize = item.getUnitSize();
    this.upc = item.getUpc();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getUnitSize() {
    return unitSize;
  }

  public void setUnitSize(int unitSize) {
    this.unitSize = unitSize;
  }

  public String getUpc() {
    return upc;
  }

  public void setUpc(String upc) {
    this.upc = upc;
  }
}

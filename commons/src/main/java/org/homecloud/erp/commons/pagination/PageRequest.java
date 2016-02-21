package org.homecloud.erp.commons.pagination;

import javax.ws.rs.BadRequestException;
import java.util.Map;

/**
 * Created by rene on 17.02.16.
 */
public class PageRequest {
  private int page;
  private int pageSize;
  private Map<String,Direction> sorts;

  public PageRequest(int page, int pageSize, Map<String, Direction> sorts) {
    this.page = page;
    this.pageSize = pageSize;
    this.sorts = sorts;
  }

  public PageRequest(int page, int pageSize) {
    this.page = page;
    this.pageSize = pageSize;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public Map<String, Direction> getSorts() {
    return sorts;
  }

  public void setSorts(Map<String, Direction> sorts) {
    this.sorts = sorts;
  }

  public static enum Direction{
    ASC,
    DESC;
    public static Direction fromString(String dir) {
      switch (dir) {
        case "ASC":
          return ASC;
        case "DESC":
          return DESC;
        default:
          throw new BadRequestException();
      }
    }

  }
}

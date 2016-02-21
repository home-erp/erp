package org.homecloud.erp.commons.pagination;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rene on 17.02.16.
 */
public class PaginationUtil {
  public static PageRequest parsePageRequest(int page, int pageSize, List<String> sorts){

    if(sorts.isEmpty())
      return new PageRequest(page,pageSize);

    return new PageRequest(page,pageSize,parseSorts(sorts));
  }

  private static Map<String,PageRequest.Direction> parseSorts(List<String> sorts) {
    Map<String,PageRequest.Direction> result = new HashMap<>();
    for(String s : sorts) {
      String [] parts = s.split(".");
      PageRequest.Direction dir;
      switch(parts.length) {
        case 1:
          dir = PageRequest.Direction.DESC;
          break;
        case 2:
          dir = PageRequest.Direction.fromString(parts[1].toUpperCase());
          break;
        default:
          throw new BadRequestException();
      }
      result.put(parts[0],dir);
    }
    return result;
  }
}

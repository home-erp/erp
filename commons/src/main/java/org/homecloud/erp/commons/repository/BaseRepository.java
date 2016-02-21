package org.homecloud.erp.commons.repository;


import org.homecloud.erp.commons.pagination.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rene on 17.02.16.
 */
public class BaseRepository<T> {

  private EntityManager em;
  private Class<T> entityClass;

  public BaseRepository(EntityManager em, Class<T> entityClass) {
    this.em = em;
    this.entityClass = entityClass;
  }

  public void create(T entity) {
    em.persist(entity);
  }

  public void edit(T entity) {
    em.merge(entity);
  }

  public void remove(String id) {
    em.remove(find(id));
  }

  public T find(Object id) {
    return em.find(entityClass, id);
  }

  public List<T> findPage(PageRequest pageRequest) {
    CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
    Root<T> root = cq.from(entityClass);
    List<Order> orderList = pageRequest.getSorts().entrySet().stream()
        .map(entry -> this.mapOrder(entry.getKey(),entry.getValue(),root))
        .collect(Collectors.toList());
    cq.select(root).orderBy(orderList);
    TypedQuery<T> q = em.createQuery(cq);
    q.setMaxResults(pageRequest.getPageSize());
    q.setFirstResult(pageRequest.getPage() * pageRequest.getPageSize());
    return q.getResultList();
  }

  public int count() {
    CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
    Root<T> rt = cq.from(entityClass);
    cq.select(em.getCriteriaBuilder().count(rt));
    TypedQuery<Long> q = em.createQuery(cq);
    return q.getSingleResult().intValue();
  }

  public <X> List<X> executeNamedQuery(Class<X> type, String queryName, Map<String,Object> parameters) {
    TypedQuery<X> query = em.createNamedQuery(queryName,type);
    parameters.forEach(query::setParameter);
    return query.getResultList();
  }

  public <X> List<X> executeNamedQuery(Class<X> type, String queryName, Map<String,Object> parameters, int limit, int offset) {
    TypedQuery<X> query = em.createNamedQuery(queryName,type);
    parameters.forEach(query::setParameter);
    query.setFirstResult(offset);
    query.setMaxResults(limit);
    return query.getResultList();
  }

  private Order mapOrder(String property, PageRequest.Direction direction,Root<?> root) {
    if (direction.equals(PageRequest.Direction.ASC)) {
      return em.getCriteriaBuilder().asc(root.get(property));
    } else {
      return em.getCriteriaBuilder().desc(root.get(property));
    }
  }

}

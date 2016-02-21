/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homecloud.inventory.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.homecloud.erp.commons.pagination.PageRequest;
import org.homecloud.erp.commons.repository.BaseRepository;
import org.homecloud.inventory.entity.Container;

/**
 *
 * @author rene
 */
@Stateless
public class ContainerService {

    @PersistenceContext(unitName = "org.homecloud.inventory.pu")
    private EntityManager em;


    private BaseRepository<Container> containerBaseRepository;

    @PostConstruct
    public void init() {
        this.containerBaseRepository = new BaseRepository<>(em,Container.class);
    }

    public void create(Container entity) {
        containerBaseRepository.create(entity);
    }

    public void edit(String id, Container entity) {
        containerBaseRepository.edit(entity);
    }

    @Transactional
    public void remove(String id) {
        containerBaseRepository.remove(id);
    }

    public Container find(String id) {
        return containerBaseRepository.find(id);
    }

    public List<Container> findRange(PageRequest page) {
        return containerBaseRepository.findPage(page);
    }

    public int countContainers() {
        return containerBaseRepository.count();
    }

    @Transactional
    public void moveContainer(String id,String newParent) {
        Container con = containerBaseRepository.find(id);
        Container parent = newParent == null
            ? null
            : containerBaseRepository.find(newParent);
        con.setParent(parent);
        containerBaseRepository.edit(con);
    }

}

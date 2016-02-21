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

import org.homecloud.erp.commons.pagination.PageRequest;
import org.homecloud.erp.commons.repository.BaseRepository;
import org.homecloud.inventory.entity.Item;

/**
 *
 * @author rene
 */
@Stateless
public class ItemService  {

    @PersistenceContext(unitName = "org.homecloud.inventory.pu")
    private EntityManager em;
    private BaseRepository<Item> itemRepository;

    @PostConstruct
    public void init() {
        this.itemRepository = new BaseRepository<>(em,Item.class);
    }

    public void create(Item entity) {
        itemRepository.create(entity);
    }

    public void edit(String id, Item entity) {
        entity.setId(id);
        itemRepository.edit(entity);
    }

    public void remove(String id) {
        itemRepository.remove(id);
    }

    public Item find( String id) {
        return itemRepository.find(id);
    }

    public List<Item> findRange(PageRequest pageRequest) {
        return itemRepository.findPage(pageRequest);
    }

    public int count() {
        return this.itemRepository.count();
    }
}

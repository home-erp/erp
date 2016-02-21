/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homecloud.inventory.entity;

import org.homecloud.erp.commons.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rene
 */
@Entity
@XmlRootElement
public class Container extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Container parent;
    private String description;
    private String itemId;
    @OneToMany
    @JoinTable(name="contains_item",
        joinColumns = {@JoinColumn(name="container_id")},
        inverseJoinColumns = {@JoinColumn(name="item_id")}
    )
    private List<Item> items;

    @OneToMany(mappedBy = "parent")
    private List<Container> children;

    @XmlTransient
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Container)) {
            return false;
        }
        Container other = (Container) object;
        return (super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "org.homecloud.inventory.entity.Container[ id=" + super.getId() + " ]";
    }

}

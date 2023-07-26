package com.example.core.version17;

import java.util.ArrayList;
import java.util.List;

public class MegaMenuDTO {

    private String Id;
    private String name;
    private String parentId;
    private List<MegaMenuDTO> childrenItems; 

    public MegaMenuDTO() {
        this.Id = "";
        this.name = "";     
        this.parentId = "";
        this.childrenItems = new ArrayList<MegaMenuDTO>();
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public List<MegaMenuDTO> getChildrenItems() {
        return childrenItems;
    }
    public void setChildrenItems(List<MegaMenuDTO> childrenItems) {
        this.childrenItems = childrenItems;
    }
    public void addChildrenItem(MegaMenuDTO childrenItem){
        if(!this.childrenItems.contains(childrenItem))
            this.childrenItems.add(childrenItem);
    }

    @Override
    public String toString() {
        return "MegaMenuDTO [Id=" + Id + ", name=" + name + ", parentId="
                + parentId + ", childrenItems=" + childrenItems + "]";
    }

}
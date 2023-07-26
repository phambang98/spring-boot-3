package com.example.core.version17;

public class Pair {
    private String childId ;
    private String parentId;

    public Pair(String childId, String parentId) {
        this.childId = childId;
        this.parentId = parentId;
    }
    public String getChildId() {
        return childId;
    }
    public void setChildId(String childId) {
        this.childId = childId;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
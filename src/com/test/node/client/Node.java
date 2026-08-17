package com.test.node.client;

public class Node {
    
    private String value;
    private int id;
    private Integer parentId; //for root
    
    public Node(String value, int id, Integer parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }
    
    public void setIp(String ip) {
        this.value = ip;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public int getId() {
        return id;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    public String getValue() {
        return value;
    }
    

}
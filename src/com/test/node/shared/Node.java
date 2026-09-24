package com.test.node.shared;

import java.io.Serializable;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ip;
    private int id;
    private Integer parentId;
    private String port;

    public Node() {
    }

    public Node(String value, int id, Integer parentId, String port) {
        this.id = id;
        this.ip = value;
        this.parentId = parentId;
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getValue() {
        return ip;
    }

    public String getPort() {
        return port;
    }
}
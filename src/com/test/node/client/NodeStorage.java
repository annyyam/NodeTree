package com.test.node.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NodeStorage {
    private Map<Integer, Node> nodes;
    private int nextId = 1;
    
    public NodeStorage() {
        nodes = new HashMap<Integer, Node>();
    }
    
    public Node createNode(String ip, Integer parentId) {
        Node node = new Node (ip, nextId, parentId);
        nodes.put(nextId, node);
        nextId ++;
        return node;
    }
    
    //public void createNode(Node node) {
    //    nodes.put(node.getId(), node);
    //}
    
    public List<Node> getAllNodes() {
        return new ArrayList<Node>(nodes.values());
    }
    
    public Node getNode(int id) {
        return nodes.get(id);
    }
    
    public void updateNode(int id, String newIp, Integer newParentId) {
        Node node = nodes.get(id);
        
        if (node!= null) {
            node.setIp(newIp);
            node.setParentId(newParentId);
        }
        
    }
    
    public void deleteNode(int id) {
        List<Integer> childrenIds = new ArrayList<Integer>();
        for (Node node : nodes.values()) {
            if (node.getParentId() != null && node.getParentId() == id) {
                childrenIds.add(node.getId());
            }
        }
        
        for (Integer childId: childrenIds) {
            deleteNode(childId);
        }
        nodes.remove(id);
    }
    

}

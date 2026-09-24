package com.test.node.server;

import java.util.List;

import com.test.node.shared.Node;

public interface NodeMapper {
    List<Node> getAllNodes();

    Node getNode(int id);

    void createNode(Node node);

    void updateNode(Node node);

    void deleteNode(Node node);
    
    List<Node> getRootNodes();
    
    List<Node> getChildren(int parentId);
}

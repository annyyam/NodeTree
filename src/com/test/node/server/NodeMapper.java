package com.test.node.server;

import java.util.List;

import com.test.node.shared.Node;

public interface NodeMapper {
    List<Node> getAllNodes();
    Node getNode(int id);
    void createNode(Node node);
    void updateNode(Node node);
    void deleteNode(int id);
    //void deleteNode(@Param("id") int id);
}

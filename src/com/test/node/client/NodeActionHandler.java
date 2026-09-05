package com.test.node.client;

public interface NodeActionHandler {
    void onNodesChanged();
    void onNodeDeleted();
}

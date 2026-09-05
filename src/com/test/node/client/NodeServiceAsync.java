package com.test.node.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.test.node.shared.Node;

public interface NodeServiceAsync {
    void getAllNodes(AsyncCallback<List<Node>> callback);
    void getNode(int id, AsyncCallback<Node> callback);
    void createNode(String ip, Integer parentId, String port, AsyncCallback<Node> callback);
    void updateNode(int id, String newIp, Integer newParentId, String port, AsyncCallback<Void> callback);
    void deleteNode(int id, AsyncCallback<Void> callback);
}
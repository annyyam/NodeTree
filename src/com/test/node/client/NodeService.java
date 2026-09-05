package com.test.node.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.test.node.shared.Node;

@RemoteServiceRelativePath("node")
public interface NodeService extends RemoteService {
    List<Node> getAllNodes();
    Node getNode(int id);
    Node createNode(String ip, Integer parentId, String port);
    void updateNode(int id, String newIp, Integer newParentId, String port);
    void deleteNode(int id);
}

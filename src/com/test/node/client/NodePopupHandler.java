package com.test.node.client;

import com.test.node.shared.Node;

public interface NodePopupHandler {
    void onAddRoot(String ip, String port);
    void onAddChild(String ip, Node parent, String port);
    void onEdit(Node node, String ip, Integer parentId, String port);
    void onDelete(Node node);
}

package com.test.node.client;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class NewTree {
private Tree tree;

public NewTree() {
    tree = new Tree();
}

public Tree getTree() {
    return tree;
}

public void buildTree(List<Node> nodes) {
    
    tree.clear();

    Map<Integer, TreeItem> items = new HashMap<>();

    //TreeItem for each Node
    for (Node node : nodes) {

        TreeItem item = new TreeItem();
       // item.setText(node.getValue());  //hmmm...
        item.setText("node-" + node.getId()); //fine
        
        item.setUserObject(node); //attach real Node with TreeItem
        
        items.put(node.getId(), item);
    }

    //child + parent
    for (Node node : nodes) {

        TreeItem item = items.get(node.getId());

        if (node.getParentId() == null) {

            //no parent -> root
            tree.addItem(item);

        } else {

            TreeItem parentItem =
                    items.get(node.getParentId());

            if (parentItem != null) {
                parentItem.addItem(item);
            }
        }
    }
    
    for (TreeItem item: items.values()) {
        item.setState(true); //open our tree rightly, ability to open different nodes and it's children
    }
}

public void addSelectionHandler(SelectionHandler<TreeItem> handler) {
    tree.addSelectionHandler(handler);
    
}

}

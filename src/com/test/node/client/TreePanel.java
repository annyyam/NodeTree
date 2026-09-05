package com.test.node.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.node.shared.Node;

public class TreePanel extends Composite {
    private NodeTree nodeTree;
    private NodeServiceAsync nodeService;
    
    public TreePanel() {      
        nodeService = GWT.create(NodeService.class); //why without this. ?
        nodeTree = new NodeTree();
        loadNodes();
        VerticalPanel panel = new VerticalPanel();
        Label title = new Label("Tree:");        
        panel.add(title);
        panel.add(nodeTree.getTree());       
        panel.setStyleName("panelBox");
        panel.addStyleName("treePanel");
        initWidget(panel);         
    }
    public void addSelectionHandler(SelectionHandler<TreeItem> handler) {
        nodeTree.addSelectionHandler(handler);
    } 
    public void addNodeSelectionHandler(final NodeSelectionHandler handler) {
        nodeTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override 
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem selectedItem = event.getSelectedItem();
                Node node = (Node) selectedItem.getUserObject();
                handler.onNodeSelected(node);
            }
        });
    }  
    public void refresh() {
        loadNodes();
    }
    private void loadNodes() {
        nodeService.getAllNodes(new AsyncCallback<List<Node>>() {
            @Override
            public void onSuccess(List <Node> nodes) {
                nodeTree.buildTree(nodes);
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Failed to load nodes", caught); //
            }
        });
    }
}

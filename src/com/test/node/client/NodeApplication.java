package com.test.node.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.node.shared.Node;

public class NodeApplication implements EntryPoint {
    private Node selectedNode; 
    private InfoPanel infoPanel;
    private AllNodesPanel allNodesPanel;
    private TreePanel treePanel;   
    private ActionPanel actionsPanel;
    
    public void onModuleLoad() {
        infoPanel = new InfoPanel();
        allNodesPanel = new AllNodesPanel();
        treePanel = buildTreePanel();   
        actionsPanel = buildActionPanel();

        HorizontalPanel topPanel = buildTopPanel(treePanel, infoPanel);
        VerticalPanel page = new VerticalPanel();
        Label title = new Label("Client React Tree");
        title.setStyleName("appTitle");    
        page.add(title);
        page.add(topPanel);
        page.add(actionsPanel);
        page.add(allNodesPanel);    
        RootPanel.get().add(page);      
    } 
    private HorizontalPanel buildTopPanel(TreePanel treePanel, InfoPanel infoPanel) {
        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.add(treePanel);
        topPanel.add(infoPanel);
        topPanel.setStyleName("mainPanel");
        return topPanel;
    }  
    private ActionPanel buildActionPanel () {
        actionsPanel = new ActionPanel(new NodeActionHandler() {
            @Override
            public void onNodesChanged() {  
                treePanel.refresh();
                allNodesPanel.refresh();
            }
            @Override
            public void onNodeDeleted() {
                selectedNode = null;
                treePanel.refresh();
                allNodesPanel.refresh();
                infoPanel.clear();
                actionsPanel.clearSelectedNode();      
            }
        });
        return actionsPanel;
    } 
    private TreePanel buildTreePanel () {
        treePanel = new TreePanel(); 
        treePanel.addNodeSelectionHandler(new NodeSelectionHandler() {
            @Override
            public void onNodeSelected(Node node) {
                selectedNode = node;
                infoPanel.showNode(node);
                actionsPanel.setSelectedNode(node);
            }
        });
        return treePanel;
    } 
}


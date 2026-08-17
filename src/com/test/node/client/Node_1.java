package com.test.node.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Node_1 implements EntryPoint {
    
    private NodeStorage storage;
    private Node selectedNode;
    
    private FlexTable selectedTable;
    private Label selectedNodeLabel;
    private FlexTable allNodesTable;
    
    private NewTree newTree;
     
    //Selected panel
    private VerticalPanel buildInfoPanel() {
        VerticalPanel infoPanel = new VerticalPanel();
        Label infoTitle = new Label("Selected:");
        
        selectedTable = new FlexTable();
        
        selectedTable.setText(0, 0, "id");
        selectedTable.setText(0, 1, "-");
        selectedTable.setText(1, 0, "parentId");
        selectedTable.setText(1, 1, "-");
        selectedTable.setText(2, 0, "ip");
        selectedTable.setText(2, 1, "-");
        
        selectedTable.setStyleName("dataTable"); 
        infoPanel.add(infoTitle);
        infoPanel.add(selectedTable);
        infoPanel.setStyleName("panelBox");
        infoPanel.addStyleName("infoPanel");
        return infoPanel;
    }
    
    //Tree panel
    private VerticalPanel buildTreePanel(NewTree newTree) {
        VerticalPanel panel = new VerticalPanel();
        Label title = new Label("Tree:");
        
        panel.add(title);
        panel.add(newTree.getTree());
        
        panel.setStyleName("panelBox");
        panel.addStyleName("treePanel");
        
        return panel;
    }
    
    private NewTree createTree(List<Node> nodes) {
        newTree = new NewTree(); //may be
        newTree.buildTree(nodes);
        return newTree;
    }
    
    private void showSelectedNode(Node node) {
        selectedTable.setText(0, 1, String.valueOf(node.getId()));

        if (node.getParentId() == null) {
            selectedTable.setText(1, 1, "-");
        } else {
            selectedTable.setText(1, 1, String.valueOf(node.getParentId()));
        }

        selectedTable.setText(2, 1, node.getValue());
        
        selectedNodeLabel.setText("selected node id=" + node.getId());

    }
    
    //Click on node
    private void addTreeSelectionHandler(NewTree newTree) {
        newTree.addSelectionHandler(
                new SelectionHandler<TreeItem>() { //create object of class, which realize interface SelectionHandler<treeItem>
                    
                    @Override
                    public void onSelection(SelectionEvent<TreeItem> event) {
                        TreeItem selectedItem = event.getSelectedItem();
                        selectedNode = (Node) selectedItem.getUserObject(); //get out attached in newTREE.java Node
                        showSelectedNode(selectedNode);
                    }
                });
        
    }
    
    private HorizontalPanel buildActionsPanel() {
        HorizontalPanel actionsPanel = new HorizontalPanel();
        
        Button addRootButton = new Button("Add root node");
        addRootButton.addClickHandler(
                new ClickHandler() {
                    
                    @Override
                    public void onClick (ClickEvent event) {
                        String ip = Window.prompt("Enter IP:", " ");
                        if (ip != null && !ip.equals("")) {
                            storage.createNode(ip, null);
                            refreshTree();
                            fillAllNodesTable();
                        }
                                
                    }
                }
                );
        
        Button addChildButton = new Button("Add child");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        
        selectedNodeLabel = new Label("No node selected"); 
        selectedNodeLabel.setStyleName("selectedNodeLabel");
       
        actionsPanel.add(addRootButton); 
        actionsPanel.add(addChildButton); 
        actionsPanel.add(editButton); 
        actionsPanel.add(deleteButton); 
        actionsPanel.add(selectedNodeLabel); 
        actionsPanel.setCellVerticalAlignment(selectedNodeLabel, HasVerticalAlignment.ALIGN_MIDDLE);
        actionsPanel.setStyleName("actionsPanel"); 
        
        return actionsPanel; 
        
        
    }
    
    private VerticalPanel buildAllNodesPanel() {
        VerticalPanel panel = new VerticalPanel();
        Label title = new Label("All nodes:");
        title.setStyleName("sectionTitle"); 
        Button refreshButton = new Button("Refresh");
        allNodesTable = new FlexTable();
        
        allNodesTable.setStyleName("dataTable");
        panel.add(title);
        panel.add(refreshButton);
        panel.add(allNodesTable);
        
        panel.setStyleName("allNodesPanel");
        fillAllNodesTable();
        return panel;
        
    }
    
    private void fillAllNodesTable() {
        allNodesTable.removeAllRows();

        allNodesTable.setText(0, 0, "id");
        allNodesTable.setText(0, 1, "parentId");
        allNodesTable.setText(0, 2, "ip");

        List<Node> nodes = storage.getAllNodes();

        int row = 1;

        for (Node node : nodes) {
            allNodesTable.setText(row, 0, String.valueOf(node.getId()));

            if (node.getParentId() == null) {
                allNodesTable.setText(row, 1, "-");
            } else {
                allNodesTable.setText(row, 1, String.valueOf(node.getParentId()));
            }

            allNodesTable.setText(row, 2, node.getValue());
            row++;
        }
        
    }
    
    private HorizontalPanel buildTopPanel(VerticalPanel treePanel, VerticalPanel infoPanel) {
        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.add(treePanel);
        topPanel.add(infoPanel);
        topPanel.setStyleName("mainPanel");
        return topPanel;
    }
    
    private void refreshTree() {
        newTree.buildTree(storage.getAllNodes());
    }
    
    public void onModuleLoad() {
        
        storage = new NodeStorage();
        
        storage.createNode("192.168.1.10", null);
        storage.createNode("172.16.254.1", 1);
        storage.createNode("127.0.0.1", 1);
        storage.createNode("10.0.0.5", 3);
        
        
        
        newTree = createTree(storage.getAllNodes());
        
        VerticalPanel treePanel = buildTreePanel(newTree);
        VerticalPanel infoPanel = buildInfoPanel();
        
        HorizontalPanel actionsPanel = buildActionsPanel();
        VerticalPanel allNodesPanel = buildAllNodesPanel();
        
        addTreeSelectionHandler(newTree);
   
        //General
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
}


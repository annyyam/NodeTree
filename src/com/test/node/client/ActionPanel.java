package com.test.node.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.test.node.shared.Node;

public class ActionPanel extends Composite{
    private Node selectedNode;
    private NodeActionHandler actionHandler;
    private Label selectedNodeLabel;
    private ButtonPopupPanel buttonPopupPanel;
    private WarningPopup warningPopup;  
    private NodeServiceAsync nodeService;
   
    public ActionPanel(NodeActionHandler ActionHandler){
        this.nodeService = GWT.create(NodeService.class);
        this.actionHandler = ActionHandler;
        warningPopup = new WarningPopup();   
        buttonPopupPanel = new ButtonPopupPanel(new NodePopupHandler() {
            @Override
            public void onAddRoot(String ip, String port) {
                methodOnAddRoot(ip, port);
            }
            @Override
            public void onAddChild(String ip, Node parent, String port) {
                methodOnAddChild(ip, parent, port);
            }
            @Override
            public void onEdit(Node node, String ip, Integer parentId, String port) {
                methodOnEdit(node, ip, parentId, port);
            }
            @Override
            public void onDelete(Node node) {
                methodOnDelete(node);
            }
        });   
        HorizontalPanel actionsPanel = new HorizontalPanel();
        Button addRootButton = new Button("Add root node");
        addRootButton.addClickHandler(
                new ClickHandler() {         
                    @Override
                    public void onClick (ClickEvent event) {     
                        buttonPopupPanel.showAddRootPopup();                          
                    }
                });    
        Button addChildButton = new Button("Add child");
        addChildButton.addClickHandler(
                new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if (!checkSelectedNode("Please select a node before adding a child")) {
                            return;
                        }
                        buttonPopupPanel.showAddChildPopup(selectedNode); 
                    }             
                }); 
        Button editButton = new Button("Edit");
        editButton.addClickHandler(
                new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if (!checkSelectedNode("Please select a node before editing")) {
                            return;
                        }
                        buttonPopupPanel.showEditPopup(selectedNode);     
                    }
                }
                );
        Button deleteButton = new Button("Delete");
        deleteButton.addClickHandler(
                new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if (!checkSelectedNode("Please select a node before deleting")) {
                            return;
                        }
                        buttonPopupPanel.showDeletePopup(selectedNode);
                    }
                });
        selectedNodeLabel = new Label("No node selected"); 
        selectedNodeLabel.setStyleName("selectedNodeLabel");     
        actionsPanel.add(addRootButton);   
        actionsPanel.add(addChildButton); 
        actionsPanel.add(editButton); 
        actionsPanel.add(deleteButton); 
        actionsPanel.add(selectedNodeLabel); 
        actionsPanel.setCellVerticalAlignment(selectedNodeLabel, HasVerticalAlignment.ALIGN_MIDDLE);
        actionsPanel.setStyleName("actionsPanel");       
        initWidget(actionsPanel);       
    }  
    public void setSelectedNode(Node node) {
        selectedNode = node;
        selectedNodeLabel.setText("Selected node id=" + node.getId());
    }
    public void clearSelectedNode() {
        selectedNode = null;
        selectedNodeLabel.setText("No node selected");
    }   
    private void methodOnAddRoot(String ip, String port) {
        nodeService.createNode(ip, null, port, new AsyncCallback<Node>() {
            @Override
            public void onSuccess(Node node) {
                actionHandler.onNodesChanged();
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Failed to create node", caught);
                warningPopup.showPopup("Failed to create node.");
            }      
        });
    }   
    private void methodOnAddChild(String ip, Node parent, String port) {
        nodeService.createNode(ip, parent.getId(), port, new AsyncCallback<Node>() {
            @Override
            public void onSuccess(Node node) {
                actionHandler.onNodesChanged();
            }
            @Override
            public void onFailure(Throwable caught) {
                warningPopup.showPopup("Failed to create node.");
            }
        });
    }
     private void updateNode(Node node, String ip, Integer parentId, String port) {
        nodeService.updateNode(node.getId(), ip, parentId, port, new AsyncCallback<Void> () {
            @Override
            public void onSuccess(Void result) {
                actionHandler.onNodesChanged();
            }
            @Override
            public void onFailure(Throwable caught) {
                warningPopup.showPopup("Failed to update node.");
            }
        });
    }    
    private void methodOnEdit( final Node node, final String ip, final Integer parentId, final String port) {
        if (parentId == null) {
            updateNode(node, ip, null, port);
            return;
        }
        nodeService.getNode(parentId, new AsyncCallback<Node>() {
            @Override
            public void onSuccess(Node parent) {
                if (parent == null) {
                    warningPopup.showPopup("Please select a real parent node.");
                    return;
                }
                updateNode(node, ip, parentId, port);
            }
            @Override
            public void onFailure(Throwable caught) {
                warningPopup.showPopup("Failed to check parent node.");
            }
        });
    }   
    private void methodOnDelete(Node node) {
        nodeService.deleteNode(node.getId(), new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                selectedNode = null;
                actionHandler.onNodeDeleted();
                //actionHandler.onNodesChanged();
                selectedNodeLabel.setText("No node selected");
            }
            @Override
            public void onFailure(Throwable caught) {
                warningPopup.showPopup("Failed to delete node.");
            }
        });
    }    
    private boolean checkSelectedNode(String message) {
        if (selectedNode == null) {
            warningPopup.showPopup(message);
            return false;
        }
        return true;
    }
}
   
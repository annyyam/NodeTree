package com.test.node.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.node.shared.Node;

public class ButtonPopupPanel extends Composite {
    private PopupPanel NodeFormpopup; 
    private Label title;
    private Label nodeLabel;
    private TextBox ipBox;
    private TextBox parentIdBox;
    private TextBox portBox;
    private Button saveButton;
    private Button cancelButton; 
    private PopupMode mode;
    private Node selectedNode; 
    private NodePopupHandler popupHandler;   
    private PopupPanel deletePopup;
    private Label deleteTitle;
    private Label deleteMessage;
    private Label deleteWarning;
    private Button deleteButton;
    private Button deleteCancelButton;
    private Node deleteNode;   
    private WarningPopup warningPopup;
    
    public ButtonPopupPanel(NodePopupHandler nodePopupHandler) {
        this.popupHandler = nodePopupHandler;
        warningPopup = new WarningPopup();
        createNodeFormPopup();
        createDeletePopup();       
    }  
    public void showAddRootPopup() {
        mode = PopupMode.ADD_ROOT;
        selectedNode = null;
        title.setText("Add root node");
        nodeLabel.setText("");
        parentIdBox.setText("");
        parentIdBox.setEnabled(false);
        ipBox.setText("");
        portBox.setText("");
        portBox.setEnabled(true);
        saveButton.setText("Add");
        NodeFormpopup.center();
    }  
    public void showAddChildPopup(final Node parent) {
        mode = PopupMode.ADD_CHILD;
        selectedNode = parent;
        title.setText("Add child node");
        nodeLabel.setText("Parent: node-" + parent.getId());
        parentIdBox.setText("");
        parentIdBox.setEnabled(false);
        ipBox.setText("");
        portBox.setText("");
        portBox.setEnabled(true);
        saveButton.setText("Add");
        NodeFormpopup.center();
    } 
    public void showEditPopup(final Node node) {
        mode = PopupMode.EDIT;
        title.setText("Edit node");
        nodeLabel.setText("Node: node-" + node.getId());
        ipBox.setText(node.getValue());
        selectedNode = node;
        parentIdBox.setEnabled(true);
        if (node.getParentId() == null) {
            parentIdBox.setText("");
        } else {
            parentIdBox.setText(String.valueOf(node.getParentId()));
        }     
        portBox.setText(node.getPort());
        portBox.setEnabled(true);
        saveButton.setText("Save");
        NodeFormpopup.center();
    }
    public void showDeletePopup(Node node) {
       deleteNode = node;
       deleteTitle.setText("Delete node");
       deleteMessage.setText("Are you sure to delete node-" + node.getId() + "?");
       deletePopup.center();   
    }    
    private void OnClick(ClickEvent event) {
        String ip = ipBox.getText();
        final String port = portBox.getText();
        if (ip == null || ip.trim().isEmpty()) {
            warningPopup.showPopup("Please enter IP.");
            return;                
        }
        if (mode == PopupMode.ADD_ROOT) {
            popupHandler.onAddRoot(ip, port);
        }
        else if (mode == PopupMode.ADD_CHILD) {
            popupHandler.onAddChild(ip, selectedNode, port);
        }
        else if(mode == PopupMode.EDIT) {
            String parentIdText = parentIdBox.getText().trim();
            Integer newParentId;
            
            if (parentIdText.isEmpty()) {
                newParentId = selectedNode.getParentId();
            } else if (parentIdText.equalsIgnoreCase("null")) {
                newParentId = null;
            } else {
                try {
                    newParentId = Integer.parseInt(parentIdText);
                } catch (NumberFormatException e) {
                    warningPopup.showPopup("Parent ID must be a number or null.");
                    return;
                }      
        }
            popupHandler.onEdit(selectedNode, ip, newParentId, port);
        }
        NodeFormpopup.hide();
    }  
    private void createNodeFormPopup() {
        NodeFormpopup = new PopupPanel(true);
        NodeFormpopup.setGlassEnabled(true);
        NodeFormpopup.setStyleName("addRootPopup");
        title = new Label();
        nodeLabel = new Label();
        ipBox = new TextBox();
        parentIdBox = new TextBox();  
        portBox = new TextBox();
        saveButton = new Button("Save"); 
        cancelButton = new Button("Cancel");    
        VerticalPanel content = new VerticalPanel();  
        content.add(title);
        content.add(nodeLabel);
        content.add(new Label("IP:"));
        content.add(ipBox);
        content.add(new Label("Parent Id:"));
        content.add(parentIdBox);
        content.add(new Label("Port:"));
        content.add(portBox);
        content.add(saveButton);
        content.add(cancelButton);  
        NodeFormpopup.setWidget(content);
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                OnClick(event);
            }
        });   
        cancelButton.addClickHandler(new ClickHandler() {
            @Override 
            public void onClick(ClickEvent event) {
                NodeFormpopup.hide();
            }
        });
    }  
    private void createDeletePopup() {
        deletePopup = new PopupPanel(true);
        deletePopup.setGlassEnabled(true);
        deletePopup.setStyleName("addRootPopup");
        deleteTitle = new Label("Delete node");
        deleteMessage = new Label();
        deleteWarning = new Label("All child-nodes will also be deleted");
        deleteButton = new Button("Delete");
        deleteCancelButton = new Button("Cancel");
        VerticalPanel deleteContent = new VerticalPanel();
        deleteContent.add(deleteTitle);
        deleteContent.add(deleteMessage);
        deleteContent.add(deleteWarning);
        deleteContent.add(deleteButton);
        deleteContent.add(deleteCancelButton);
        deletePopup.setWidget(deleteContent); 
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                popupHandler.onDelete(deleteNode);
                deletePopup.hide();
            }
        });
        deleteCancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                deletePopup.hide();
            }
        });
    } 
}

package com.test.node.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.node.shared.Node;

public class InfoPanel extends Composite{
    private FlexTable selectedTable;
    
    public InfoPanel() {
        VerticalPanel infoPanel = new VerticalPanel();
        Label infoTitle = new Label("Selected:");  
        selectedTable = new FlexTable(); 
        selectedTable.setText(0, 0, "id");
        selectedTable.setText(0, 1, "-");
        selectedTable.setText(1, 0, "parentId");
        selectedTable.setText(1, 1, "-");
        selectedTable.setText(2, 0, "ip");
        selectedTable.setText(2, 1, "-"); 
        selectedTable.setText(3, 0, "port");
        selectedTable.setText(3, 1, "-"); 
        selectedTable.setStyleName("dataTable"); 
        infoPanel.add(infoTitle);
        infoPanel.add(selectedTable);
        infoPanel.setStyleName("panelBox");
        infoPanel.addStyleName("infoPanel"); 
        initWidget(infoPanel);
    }   
    public void showNode(Node node) {
        selectedTable.setText(0, 1, String.valueOf(node.getId()));
        if (node.getParentId() == null) {
            selectedTable.setText(1, 1, "-");
        } else {
            selectedTable.setText(1, 1, String.valueOf(node.getParentId()));
        }
        selectedTable.setText(2, 1, node.getValue());
        selectedTable.setText(3, 1, node.getPort());
    }   
    public void clear() {
        selectedTable.setText(0,1,"-");
        selectedTable.setText(1,1,"-");
        selectedTable.setText(2,1,"-");
        selectedTable.setText(3,1,"-");
    }
}

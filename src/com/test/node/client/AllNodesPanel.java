package com.test.node.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.node.shared.Node;

public class AllNodesPanel extends Composite {
    private FlexTable allNodesTable;
    private NodeServiceAsync nodeService;
    
    public AllNodesPanel() {
        nodeService = GWT.create(NodeService.class);
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
        refresh();
        initWidget(panel);
    }    
    public void refresh() {
        allNodesTable.removeAllRows();
        allNodesTable.setText(0, 0, "id");
        allNodesTable.setText(0, 1, "parentId");
        allNodesTable.setText(0, 2, "ip : port");
        nodeService.getAllNodes(new AsyncCallback<List<Node>> () {
            @Override
            public void onSuccess(List<Node> nodes) {
                int row = 1;
                for (Node node : nodes) {
                    allNodesTable.setText(row, 0, String.valueOf(node.getId()));

                    if (node.getParentId() == null) {
                        allNodesTable.setText(row, 1, "-");
                    } else {
                        allNodesTable.setText(row, 1, String.valueOf(node.getParentId()));
                    }
                    String nodePort = node.getPort() == null ? "" : node.getPort();
                    allNodesTable.setText(row, 2, node.getValue() + " : " + nodePort);
                    row++;
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Failed to load nodes", caught); //
                allNodesTable.setText(1, 0, "Failed to load nodes.");
            }
        });
    }
}

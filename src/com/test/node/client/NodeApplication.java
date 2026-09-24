package com.test.node.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.node.client.Action.ActionPresenter;
import com.test.node.client.Action.ActionView;
import com.test.node.client.AllNodes.AllNodesPresenter;
import com.test.node.client.AllNodes.AllNodesView;
import com.test.node.client.DeletePopup.DeletePopupPresenter;
import com.test.node.client.DeletePopup.DeletePopupView;
import com.test.node.client.Info.InfoPresenter;
import com.test.node.client.Info.InfoView;
//import com.test.node.client.ButtonPopup.ButtonPopupDisplay;
//import com.test.node.client.ButtonPopup.ButtonPopupPresenter;
//import com.test.node.client.ButtonPopup.ButtonPopupView;
import com.test.node.client.NodeFormPopup.NodeFormPopupPresenter;
import com.test.node.client.NodeFormPopup.NodeFormPopupView;
import com.test.node.client.Tree.TreePresenter;
import com.test.node.client.Tree.TreeView;
import com.test.node.shared.Node;

public class NodeApplication implements EntryPoint {
    private TreePresenter treePresenter;
    private InfoPresenter infoPresenter;
    private AllNodesPresenter allNodesPresenter;
    private ActionPresenter actionPresenter;
    //private ButtonPopupPresenter buttonPopupPresenter;
    private NodeFormPopupPresenter nodeFormPopupPresenter;
    private DeletePopupPresenter deletePopupPresenter;

    public void createInfo() {
        InfoView infoView = new InfoView();
        infoPresenter = new InfoPresenter(infoView);
    }

    public void createAllNodes() {
        AllNodesView allNodesView = new AllNodesView();
        allNodesPresenter = new AllNodesPresenter(allNodesView);
        allNodesPresenter.loadNodes();
    }

    public void createTreePanel() {
        TreeView treeView = new TreeView();
        treePresenter = new TreePresenter(treeView);
        treePresenter.addNodeSelectionHandler(new NodeSelectionHandler() {
            @Override
            public void onNodeSelected(Node node) {
                infoPresenter.showNodeOnPanel(node);
                
                
                actionPresenter.setSelectedNode(node);
            }
        });
        treePresenter.loadNodes();
    }

    public void createPopups() {
        NodeChangeHandler nodeChangeHandler = new NodeChangeHandler() {
            @Override
            public void onNodesChanged() {
                treePresenter.loadNodes();
                GWT.log("NODES CHANGED");
                allNodesPresenter.loadNodes();
            }
        };
        NodeFormPopupView nodeFormPopupView = new NodeFormPopupView();
        nodeFormPopupPresenter = new NodeFormPopupPresenter(nodeFormPopupView, nodeChangeHandler);
        DeletePopupView deletePopupView = new DeletePopupView();
        deletePopupPresenter =new DeletePopupPresenter(deletePopupView, nodeChangeHandler);
    }

    public void createAction() {
        ActionView actionView = new ActionView();
        actionPresenter = new ActionPresenter(actionView, nodeFormPopupPresenter, deletePopupPresenter);
    }

    public void onModuleLoad() {
        createInfo();
        createAllNodes();
        //createButtonPopup();
        createTreePanel();
        createPopups();
        createAction();

        HorizontalPanel topPanel = buildTopPanel(treePresenter, infoPresenter);
        VerticalPanel page = new VerticalPanel();
        Label title = new Label("Client React Tree");
        title.setStyleName("appTitle");
        page.add(title);
        page.add(topPanel);

        actionPresenter.go(page);
        allNodesPresenter.go(page);
        RootPanel.get().add(page);
    }

    private HorizontalPanel buildTopPanel(TreePresenter treePresenter, InfoPresenter infoPresenter) {
        HorizontalPanel topPanel = new HorizontalPanel();
        treePresenter.go(topPanel);
        infoPresenter.go(topPanel);
        topPanel.setStyleName("mainPanel");
        return topPanel;
    }
}

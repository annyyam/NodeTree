package com.test.node.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WarningPopup extends PopupPanel{
    private Label message;
    private Button okButton;
    
    public WarningPopup () {
        super(true);
        setGlassEnabled(true);
        setStyleName("addRootPopup");
        VerticalPanel popupContent = new VerticalPanel();
        message = new Label();
        okButton = new Button("OK");
        popupContent.add(message);
        popupContent.add(okButton);
        setWidget(popupContent);
        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }
    public void showPopup(String messageText) {
        message.setText(messageText);
        center();
    }
}

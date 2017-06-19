/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodexx.csorm.usermanagement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author kodexx
 */
public class UserManagementView extends VerticalLayout implements View {
    
    public static final String VIEW_NAME = "User Management";

    public UserManagementView() {
        CustomLayout usermanagementContent = new CustomLayout("comingsoon");
        usermanagementContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        usermanagementContent.addComponent(
                new Label(VaadinIcons.WRENCH.getHtml()
                        +" This section is not yet implemented but will be."
                        , ContentMode.HTML), "comingsooninfo"); 

        setSizeFull();
        setMargin(false);
        setStyleName("about-view");
        addComponent(usermanagementContent);
        setComponentAlignment(usermanagementContent, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

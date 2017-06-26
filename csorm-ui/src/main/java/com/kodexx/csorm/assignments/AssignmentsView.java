package com.kodexx.csorm.assignments;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AssignmentsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Assignments";

    public AssignmentsView() {
        CustomLayout assignmentsViewContent = new CustomLayout("comingsoon");
        assignmentsViewContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        assignmentsViewContent.addComponent(
                new Label(VaadinIcons.SPECIALIST.getHtml()+" " +VaadinIcons.WRENCH.getHtml()
                        +" This section is not yet implemented but will be."
                        , ContentMode.HTML), "comingsooninfo");

        setSizeFull();
        setMargin(false);
        setStyleName("about-view");
        addComponent(assignmentsViewContent);
        setComponentAlignment(assignmentsViewContent, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}

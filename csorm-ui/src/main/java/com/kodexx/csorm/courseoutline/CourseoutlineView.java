package com.kodexx.csorm.courseoutline;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class CourseoutlineView extends VerticalLayout implements View{
    public static final String VIEW_NAME = "Course Outline";

    public CourseoutlineView(){
        CustomLayout CourseoutlineViewContent =  new CustomLayout("comingsoon");
        CourseoutlineViewContent.setStyleName("about-content");

        CourseoutlineViewContent.addComponent(
                new Label(VaadinIcons.SPECIALIST.getHtml()+" " +VaadinIcons.WRENCH.getHtml()
                        +" This section is not yet implemented but will be."
                        , ContentMode.HTML), "comingsooninfo");
        setSizeFull();
        setMargin(false);
        setStyleName("about-view");
        addComponent(CourseoutlineViewContent);
        setComponentAlignment(CourseoutlineViewContent, Alignment.MIDDLE_CENTER);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
    }
}

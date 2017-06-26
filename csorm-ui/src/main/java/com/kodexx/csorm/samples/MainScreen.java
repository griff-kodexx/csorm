package com.kodexx.csorm.samples;

import com.kodexx.csorm.MyUI;
import com.kodexx.csorm.about.AboutView;
import com.kodexx.csorm.assignments.AssignmentsView;
import com.kodexx.csorm.courseoutline.CourseoutlineView;
import com.kodexx.csorm.files.FilesView;
import com.kodexx.csorm.groups.GroupsView;
import com.kodexx.csorm.timetable.TimetableView;
import com.kodexx.csorm.usermanagement.UserManagementView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 *
 *
 */
public class MainScreen extends HorizontalLayout {
    private Menu menu;


    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
    public MainScreen(MyUI ui) {
        
        setSpacing(false);
        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);

        //Files view
        menu.addView(new FilesView(), FilesView.VIEW_NAME,FilesView.VIEW_NAME,
                VaadinIcons.FILE);
        //Assignmnets View
        menu.addView(new AssignmentsView(), AssignmentsView.VIEW_NAME, AssignmentsView.VIEW_NAME,
                VaadinIcons.FILE);
        //Course Outline View
        menu.addView(new CourseoutlineView(), CourseoutlineView.VIEW_NAME, CourseoutlineView.VIEW_NAME,
                VaadinIcons.BOOK);
        //Groups View
        menu.addView(new GroupsView(), GroupsView.VIEW_NAME, GroupsView.VIEW_NAME,
                VaadinIcons.USERS);
        //Timetable View
        menu.addView(new TimetableView(), TimetableView.VIEW_NAME, TimetableView.VIEW_NAME,
                VaadinIcons.TABLE);
        //User Management
        menu.addView(new UserManagementView(), UserManagementView.VIEW_NAME, UserManagementView.VIEW_NAME,
                VaadinIcons.WRENCH);
        //About view
        menu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                VaadinIcons.INFO_CIRCLE);
        
        
        
        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }
}

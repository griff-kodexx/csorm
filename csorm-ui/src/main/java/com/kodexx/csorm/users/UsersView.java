package com.kodexx.csorm.users;

import com.kodexx.csorm.backend.data.User;
import com.kodexx.csorm.samples.ResetButtonForTextField;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UsersView extends CssLayout implements View {

    public static final String VIEW_NAME = "Users";
    private UsersGrid grid;
    private UserForm form;
    private TextField filter;
    
    private UsersLogic viewLogic = new UsersLogic(this);
    private Button newUser;
    
    private UsersDataProvider dataProvider = new UsersDataProvider();
    
    public UsersView(){
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        grid = new UsersGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

        form = new UserForm(viewLogic);

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.addComponent(topLayout);
        barAndGridLayout.addComponent(grid);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");

        addComponent(barAndGridLayout);
        addComponent(form);

        viewLogic.init();
    }
    
    public HorizontalLayout createTopBar(){
        filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setPlaceholder("Filter by reg number or name");
        filter.setCaption("Search for User");
        ResetButtonForTextField.extend(filter);
        
        //Apply the filter to the grid's data provider. TextField value isn never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
        
        newUser = new Button("New User");
        newUser.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newUser.setIcon(VaadinIcons.PLUS_CIRCLE);
        newUser.addClickListener(click -> viewLogic.newUser());
        
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newUser);
        topLayout.setComponentAlignment(filter, Alignment.TOP_LEFT);
        topLayout.setExpandRatio(filter,1);
        topLayout.setStyleName("top-bar");
        return topLayout;
        
    }
            

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        viewLogic.enter(event.getParameters());
    }
    
    public void showError(String msg){
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);
    }

    public void setNewUserEnabled(boolean enabled) {
        newUser.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
        form.setVisible(false);
    }

    public void selectRow(User row) {
        grid.getSelectionModel().select(row);
    }

    public User getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateUser(User user){
        dataProvider.save(user);
        //some FIX requires. check crudviewclass
    }
    
    public void removeUser(User user){
        dataProvider.delete(user);
    }

    public void editUser(User user){
        if(user != null){
            form.addStyleName("visible");
            form.setVisible(true);
            form.setEnabled(true);
        }else{
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
        form.editUser(user);
    }
 
}

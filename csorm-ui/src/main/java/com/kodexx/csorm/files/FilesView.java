package com.kodexx.csorm.files;


import com.kodexx.csorm.samples.ResetButtonForTextField;
import com.kodexx.csorm.backend.service.DataService;
import com.kodexx.csorm.backend.data.File;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FilesView extends CssLayout implements View{
    
    public static final String VIEW_NAME = "Files";
    private FilesGrid grid;
    private FileForm form;
    private TextField filter;
    
    private FilesLogic viewLogic = new FilesLogic(this);
    private Button newFile;
    
    private FilesDataProvider dataProvider = new FilesDataProvider();
    
    public FilesView(){
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar();
        
        grid = new FilesGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        
        form = new FileForm(viewLogic);
                
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
        filter.setPlaceholder("Filter by Unit Code, Title or Description");
        filter.setCaption("Search for File");
        ResetButtonForTextField.extend(filter);
        
        //Apply the filter to the grid's data provider. TextField value isn never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
        
        newFile = new Button("New File");
        newFile.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newFile.setIcon(VaadinIcons.PLUS_CIRCLE);
        newFile.addClickListener(click -> viewLogic.newFile());
        
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newFile);
        topLayout.setComponentAlignment(filter, Alignment.TOP_LEFT);
        topLayout.setExpandRatio(filter,1);
        topLayout.setStyleName("top-bar");
        return topLayout;                
    }
    
    @Override
    public void enter(ViewChangeEvent event){
        viewLogic.enter(event.getParameters());
    }
    
    public void showError(String msg){
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }
    
    public void showSaveNotification(String msg) {
        Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);
    }

    public void setNewFileEnabled(boolean enabled) {
        newFile.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
        form.setVisible(false);
    }

    public void selectRow(File row) {
        grid.getSelectionModel().select(row);
    }

    public File getSelectedRow() {
        return grid.getSelectedRow();
    }
    
    public void updateFile(File file){
        dataProvider.save(file);
        //some FIX requires. check crudviewclass
    }
    
    public void removeFile(File file){
        dataProvider.delete(file);
    }
    
    public void editFile(File file){
        if(file != null){
            form.addStyleName("visible");
            form.setVisible(true);
            form.setEnabled(true);
        }else{
            form.removeStyleName("visible");
            form.setEnabled(false);            
        }
        form.editFile(file);
    }
}

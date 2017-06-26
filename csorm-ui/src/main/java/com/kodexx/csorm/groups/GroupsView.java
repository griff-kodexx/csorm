package com.kodexx.csorm.groups;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupsView extends VerticalLayout implements View,Upload.Receiver,Upload.SucceededListener, Upload.ProgressListener {

    public static final String VIEW_NAME = "Groups";
    private ByteArrayOutputStream outputStream;
    final Image image = new Image("Uploaded image");
    ProgressBar bar = new ProgressBar(0.0f);



    public GroupsView() {
        CustomLayout aboutContent = new CustomLayout("comingsoon");
        aboutContent.setStyleName("about-content");

        Upload upload = new Upload();
        upload.setImmediateMode(false);
        upload.setButtonCaption("upload now");
        upload.setReceiver(this);
        upload.addSucceededListener(this);
        upload.addProgressListener(this);


        HorizontalLayout hr = new HorizontalLayout();
        hr.setSizeFull();
        hr.addComponent(upload);
        hr.addComponent(bar);

        hr.addComponent(image);
        addComponent(hr);
        setComponentAlignment(hr, Alignment.BOTTOM_RIGHT);

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.addComponent(
              new Label(VaadinIcons.SPECIALIST.getHtml()+" " +VaadinIcons.WRENCH.getHtml()
                        +" This section is not yet implemented but will be."
                        , ContentMode.HTML), "comingsooninfo");
        setSizeFull();
        setMargin(false);
        setStyleName("about-view");
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
    }

     private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(5000);
        notification.show(Page.getCurrent());
        notification.setPosition(Position.TOP_CENTER);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.outputStream = new ByteArrayOutputStream();
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        OutputStream out = null;
        File file = new File("/tmp/thefilename");
        try {
            out = new FileOutputStream(file);
            outputStream.writeTo(out);
            image.setSource(new FileResource(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GroupsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GroupsView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(GroupsView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {

        //Update the progress bar (bar.setValue()) accordingly
        //how?? sijui

    }


}

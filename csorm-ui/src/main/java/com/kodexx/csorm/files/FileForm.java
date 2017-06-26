package com.kodexx.csorm.files;

import com.kodexx.csorm.backend.data.File;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Upload;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * a form for editing a single file
 */
public class FileForm extends FilesFormDesign implements View,Upload.Receiver,Upload.SucceededListener, Upload.ProgressListener{

    private FilesLogic viewLogic;
    private Binder<File> binder;
    private File currentFile;
    private ByteArrayOutputStream outputStream;

    public FileForm(FilesLogic filesLogic){
        super();
        addStyleName("product-form");
        viewLogic = filesLogic;


        binder = new BeanValidationBinder<>(File.class);
        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event-> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });


        save.addClickListener(event -> {
            if(currentFile != null && binder.writeBeanIfValid(currentFile)){
                viewLogic.saveFile(currentFile);
            }
        });

        discard.addClickListener(event -> viewLogic.editFile(currentFile));
        cancel.addClickListener(event -> viewLogic.cancelFile());

        delete.addClickListener(event -> {
            if(currentFile !=null ){
                viewLogic.deleteFile(currentFile);
            }
        });

        //upload area
        //upload = new Upload();
        upload.setReceiver(this);
        upload.addSucceededListener(this);
        upload.addProgressListener(this);
        bar.setCaption("Progress");
        bar.setSizeFull();

    }


    public void editFile(File file){
        if(file == null){
            file = new File();
        }
        currentFile = file;
        binder.readBean(file);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);

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
        java.io.File tfile = new java.io.File("/tmp/thefilename");
        try {
            out = new FileOutputStream(tfile);
            outputStream.writeTo(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(FileForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        //Update the progress bar (bar.setValue()) accordingly
        //how?? sijui
    }
}
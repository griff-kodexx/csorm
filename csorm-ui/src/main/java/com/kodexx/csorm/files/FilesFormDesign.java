package com.kodexx.csorm.files;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.declarative.Design;

@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")

public class FilesFormDesign extends CssLayout{
    protected TextField title;
    protected TextField code;
    protected TextField description;
    protected Upload upload;
    protected ProgressBar bar;
    protected Button download;
    protected Button view;
    protected Button save;
    protected Button discard;
    protected Button cancel;
    protected Button delete;

    public FilesFormDesign(){
        Design.read(this);
    }

}

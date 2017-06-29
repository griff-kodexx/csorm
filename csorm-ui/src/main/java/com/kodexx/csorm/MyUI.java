package com.kodexx.csorm;

import com.kodexx.csorm.authentication.AccessControl;
import com.kodexx.csorm.authentication.BasicAccessControl;
import com.kodexx.csorm.authentication.LoginScreen;
import com.kodexx.csorm.authentication.LoginScreen.LoginListener;
import com.kodexx.csorm.files.FilesView;
import com.kodexx.csorm.samples.MainScreen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import javax.servlet.annotation.WebServlet;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("com.kodexx.csorm.MyAppWidgetset")
public class MyUI extends UI {
    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
            getPage().setTitle("CSORM");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        
        //change this to a better landing
        getNavigator().navigateTo(FilesView.VIEW_NAME);
    }


    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false, widgetset = "com.kodexx.csorm.AppWidgetSet")
    public static class MyUIServlet extends VaadinServlet {
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

/**
 *
 * @author kodexx
 */

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
@Theme("mytheme")
public class LoginUI extends UI {
    
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = LoginUI.class)
    public static class logiServlet extends VaadinServlet {
        
    }
    public static Authentication AUTH;
    @Override
    protected void init(VaadinRequest request){
        AUTH = new Authentication();
        new Navigator(this, this);
        
        getNavigator().addView(LoginPage.NAME, LoginPage.class);
        getNavigator().setErrorView(LoginPage.class);
        
        Page.getCurrent().addUriFragmentChangedListener(new UriFragmentChangedListener(){
            
            @Override
            public void uriFragmentChanged(UriFragmentChangedEvent event){
                router(event.getUriFragment());
            }
        });
        router("");
    }
    
    private void router(String route){
        Notification.show(route);
        if(getSession().getAttribute("user") != null){
            getNavigator().addView(SecurePage.NAME, SecurePage.class);
            getNavigator().addView(OtherSecurePage.NAME, OtherSecurePage.class);
            if(route.equals("!OtherSecure")){
                getNavigator().navigateTo(OtherSecurePage.NAME);
            }else{
                getNavigator().navigateTo(SecurePage.NAME);
            }
        }else{
            getNavigator().navigateTo(LoginPage.NAME);
        }
    }
}























































package com.kodexx.csorm.authentication;

import com.kodexx.csorm.backend.data.User;
import com.kodexx.csorm.backend.service.UserServiceImpl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    private TextField username;
    private PasswordField password;        
    private TextField fname;
    private TextField lname; 
    private TextField email; //email field requires touchkit plugin and sina bundles for now
    private Button login;
    private Button register;
    private Button toRegister;
    private Button forgotPassword;
    private Button resetPass;
    private Button backToLogin;
    private LoginListener loginListener;
    private AccessControl accessControl;

    public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        //i will have to implement register and forgot password forms for better code design
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(false);
        centeringLayout.setSpacing(false);
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm,
                Alignment.MIDDLE_CENTER);

        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();


        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);

       
        //for the login page, register and forgot password... all in one form
        loginForm.addComponent(username = new TextField("Username", "admin"));
        username.setWidth(15, Unit.EM);
        username.setDescription("Enter your username");
        loginForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);
        password.setDescription("Enter your Password");
        
        
        loginForm.addComponent(email = new TextField("Email"));
        email.setDescription("Enter the email you registered with");
        email.setWidth(15, Unit.EM);        
        loginForm.addComponent(fname = new TextField("First Name"));
        loginForm.addComponent(lname = new TextField("Last Name"));        
        

        
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        buttons.addComponent(login = new Button("Login"));
        buttons.addComponent(resetPass = new Button("Reset Password"));        
        buttons.addComponent(forgotPassword = new Button("Forgot password"));        
        buttons.addComponent(backToLogin = new Button("Login page"));
        
        loginForm.addComponent(buttons);
        
        
        loginForm.addComponent(toRegister = new Button("Register"));    
        toRegister.addStyleName(ValoTheme.BUTTON_LINK);
        toRegister.setIcon(VaadinIcons.ARROW_CIRCLE_RIGHT_O);
        
        loginForm.addComponent(register = new Button("Register"));    
        register.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        
       
        
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
        forgotPassword.setIcon(VaadinIcons.QUESTION_CIRCLE_O);
        backToLogin.addStyleName(ValoTheme.BUTTON_LINK);
        backToLogin.setIcon(VaadinIcons.ARROW_CIRCLE_LEFT_O);
      
        
        
        backToLogin.setSizeUndefined();
        email.setVisible(false);
        fname.setVisible(false);
        lname.setVisible(false);        
        resetPass.setVisible(false);
        backToLogin.setVisible(false);
        register.setVisible(false);
        
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {             //should catch some login exception (if any) here and do something with it
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        forgotPassword.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                username.setVisible(false);
                password.setVisible(false);
                login.setVisible(false);
                email.setVisible(true);
                resetPass.setVisible(true);
                forgotPassword.setVisible(false);
                backToLogin.setVisible(true);
            }
        });
        
        backToLogin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {                
                email.setVisible(false);
                resetPass.setVisible(false);
                fname.setVisible(false);
                lname.setVisible(false);
                backToLogin.setVisible(false);
                register.setVisible(false);
                username.setVisible(true);
                password.setVisible(true);
                login.setVisible(true);
                forgotPassword.setVisible(true);                
                toRegister.setVisible(true);
            }
        });
        resetPass.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        resetPass.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                showNotification(new Notification("Successful",
                    "Please check your email for the reset link.",
                    Notification.Type.HUMANIZED_MESSAGE));
                backToLogin.setVisible(true);
                email.setVisible(false);
                resetPass.setVisible(false);
            }
        });
        
       toRegister.addClickListener(new Button.ClickListener() {            
            @Override
            public void buttonClick(Button.ClickEvent event) {
                username.setVisible(true);
                password.setVisible(true);
                fname.setVisible(true);
                lname.setVisible(true);                ;
                email.setVisible(true);                
                register.setVisible(true);                 
                backToLogin.setVisible(true);
                toRegister.setVisible(false);
                login.setVisible(false);
                resetPass.setVisible(false);
                forgotPassword.setVisible(false);                
                
            }
        });
        
        register.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    useService();
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                showNotification(new Notification("Registration Successful",
                    "You can log in now.",
                    Notification.Type.HUMANIZED_MESSAGE));
                password.setValue("");
                fname.setValue("");
                lname.setValue("");
                email.setValue("");                
            }
        });
                
        return loginForm;
    }
    
    
        private void useService() throws NoSuchAlgorithmException, InvalidKeySpecException{
            User person = new User();            
            person.setId(-1);
            person.setEmail(username.getValue());
            person.setFirstName(fname.getValue());
            person.setLastName(lname.getValue());
            person.setPassword(password.getValue());
            person.setUsername(username.getValue());
            
            UserServiceImpl.get().updateUser(person);
            person.reset();
        }

    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>Login Information</h1>"
                        + "Log in with your <b>Student Registration Number</b> or with your <b>Staff ID </b>to gain Full administrative access to your files and Read-Only to others.",
                ContentMode.HTML);
        loginInfoText.setSizeFull();
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    private void login() {
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
        } else {
            showNotification(new Notification("Login failed",
                    "Please check your username and password and try again",
                    Notification.Type.WARNING_MESSAGE));
            username.focus();
        }
    }

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(5000);
        notification.show(Page.getCurrent());
        notification.setPosition(Position.TOP_CENTER);

    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}

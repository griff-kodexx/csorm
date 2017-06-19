package com.kodexx.csorm.authentication;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction;
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

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    private TextField username;
    private TextField email;
    private PasswordField password;
    private Button login;
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
        
        loginForm.addComponent(email = new TextField("Email"));
        email.setDescription("Enter the email you registered with");
        email.setWidth(15, Unit.EM);
        
        
        loginForm.addComponent(username = new TextField("Username", "admin"));        
        username.setWidth(15, Unit.EM);        
        username.setDescription("Enter your username");
        loginForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);
        password.setDescription("Enter your Password");
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Login"));
        buttons.addComponent(resetPass = new Button("Reset Password"));
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

        buttons.addComponent(forgotPassword = new Button("Forgot password?"));
        buttons.addComponent(backToLogin = new Button("Login page"));
        backToLogin.setSizeUndefined();
        email.setVisible(false);
        resetPass.setVisible(false);
        backToLogin.setVisible(false);
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
                username.setVisible(true);
                password.setVisible(true);
                login.setVisible(true);
                email.setVisible(false);
                resetPass.setVisible(false);
                forgotPassword.setVisible(true);
                backToLogin.setVisible(false);
            }
        });
        
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
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
        backToLogin.addStyleName(ValoTheme.BUTTON_LINK);
        return loginForm;
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

package pl.estrix.app.frontend.web;


import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("sessionMB")
@Scope("session")
@Setter
@Getter
public class SessionMangeBean extends MainController {

    private static final Logger log = LoggerFactory.getLogger(SessionMangeBean.class);

    private String email;

    private char[] password;

    private boolean loggedIn;
    private String originalURL;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
    }

    public String loginAction() {
        FacesMessage message = null;
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.email, String.valueOf(this.password)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            password = null;

            FacesContext fCtx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);

            String redirectUrl = (String)session.getAttribute("redirect_url");
            if (redirectUrl != null){
                return redirectUrl + "?faces-redirect=true";
            }

            return "/secured/dashboard.xhtml?faces-redirect=true";
        } catch (BadCredentialsException e) {
            log.error("Bad credentials for user {}", email);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bad credentials", null);
        } catch (DisabledException e) {
            log.error("User {} is disabled", email);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is disabled", null);
        } catch (AuthenticationException e) {
            log.error("Exception in authentication for user {} : {}", email, e.getMessage());
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to authenticate", null);
        }
        if (message != null) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage("loginFormMessages", message);
        }

        return "";
    }

    public void logoutAction(ActionEvent actionEvent){
        SecurityContextHolder.clearContext();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect("/login.html?faces-redirect=true");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void recordOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }


    private String actionText;

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUserAction(){
        actionText = "User action successfull";
        return "";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public String testAdminAction(){
        actionText = "Admin action successfull";
        return "";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testSuperuserAction(){
        actionText = "Superuser action successfull";
        return "";
    }
}
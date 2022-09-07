package pl.estrix.controller.ui;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Slf4j
@Data
@Scope(value = "session")
@Component(value = "loginController")
@ELBeanName(value = "loginController")
@Join(path = "/login", to = "/login.jsf")
public class LoginController {

    private String email;

    private char[] password;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String loginAction() {
        FacesMessage message = null;
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.email, String.valueOf(this.password)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            password = null;
            return "/secured/view/preview.html?faces-redirect=true";
        } catch (BadCredentialsException e) {
            log.error("Bad credentials for user {}", email);

            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Złe dane logowania", null);
        } catch (DisabledException e) {
            log.error("User {} is disabled", email);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Konto zablokowane", null);
        } catch (AuthenticationException e) {
            log.error("Exception in authentication for user {}", email);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Złe dane logowania", null);
        }
        if (message != null) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage("loginFormMessages", message);
        }

        return "";
    }
}

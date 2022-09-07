package pl.estrix.app.frontend.web.settings.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.user.service.UserService;
import pl.estrix.app.backend.user.service.impl.UserServiceImpl;
import pl.estrix.app.common.dto.model.UserDto;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Component("loggedUser")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class LoggedUserBean implements Serializable {

    private UserDto user;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContext.getAuthentication().getName();

        user = userService.getItem(username);
    }

}

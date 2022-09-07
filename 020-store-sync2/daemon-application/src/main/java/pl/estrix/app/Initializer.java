package pl.estrix.app;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.stereotype.Component;
import pl.estrix.app.authentication.SessionListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Component
public class Initializer implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {

        sc.addListener(new SessionListener());
        sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
    }


}
package pl.estrix.app.authentication;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ExpiredSessionExceptionHandler implements MessageSourceAware {

    public static final String COOKIE_SESSION_EXPIRED_NAME = "sessionExpired";

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Setter
    private MessageSource messageSource;

    public void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {

        System.out.println("ExpiredSessionExceptionHandler.request: " + request.getRequestURI());
        String requestUrl = "";
//        System.out.println();

        handleRegularRequest(request, response, requestUrl);

        log.debug("{} : clearing invalid session attribute", requestUrl);
//        ExpiredSessionMarkerFilter.setSessionExpired(request, false);
    }

    private void handleRegularRequest(HttpServletRequest request, HttpServletResponse response, String requestUrl) throws IOException {
        Cookie cook = new Cookie(COOKIE_SESSION_EXPIRED_NAME, "true");
        cook.setPath("/");
        response.addCookie(cook);
        redirectStrategy.sendRedirect(request, response, requestUrl);
    }
}
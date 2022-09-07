package pl.estrix.app.authentication;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExpiredSessionMarkerFilter extends OncePerRequestFilter {

    public static final String EXPIRED_SESSION_ATTRIBUTE = ExpiredSessionMarkerFilter.class.getName() + ".EXPIRED_SESSION";

    public static boolean isSessionExpired(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return true;
        }

        return session.isNew();

//        return Objects.equal(session.getAttribute(EXPIRED_SESSION_ATTRIBUTE), Boolean.TRUE);
    }

    public static void setSessionExpired(HttpServletRequest request, boolean sessionExpired) {
        HttpSession session = request.getSession();

        if (sessionExpired) {
            session.setAttribute(EXPIRED_SESSION_ATTRIBUTE, Boolean.TRUE);
        } else {
            session.removeAttribute(EXPIRED_SESSION_ATTRIBUTE);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isRequestedSessionIdInvalid(request)) {

            // create new session if required
            String newSessionId = request.getSession(true).getId();

//            logger.debug(String.format("%s : invalid session detected and replaced: %s -> %s",
//                    ServletUriComponentsBuilder.fromRequest(request).build().toString(), request.getRequestedSessionId(), newSessionId));

            setSessionExpired(request, true);

        }

        filterChain.doFilter(request, response);
    }

    boolean isRequestedSessionIdInvalid(HttpServletRequest request) {
        return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
    }

}

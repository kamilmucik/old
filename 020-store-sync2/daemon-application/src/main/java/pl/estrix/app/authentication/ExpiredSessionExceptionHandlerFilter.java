package pl.estrix.app.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ExpiredSessionExceptionHandlerFilter extends GenericFilterBean {

    private static final String FACES_REQUEST_HEADER = "faces-request";

    private final ExpiredSessionExceptionHandler expiredSessionExceptionHandler;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = HttpServletRequest.class.cast(req);
        HttpServletResponse response = HttpServletResponse.class.cast(res);

        boolean ajaxRedirect = "partial/ajax".equals(request.getHeader(FACES_REQUEST_HEADER));

        if (!request.getRequestURL().toString().contains("javax.faces.resource")) {

            if (request.getRequestURI().startsWith("/secured/")){
                String url = request.getRequestURI();
                HttpSession session = request.getSession();
                session.setAttribute("redirect_url", url);

//                System.out.println("" + request.getRequestURL());
//                System.out.println("" + request.getRequestURI());
//                request.getParameterMap().forEach( (k,v) -> {
//                    for (String vv : v) {
//                        System.out.println(k + " : " + vv);
//                    }
//                });
            }
        }


        if (ExpiredSessionMarkerFilter.isSessionExpired(request)) {

            if (ajaxRedirect) {
                String requestURI = getRequestUrl(request);
                String ajaxRedirectXml = createAjaxRedirectXml("secured/dashboard.xhtml");
                response.setContentType("text/xml");
                response.getWriter().write(ajaxRedirectXml);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            // skip expired session exception handling if session is not expired
            chain.doFilter(request, response);
        }
    }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();

        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            requestURL.append("?").append(queryString);
        }

        return requestURL.toString();
    }

    private String createAjaxRedirectXml(String redirectUrl) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<partial-response><redirect url=\"")
                .append(redirectUrl)
                .append("\"></redirect></partial-response>")
                .toString();
    }

}

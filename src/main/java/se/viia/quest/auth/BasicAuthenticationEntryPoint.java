package se.viia.quest.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author affe 2018-04-26
 */
public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String headerValue;

    public BasicAuthenticationEntryPoint(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("WWW-Authenticate", this.headerValue);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage());
    }
}

package se.viia.quest.auth;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import se.viia.quest.exception.InvalidTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author affe 2018-04-25
 */
public class JwtAuthenticationFilter extends GenericFilterBean {
    private static final Logger LOG = getLogger(JwtAuthenticationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final AntPathRequestMatcher requestMatcher;

    public JwtAuthenticationFilter(AntPathRequestMatcher requestMatcher) {
        Preconditions.checkArgument(requestMatcher != null, "Argument requestMatcher should no be null!");
        this.requestMatcher = requestMatcher;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Should we process this url?
        if (requestMatcher != null && !requestMatcher.matches(httpRequest)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = httpRequest.getHeader(AUTHORIZATION_HEADER);
        if (Strings.isNullOrEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Token token = TokenHandler.validateAndGet(accessToken);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(token.getUsername(), "", token.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            LOG.warn("Failed to extract user information from token. Token was invalid! ");
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}

package ua.ms.configuration.security.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.ms.configuration.security.service.RegistrationService;
import ua.ms.entity.user.User;

import java.io.IOException;
import java.util.Optional;

import static ua.ms.util.ApplicationConstants.Security.TOKEN_HEADER_NAME;
import static ua.ms.util.ApplicationConstants.Security.TOKEN_PREFIX;

@Log4j2
@Component("jwtFilter")
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final RegistrationService registrationService;
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWT filter was called, attempt to get token");
        String authHeader = httpServletRequest.getHeader(TOKEN_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                log.debug("Invalid JWT Token, sending error");
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String username = jwtUtils.getClaimFromToken(jwt);
                    Optional<User> user = registrationService.loadByUsername(username);
                    if (user.isPresent() && user.get().isAccountNonLocked()) {

                        UserDetails userDetails = user.get();
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails,
                                        userDetails.getPassword(),
                                        userDetails.getAuthorities());

                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            log.debug("ok, filter passes the request");
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                } catch (JWTVerificationException exc) {
                    log.debug("Invalid JWT Token, sending error");
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

package com.eAuction.e_backend.Security;

import com.eAuction.e_backend.Service.Impl.jwtService;
import com.eAuction.e_backend.vo.Util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.eAuction.e_backend.Exception.SessionExpiredException;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private jwtService userDetailsService;

    @Autowired
    private Util jwtUtil;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwt = null;
            if (
                authorizationHeader != null &&
                !("Bearer null".equals(authorizationHeader)) &&
                authorizationHeader.startsWith("Bearer ")
            ) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (
                username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(
                        authenticationToken
                    );
                }
            }
            chain.doFilter(request, response);
        } catch (io.jsonwebtoken.JwtException e) {
            exceptionResolver.resolveException(request, response, null, new SessionExpiredException("Your session has expired or is invalid. Please log in again."));
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }
}

package com.eAuction.e_backend.vo;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.eAuction.e_backend.Exception.SessionExpiredException;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtWebSocketInterceptor implements ChannelInterceptor {

    private final Util jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtWebSocketInterceptor(Util jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = extractToken(accessor);
            
            if (token != null) {
                try {
                    String username = jwtUtil.extractUsername(token);
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    
                    // Combined the checks to avoid redundant parsing
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        
                        // Set authentication for Spring Security & the WebSocket session
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        accessor.setUser(authentication);
                    } else {
                        // Clear context if token validation fails
                        SecurityContextHolder.clearContext();
                    }
                } catch (ExpiredJwtException e) {
                    throw new SessionExpiredException("WebSocket session expired due to invalid token.");
                }
            }
        }
        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        String header = accessor.getFirstNativeHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
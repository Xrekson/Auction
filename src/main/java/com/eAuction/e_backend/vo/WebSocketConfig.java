package com.eAuction.e_backend.vo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtWebSocketInterceptor jwtInterceptor;

    public WebSocketConfig(JwtWebSocketInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // MUST uncomment this to enable the security check you wrote!
        registration.interceptors(jwtInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/main");
        config.setApplicationDestinationPrefixes("/auc");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websoc")
                .setAllowedOriginPatterns("*");
        // .withSockJS();
    }
}
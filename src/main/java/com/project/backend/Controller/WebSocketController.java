package com.project.backend.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	@MessageMapping("/message") // Clients will send messages to /app/message
    @SendTo("/topic/response") // Responses will be sent to subscribers of /topic/response
    public String processMessage(String message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return "Server received: " + message;
    }
}

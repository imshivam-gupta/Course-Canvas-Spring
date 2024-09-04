package com.example.coursecanvasspring.controller;//package com.example.coursecanvasspring.controller;
//
////import org.springframework.messaging.handler.annotation.MessageMapping;
////import org.springframework.messaging.handler.annotation.SendTo;
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.RestController;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@RestController
////public class WebSocketController extends TextWebSocketHandler {
////
////    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////
////    @Override
////    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
////        String payload = message.getPayload();
////        // Parse message
////        Map<String, Object> parsedMessage = parseMessage(payload);
////        String type = (String) parsedMessage.get("type");
////
////        if ("sender".equals(type)) {
////            sessions.put("sender", session);
////        } else if ("receiver".equals(type)) {
////            sessions.put("receiver", session);
////        } else {
////            handleSignalingMessage(parsedMessage, session);
////        }
////    }
////
////    private void handleSignalingMessage(Map<String, Object> message, WebSocketSession senderSession) throws IOException {
////        String type = (String) message.get("type");
////        WebSocketSession receiverSession = sessions.get("receiver");
////        WebSocketSession sender = sessions.get("sender");
////
////        if ("createOffer".equals(type) && senderSession.equals(sender)) {
////            if (receiverSession != null) {
////                receiverSession.sendMessage(new TextMessage(message.toString()));
////            }
////        } else if ("createAnswer".equals(type) && senderSession.equals(receiverSession)) {
////            if (sender != null) {
////                sender.sendMessage(new TextMessage(message.toString()));
////            }
////        } else if ("iceCandidate".equals(type)) {
////            WebSocketSession targetSession = senderSession.equals(sender) ? receiverSession : sender;
////            if (targetSession != null) {
////                targetSession.sendMessage(new TextMessage(message.toString()));
////            }
////        }
////    }
////
////    private Map<String, Object> parseMessage(String payload) {
////        // Implement your message parsing logic here
////        return Map.of(); // Placeholder, replace with actual parsing
////    }
////}
//package com.example.coursecanvasspring.controller;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @MessageMapping("/initialize")
//    public void initializeSender(String message) {
//        System.out.println("Initialize sender: " + message);
//        messagingTemplate.convertAndSend("/topic/message", message);
//    }
//
//
//    @MessageMapping("/sender/offer")
//    public void handleSenderCreateOffer(String offer) {
//        System.out.println("Received create offer from sender: " + offer);
//        messagingTemplate.convertAndSend("/receiver/createOffer", offer);
//    }
//
//    @MessageMapping("/receiver/offer")
//    public void handleReceiverCreateOffer(String offer) {
//        System.out.println("Received create offer from receiver: " + offer);
//        messagingTemplate.convertAndSend("/sender/createOffer", offer);
//    }
//
//    @MessageMapping("/sender/answer")
//    public void handleSenderCreateAnswer(String answer) {
//        System.out.println("Received create answer from sender: " + answer);
//        messagingTemplate.convertAndSend("/receiver/createAnswer", answer);
//    }
//
//    @MessageMapping("/receiver/answer")
//    public void handleReceiverCreateAnswer(String answer) {
//        System.out.println("Received create answer from receiver: " + answer);
//        messagingTemplate.convertAndSend("/sender/createAnswer", answer);
//    }
//
//    @MessageMapping("/receiver/iceCandidate")
//    public void handleReceiverIceCandidate(String candidate) {
//        System.out.println("Received ICE candidate from receiver: " + candidate);
//        messagingTemplate.convertAndSend("/sender/iceCandidate", candidate);
//    }
//
//    @MessageMapping("/sender/iceCandidate")
//    public void handleSenderIceCandidate(String candidate) {
//        System.out.println("Received ICE candidate from sender: " + candidate);
//        messagingTemplate.convertAndSend("/receiver/iceCandidate", candidate);
//    }
//
//}

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/initialize")
    public void initializeSender(String message) {
        System.out.println("Initialize sender: " + message);
        messagingTemplate.convertAndSend("/topic/initialize", message);
    }

    @MessageMapping("/sender/offer")
    public void handleSenderCreateOffer(String offer) {
        System.out.println("Received create offer from sender: " + offer);
        messagingTemplate.convertAndSend("/topic/receiverOffer", offer);
    }

    @MessageMapping("/receiver/offer")
    public void handleReceiverCreateOffer(String offer) {
        System.out.println("Received create offer from receiver: " + offer);
        messagingTemplate.convertAndSend("/topic/senderOffer", offer);
    }

    @MessageMapping("/sender/answer")
    public void handleSenderCreateAnswer(String answer) {
        System.out.println("Received create answer from sender: " + answer);
        messagingTemplate.convertAndSend("/topic/receiverAnswer", answer);
    }

    @MessageMapping("/receiver/answer")
    public void handleReceiverCreateAnswer(String answer) {
        System.out.println("Received create answer from receiver: " + answer);
        messagingTemplate.convertAndSend("/topic/senderAnswer", answer);
    }

    @MessageMapping("/receiver/iceCandidate")
    public void handleReceiverIceCandidate(String candidate) {
        System.out.println("Received ICE candidate from receiver: " + candidate);
        messagingTemplate.convertAndSend("/topic/senderIceCandidate", candidate);
    }

    @MessageMapping("/sender/iceCandidate")
    public void handleSenderIceCandidate(String candidate) {
        System.out.println("Received ICE candidate from sender: " + candidate);
        messagingTemplate.convertAndSend("/topic/receiverIceCandidate", candidate);
    }

}

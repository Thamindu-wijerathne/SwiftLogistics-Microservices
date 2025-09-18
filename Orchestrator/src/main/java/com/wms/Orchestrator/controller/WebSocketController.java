package com.wms.Orchestrator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;


    public void broadcastPackageUpdate(String packageJson) {
        messagingTemplate.convertAndSend("/topic/package-updates", packageJson);
        log.info("Message sent to front-ends : {}",packageJson);
    }

}

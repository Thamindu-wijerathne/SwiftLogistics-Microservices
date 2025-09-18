package com.wms.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.demo.dto.DeliveryPackageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
@Service
public class TCPService {
    private final ObjectMapper mapper;

    public TCPService(ObjectMapper mapper) {
        this.mapper = mapper; // Spring Boot auto-configured mapper includes JavaTimeModule
    }

    public void SendTCPMessage(DeliveryPackageDTO deliveryPackage){
        try (Socket socket = new Socket("localhost", 9100); // Orchestrator's Or API gateway's  TCP listening Port
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String json = mapper.writeValueAsString(deliveryPackage);
            out.println(json);
            System.out.println("Sent to Orchestrator: " + json);
        } catch (Exception e) {
            log.error("ERROR WHILE BROADCASTING :",e);
        }
    }
}

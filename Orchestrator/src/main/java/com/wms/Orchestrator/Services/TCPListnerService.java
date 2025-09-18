package com.wms.Orchestrator.Services;

import com.wms.Orchestrator.controller.WebSocketController;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@Component
public class TCPListnerService {
    @Autowired
    private  WebSocketController wsController; // inject controller

    private static final int TCPPort= 9100; //TCP Listning Port

    @PostConstruct
    public void startTCPListner(){
        new Thread(()-> {
            try (ServerSocket serverSocket = new ServerSocket(TCPPort)) {
                log.info("<------------ TCP Listner Started on Port {} ------------->", TCPPort);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();

                }
            } catch (Exception e) {
                log.error("TCP Listener error: ", e);
            }
        }).start();
    }

    private void handleClient(Socket clientSocket){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String msg; //tcp packet
            while ((msg = in.readLine()) != null) {
                log.info("Received from WMS: {}", msg);

                wsController.broadcastPackageUpdate(msg); //broadcasting recieved tcp packet to frontend.


            }
        } catch (Exception e) {
            log.error("Error handling client", e);
        }
    }

}

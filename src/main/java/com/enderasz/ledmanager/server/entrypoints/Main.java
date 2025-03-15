package com.enderasz.ledmanager.server.entrypoints;

import com.enderasz.ledmanager.server.ui.ClientManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting LED Manager Server");

        ServerSocket socket;
        try {
            socket = new ServerSocket(5555);
            logger.info("Server listening on port 5555 established");
        } catch (IOException e) {
            logger.error("Server socket setup failed", e);
            return;
        }

        Integer newClientId = 1;

        while(true) {
            logger.info("Awaiting for new client connection");
            Socket clientSocket;
            try {
                clientSocket = socket.accept();
            } catch (IOException e) {
                logger.error("Client connection failed", e);
                continue;
            }
            logger.info("New client connected - " + newClientId);
            new ClientManager(clientSocket, newClientId).start();
            newClientId++;
        }
    }
}
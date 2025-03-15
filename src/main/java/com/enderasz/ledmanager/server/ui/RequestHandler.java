package com.enderasz.ledmanager.server.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RequestHandler {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    private final ClientManager clientManager;

    public RequestHandler(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public boolean handle() {
        Route requestRoute;
        try {
            requestRoute = readRequest();
        } catch (IOException e) {
            return false;
        }

        if(requestRoute == null) {
            logger.error("Invalid request received");
            return false;
        }

        logger.info("Handling request: " + requestRoute);
        requestRoute.handle(clientManager);
        logger.info("Request handled");
        return true;
    }

    private Route readRequest() throws IOException {
        String message;
        try {
            message = clientManager.readMessage();
        } catch (IOException e) {
            logger.warn("Failed to read a request", e);
            throw e;
        }
        return Route.getRouter(message);
    }
}

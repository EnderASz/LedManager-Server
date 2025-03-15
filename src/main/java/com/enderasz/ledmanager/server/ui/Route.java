package com.enderasz.ledmanager.server.ui;

import com.enderasz.ledmanager.server.ui.endpoints.ConfigSave;
import com.enderasz.ledmanager.server.ui.endpoints.ConfigUpload;
import com.enderasz.ledmanager.server.ui.endpoints.Handshake;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum Route {
    HANDSHAKE("H", Handshake::new),
    CONFIG_SAVE("CFG_S", ConfigSave::new),
    CONFIG_UPLOAD("CFG_U", ConfigUpload::new);

    private static final Logger logger = LogManager.getLogger();

    private final String requestSignature;
    private final Supplier<Consumer<ClientManager>> endpointFactory;

    public static Route getRouter(String requestSignature) {
        for (Route router : Route.values()) {
            if (router.requestSignature.equals(requestSignature)) {
                return router;
            }
        }
        return null;
    }

    Route(String requestSignature) {
        this.requestSignature = requestSignature;
        this.endpointFactory = null;
    }

    Route(String requestSignature, Supplier<Consumer<ClientManager>> endpointFactory) {
        this.requestSignature = requestSignature;
        this.endpointFactory = endpointFactory;
    }

    public void handle(ClientManager clientManager) {
        if(endpointFactory == null) {
            logger.debug("No endpoint factory available for route {} - no request handle procedure", this);
            return;
        }
        Consumer<ClientManager> endpoint = endpointFactory.get();
        if (endpoint == null) {
            logger.debug("No endpoint available for route {} - no request handle procedure", this);
            return;
        }
        endpoint.accept(clientManager);
    }
}

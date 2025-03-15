package com.enderasz.ledmanager.server.ui.endpoints;

import com.enderasz.ledmanager.server.domain.LightConfig;
import com.enderasz.ledmanager.server.ui.ClientManager;
import com.enderasz.ledmanager.server.ui.ConfigReceiver;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigUpload implements Consumer<ClientManager> {
    @Override
    public void accept(ClientManager clientManager) {
        Map<Integer, LightConfig> config = receive(clientManager);
        upload(config);
    }

    private static Map<Integer, LightConfig> receive(ClientManager clientManager) {
        try {
            return new ConfigReceiver(clientManager).receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void upload(Map<Integer, LightConfig> config) {

    }
}

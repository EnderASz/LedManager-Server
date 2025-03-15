package com.enderasz.ledmanager.server.ui.endpoints;

import com.enderasz.ledmanager.server.ui.ClientManager;

import java.util.function.Consumer;

public class Handshake implements Consumer<ClientManager> {
    @Override
    public void accept(ClientManager clientManager) {
        clientManager.sendMessage("H");
    }
}

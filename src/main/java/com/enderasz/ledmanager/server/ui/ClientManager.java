package com.enderasz.ledmanager.server.ui;

import com.enderasz.ledmanager.server.common.BytesConverter;
import com.enderasz.ledmanager.server.entrypoints.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientManager extends Thread {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private Integer clientId;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientManager(Socket socket, Integer clientId) {
        this.clientId = clientId;
        this.setName("ClientHandler-" + clientId);

        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        logger.info("Client Handler started");
        boolean result;
        RequestHandler requestHandler = new RequestHandler(this);
        do {
            result = requestHandler.handle();
        } while(result);
        try {
            socket.close();
        } catch (IOException e) {
            logger.error("Error closing socket", e);
        }
        logger.info("Client Handler stopped");
    }

    public String readMessage() throws IOException, SocketException {
        List<Byte> message = new ArrayList<>();

        int input;
        do {
            try {
                input = in.read();
            } catch (SocketException e) {
                logger.trace("Message read interrupted dues to unexpected connection close");
                throw e;
            } catch (IOException e) {
                logger.trace("Message read interrupted due to an error");
                throw e;
            }

            if(input == -1) {
                logger.trace("Message read interrupted due to unexpected end of stream");
                throw new IOException("Message read interrupted due to unexpected end of stream");
            }

            if(input > 32 && input < 127) {
                logger.debug("Received byte: " + BytesConverter.charToHex((char) input) + " - " + (char) input);
            } else {
                logger.debug("Received byte: " + BytesConverter.charToHex((char) input));
            }

            message.add((byte) input);
        } while(input != 0);

        int messageLength = message.size() - 1;
        byte[] messagePrimitive = new byte[messageLength];
        for(int i = 0; i < messageLength; i++) {
            messagePrimitive[i] = message.get(i);
        }

        return new String(messagePrimitive);
    }

    public void sendMessage(byte[] message) {
        for (byte b : message) {
            out.write(b);
            if(b > 32 && b < 127) {
                logger.debug("Sending byte: " + BytesConverter.charToHex((char) b) + " - " + (char) b);
            } else {
                logger.debug("Sending byte: " + BytesConverter.charToHex((char) b));
            }
        }
        if(message[message.length - 1] != 0) {
            logger.debug("Sending additional null character");
            out.write(0);
        }
        out.flush();
    }

    public void sendMessage(String message) {
        sendMessage(message, StandardCharsets.UTF_8);
    }

    public void sendMessage(String message, Charset charset) {
        sendMessage(message.getBytes(charset));
    }
}

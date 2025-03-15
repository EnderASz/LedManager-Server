package com.enderasz.ledmanager.server.entrypoints;

import com.enderasz.ledmanager.server.common.BytesConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class TestClientMain {
    private static final Logger logger = LogManager.getLogger();

    private static void connectAndRun() throws IOException {
        Socket socket = new Socket("127.0.0.1", 5555);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Scanner scanner = new Scanner(System.in);

        logger.info("New connection established");

        AtomicReference<Boolean> error = new AtomicReference<>(false);
        AtomicReference<Boolean> close = new AtomicReference<>(false);

        new Thread(() -> {
            while(true) {
                int input;
                try {
                    input = in.read();
                } catch (IOException e) {
                    logger.error("Connection error when reading");
                    error.set(true);
                    return;
                }

                if(input == -1) {
                    close.set(true);
                    logger.warn("Connection closed while reading");
                    return;
                }

                if(input > 32 && input < 127) {
                    logger.info("Received byte: " + BytesConverter.charToHex((char) input) + " - " + (char) input);
                } else {
                    logger.info("Received byte: " + BytesConverter.charToHex((char) input));
                }
            }
        }, "SocketInput").start();

        while(true) {
            String userInput = scanner.nextLine();
            if(error.get()) {
                logger.warn("Message send interrupted due to a connection error - aborting procedure");
                return;
            }
            if(close.get()) {
                logger.info("Connection closed - aborting send procedure");
                return;
            }
            byte[] bytes = BytesConverter.stringToBytes(userInput);
            logger.info("Sending a message: " + BytesConverter.stringifyHex(bytes));
            for(byte b: bytes) {
                out.write(b);
            }
            out.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        while(true) {
            try {
                connectAndRun();
            } catch (IOException e) {
                logger.warn("Reestablishing connection");
            }
        }
    }
}

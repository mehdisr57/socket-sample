package com.msrazavi.test.concurrency.concurrency1.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author M_SadatRazavi
 */
public record ClientReceiver(Socket socket) implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientReceiver.class);

    @Override
    public void run() {
        LOGGER.info("receiver started");
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
            while (!this.socket.isClosed()) {
                LOGGER.info("wait for Message from server...");
                String message = inputStream.readUTF();
                LOGGER.info("Message received from server: {}", message);
            }
        } catch (IOException e) {
            LOGGER.error("error on Receiver", e);
        }
    }
}

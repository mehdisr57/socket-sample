package com.msrazavi.test.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author M_SadatRazavi
 */
public record ClientSender(Socket socket) implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSender.class);

    @Override
    public void run() {
        LOGGER.info("ClientSender started");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String inputText = "start";
        try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            while (!inputText.equals("exit")) {
                inputText = bufferedReader.readLine();
                LOGGER.info("Sending request to Socket {} - {}", socket, inputText);
                outputStream.writeUTF(inputText);
                outputStream.flush();
            }
        } catch (IOException e) {
            LOGGER.error("error on Sender", e);
        }
        LOGGER.info("ClientSender stopped");
    }
}

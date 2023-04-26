package com.msrazavi.test.socket.client;

import com.msrazavi.test.socket.common.util.JsonUtil;
import com.msrazavi.test.socket.common.util.MessageStatusBuilder;
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
                final String[] split = inputText.split(" ");
                if (split.length == 2) {
                    sendMessage(outputStream,
                            JsonUtil.instance().serialize(MessageStatusBuilder.ofSentToServer(split[0], split[1]))
                    );
                } else {
                    sendMessage(outputStream, inputText);
                }
            }
        } catch (IOException e) {
            LOGGER.error("error on Sender", e);
        }
        LOGGER.info("ClientSender stopped");
    }

    private void sendMessage(DataOutputStream outputStream, String message) throws IOException {
        outputStream.writeUTF(message);
        outputStream.flush();
    }
}

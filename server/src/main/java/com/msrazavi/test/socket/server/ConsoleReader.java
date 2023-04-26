package com.msrazavi.test.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleReader.class);
    private final BufferedReader bufferedReader;

    public ConsoleReader() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void sendMessage(String[] split) {
        final String id = split[0];
        ChannelRepository.instance().get(id).ifPresentOrElse(
                channel -> channel.sendMessage(split[1]),
                () -> LOGGER.error("there is no observer for {}", id)
        );
    }

    @Override
    public void run() {
        String inputText = "start";
        try {
            while (!inputText.equals("exit")) {
                LOGGER.info("waiting for the input message (for send to client first enter its id; exit)");
                final String line = this.bufferedReader.readLine();
                final String[] split = line.split(" ");
                if (split.length == 2) {
                    sendMessage(split);
                } else if (inputText.equals("list")) {
                    final String log = ChannelRepository.instance().channelList()
                            .stream()
                            .reduce("-", (s, s2) -> s + ";" + s2);
                    LOGGER.info(log);
                } else {
                    inputText = line;
                }
            }
        } catch (IOException e) {
            LOGGER.error("error on ConsoleReader", e);
        }
    }
}

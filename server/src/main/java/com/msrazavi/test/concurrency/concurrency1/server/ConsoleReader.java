package com.msrazavi.test.concurrency.concurrency1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ConsoleReader implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleReader.class);
    private final Map<Integer, ConsoleObserver> observers = new HashMap<>();
    private final BufferedReader bufferedReader;

    public ConsoleReader() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void sendMessage(String[] split) {
        final int id = Integer.parseInt(split[0]);
        ConsoleObserver observer = this.observers.get(id);
        if (observer == null) {
            LOGGER.error("there is no observer for {}", id);
        } else {
            observer.accept(split[1]);
        }
    }

    public void add(ConsoleObserver consoleObserver) {
        this.observers.put(consoleObserver.getId(), consoleObserver);
    }

    @Override
    public void run() {
        String inputText = "start";
        try {
            while (!inputText.equals("exit")) {
                LOGGER.info("Waiting for the client message");
                final String line = this.bufferedReader.readLine();
                final String[] split = line.split(" ");
                if (split.length == 2) {
                    sendMessage(split);
                } else {
                    inputText = line;
                }
            }
        } catch (IOException e) {
            LOGGER.error("error on ConsoleReader", e);
        }
    }

    public interface ConsoleObserver {

        int getId();

        void accept(String message);
    }
}

package com.msrazavi.test.concurrency.concurrency1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        try {
            final int port = 5872;
            final ConsoleReader consoleReader = new ConsoleReader();
            final Thread t1 = new Thread(consoleReader);
            Listener listener = new Listener(port, consoleReader);
            final Thread t2 = new Thread(listener);
            t1.start();
            t2.start();
            t1.join();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

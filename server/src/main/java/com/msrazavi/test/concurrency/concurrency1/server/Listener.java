package com.msrazavi.test.concurrency.concurrency1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private int counter = 1;
    private final ServerSocket server;
    private final ConsoleReader consoleReader;
    private boolean isContinue = true;

    public Listener(int port, ConsoleReader consoleReader) throws IOException {
        this.server = new ServerSocket(port);
        this.consoleReader = consoleReader;
    }

    public void stop() {
        isContinue = false;
    }

    private void startThread(Socket socket) {
        try {
            ServerReceiver receiver = new ServerReceiver(++counter, socket);
            consoleReader.add(receiver);
            Thread receiverThread = new Thread(receiver);
            Thread receiverThread2 = new Thread(receiver);
            receiverThread.start();
            receiverThread2.start();
        } catch (IOException e) {
            LOGGER.error("error on new Thread", e);
        }
    }

    @Override
    public void run() {
        while (isContinue) {
            try {
                LOGGER.info("Waiting for the client connect");
                Socket socket = this.server.accept();
                startThread(socket);
            } catch (IOException e) {
                LOGGER.error("error on Listener", e);
            }
        }
    }
}

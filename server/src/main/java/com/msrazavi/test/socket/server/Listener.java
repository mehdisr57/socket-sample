package com.msrazavi.test.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private int counter = 0;
    private final ServerSocket server;
    private boolean isContinue = true;

    public Listener(int port) throws IOException {
        this.server = new ServerSocket(port);
    }

    public void stop() {
        isContinue = false;
    }

    private void startThread(Socket socket) {
        try {
            final int id = counter++ + 1;
            Channel channel = new Channel(id, socket);
            ChannelRepository.instance().put(String.valueOf(id), channel);
            Thread receiverThread = new Thread(channel);
            receiverThread.start();
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

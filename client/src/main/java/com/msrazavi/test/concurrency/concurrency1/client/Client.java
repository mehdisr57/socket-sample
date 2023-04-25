package com.msrazavi.test.concurrency.concurrency1.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author M_SadatRazavi
 */
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public void start(int port) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        try (Socket socket = new Socket(host.getHostName(), port)) {
            final ClientReceiver clientReceiver = new ClientReceiver(socket);
            final Thread receiverThread = new Thread(clientReceiver);
            final Thread receiverThread2 = new Thread(clientReceiver);
            receiverThread.setDaemon(true);
            final Thread senderThread = new Thread(new ClientSender(socket));
            receiverThread.start();
            receiverThread2.start();
            senderThread.start();
            senderThread.join();
        } catch (InterruptedException e) {
            LOGGER.info("error on Client", e);
            Thread.currentThread().interrupt();
        }
        LOGGER.info("Client stopped");
    }
}

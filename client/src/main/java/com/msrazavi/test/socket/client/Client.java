package com.msrazavi.test.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author M_SadatRazavi
 */
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public void start(int port) {
        getSocket(port, 1, 20).ifPresentOrElse(socket -> {
            try {
                final Thread receiverThread = new Thread(new ClientReceiver(socket));
                receiverThread.setDaemon(true);
                final Thread senderThread = new Thread(new ClientSender(socket));
                receiverThread.start();
                senderThread.start();
                senderThread.join();
            } catch (InterruptedException e) {
                LOGGER.error("error on Client", e);
                Thread.currentThread().interrupt();
            } finally {
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        LOGGER.error("error on close Socket", e);
                    }
                }
            }
        }, () -> LOGGER.warn("can't open to socket"));

        LOGGER.info("Client stopped");
    }

    private Optional<Socket> getSocket(final int port, final int retry, final int maxRetry) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), port);
            return Optional.of(socket);
        } catch (IOException exception) {
            LOGGER.info("error on Client", exception);
            if (retry > maxRetry) {
                return Optional.empty();
            }
            final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            return retry(port, retry, maxRetry, scheduledExecutor);
        }
    }

    private Optional<Socket> retry(int port, int retry, int maxRetry, ScheduledExecutorService scheduledExecutor) {
        LOGGER.info("retry to get socket for {} time (max number of attempts is equal to {})", retry, maxRetry);
        try {
            return scheduledExecutor.schedule(() -> this.getSocket(port, retry + 1, maxRetry),
                            2,
                            TimeUnit.SECONDS)
                    .get();
        } catch (ExecutionException e) {
            LOGGER.info("error on retry", e);
        } catch (InterruptedException e) {
            LOGGER.info("error on retry", e);
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }
}

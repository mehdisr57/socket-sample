package com.msrazavi.test.concurrency.concurrency1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author M_SadatRazavi
 */
public class ServerReceiver implements Runnable, ConsoleReader.ConsoleObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerReceiver.class);
    private final int id;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public ServerReceiver(int id, Socket socket) throws IOException {
        this.id = id;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        LOGGER.info("receiver started");
        write("Welcome, Your Id = " + this.id);
        String message = "";
        try {
            while (!message.equals("exit")) {
                message = inputStream.readUTF();
                LOGGER.info("Message Received: {}", message);
                write("Receive Message with Length: " + message.length());
            }
        } catch (IOException e) {
            LOGGER.error("error on read (id: " + id + ")", e);
        }
    }

    private void write(String message) {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("error on write (id: " + id + ")", e);
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void accept(String message) {
        write(message);
    }
}

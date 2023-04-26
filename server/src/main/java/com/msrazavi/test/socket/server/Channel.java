package com.msrazavi.test.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msrazavi.test.socket.common.model.MessageStatus;
import com.msrazavi.test.socket.common.util.JsonUtil;
import com.msrazavi.test.socket.common.util.MessageStatusBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

/**
 * @author M_SadatRazavi
 */
public class Channel implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Channel.class);
    private final int id;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public Channel(int id, Socket socket) throws IOException {
        this.id = id;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        LOGGER.info("receiver started");
        write("{id: " + this.id + "}");
        String message = "";
        try {
            while (!message.equals("exit")) {
                message = inputStream.readUTF();
                LOGGER.info("Message Received: {} (id: {})", message, this.id);
                final Optional<MessageStatus> messageStatus = getMessageStatus(message);
                if (messageStatus.isEmpty()) {
                    write("Receive Message with Length: " + message.length());
                } else {
                    final MessageStatus sentMessage = MessageStatusBuilder.ofInServer(String.valueOf(this.id), messageStatus.get());
                    write(sentMessage);
                    ChannelRepository.instance().get(sentMessage.getMessage().getTo()).ifPresent(channel -> {
                        MessageStatus sentToReceiver = MessageStatusBuilder.ofSentToReceiver(sentMessage);
                        channel.write(sentToReceiver);
                    });
                }
            }
        } catch (IOException e) {
            LOGGER.error("error on read (id: " + id + ")", e);
        }
    }

    private Optional<MessageStatus> getMessageStatus(String message) {
        try {
            final MessageStatus messageStatus = JsonUtil.instance().deSerialize(message, MessageStatus.class);
            return Optional.of(messageStatus);
        } catch (JsonProcessingException e) {
            LOGGER.warn("error on getMessageStatus", e);
            return Optional.empty();
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

    private void write(MessageStatus message) {
        try {
            final String json = JsonUtil.instance().serialize(message);
            outputStream.writeUTF(json);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("error on write (id: " + id + ")", e);
        }
    }

    public void sendMessage(String message) {
        write(message);
    }
}

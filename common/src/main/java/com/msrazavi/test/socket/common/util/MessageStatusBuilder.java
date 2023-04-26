package com.msrazavi.test.socket.common.util;

import com.msrazavi.test.socket.common.model.Message;
import com.msrazavi.test.socket.common.model.MessageStatus;
import com.msrazavi.test.socket.common.model.State;
import com.msrazavi.test.socket.common.model.Status;

public final class MessageStatusBuilder {

    private MessageStatusBuilder() {
    }

    public static MessageStatus ofSentToServer(String to, String text) {
        final Message message = new Message(to, text);
        final Status status = new Status(State.SENT_TO_SERVER);
        return new MessageStatus(message, status);
    }

    public static MessageStatus ofInServer(String from, MessageStatus messageStatus) {
        final Status status = new Status(State.IN_SERVER);
        messageStatus.changeStatus(status);
        messageStatus.getMessage().setFrom(from);
        return messageStatus;
    }

    public static MessageStatus ofSentToReceiver(MessageStatus messageStatus) {
        final Status status = new Status(State.SENT_TO_RECEIVER);
        messageStatus.changeStatus(status);
        return messageStatus;
    }
}

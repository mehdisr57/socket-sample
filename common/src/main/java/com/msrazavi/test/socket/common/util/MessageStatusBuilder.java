package com.msrazavi.test.socket.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msrazavi.test.socket.common.model.Message;
import com.msrazavi.test.socket.common.model.MessageStatus;
import com.msrazavi.test.socket.common.model.State;
import com.msrazavi.test.socket.common.model.Status;

public final class MessageStatusBuilder {

    private MessageStatusBuilder() {
    }

    public static String ofSentToServer(String to, String text) throws JsonProcessingException {
        final Message message = new Message(to, text);
        final Status status = new Status(State.SENT_TO_SERVER);
        final MessageStatus messageStatus = new MessageStatus(message, status);
        return JsonUtil.instance().serialize(messageStatus);
    }

    public static String ofInServer(String from, MessageStatus messageStatus) throws JsonProcessingException {
        final Status status = new Status(State.IN_SERVER);
        messageStatus.changeStatus(status);
        messageStatus.getMessage().setFrom(from);
        return JsonUtil.instance().serialize(messageStatus);
    }
}

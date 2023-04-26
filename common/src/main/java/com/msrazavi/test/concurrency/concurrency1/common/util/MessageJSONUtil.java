package com.msrazavi.test.concurrency.concurrency1.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msrazavi.test.concurrency.concurrency1.common.model.Message;

import java.time.LocalDateTime;

public final class MessageJSONUtil {

    private MessageJSONUtil() {
    }

    public static String jsonOf(String to, String text) throws JsonProcessingException {
        final Message message = new Message();
        message.setTo(to);
        message.setText(text);
        return JsonUtil.instance().serialize(message);
    }

    public static String jsonOf(Message message, String from) throws JsonProcessingException {
        message.setFrom(from);
        message.addHistory(message.getDateTime());
        message.setDateTime(LocalDateTime.now());
        return JsonUtil.instance().serialize(message);
    }
}

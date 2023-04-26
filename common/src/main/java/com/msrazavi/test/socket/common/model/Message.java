package com.msrazavi.test.socket.common.model;

/**
 * @author M_SadatRazavi
 */
public class Message {
    private String from;
    private final String to;
    private final String text;

    public Message(String to, String text) {
        this.to = to;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }
}

package com.msrazavi.test.socket.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author M_SadatRazavi
 */
public class Message {
    private String from;
    private final String to;
    private final String text;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Message(@JsonProperty("to") String to, @JsonProperty("text") String text) {
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

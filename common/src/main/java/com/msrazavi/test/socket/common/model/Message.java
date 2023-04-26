package com.msrazavi.test.socket.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author M_SadatRazavi
 */
public class Message {
    private String from;
    private String to;
    private String text;
    private LocalDateTime dateTime;
    private List<LocalDateTime> history;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<LocalDateTime> getHistory() {
        if (history == null) {
            return Collections.emptyList();
        }
        return history;
    }

    public void addHistory(LocalDateTime dateTime) {
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(dateTime);
    }
}

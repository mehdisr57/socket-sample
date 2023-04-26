package com.msrazavi.test.socket.common.model;

import java.util.ArrayList;
import java.util.List;

public class MessageStatus {

    private final Message message;
    private Status lastStatus;
    private List<Status> history;

    public MessageStatus(Message message) {
        this.message = message;
    }

    public MessageStatus(Message message, Status status) {
        this.message = message;
        this.lastStatus = status;
    }

    public Message getMessage() {
        return message;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public List<Status> getHistory() {
        return history;
    }

    public void changeStatus(Status status) {
        if (history == null) {
            history = new ArrayList<>();
        }
        if (this.lastStatus != null) {
            history.add(this.lastStatus);
        }
        this.lastStatus = status;
    }
}

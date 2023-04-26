package com.msrazavi.test.socket.common.model;

import java.time.LocalDateTime;

/**
 * @author M_SadatRazavi
 */
public class Status {
    private final State state;
    private final LocalDateTime dateTime;

    public Status(State state, LocalDateTime dateTime) {
        this.state = state;
        this.dateTime = dateTime;
    }

    public Status(State state) {
        this.state = state;
        this.dateTime = LocalDateTime.now();
    }

    public State getState() {
        return state;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}

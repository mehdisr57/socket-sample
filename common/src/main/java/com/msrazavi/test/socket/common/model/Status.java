package com.msrazavi.test.socket.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author M_SadatRazavi
 */
public class Status {
    private final State state;
    private final LocalDateTime dateTime;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Status(@JsonProperty("state") State state, @JsonProperty("dateTime") LocalDateTime dateTime) {
        this.state = state;
        this.dateTime = Objects.requireNonNullElseGet(dateTime, LocalDateTime::now);
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

package com.msrazavi.test.socket.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.locks.ReentrantLock;

public class JsonUtil {

    private static JsonUtil instance = null;
    private static final ReentrantLock lock = new ReentrantLock();
    private final ObjectMapper mapper;

    private JsonUtil() {
        this.mapper = new ObjectMapper();
    }

    public static JsonUtil instance() {
        if (instance != null) return instance;
        createInstance();
        return instance;
    }

    private static void createInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new JsonUtil();
            }
        } finally {
            lock.unlock();
        }
    }

    public <T> String serialize(T obj) throws JsonProcessingException {
        return this.mapper.writeValueAsString(obj);
    }

    public <T> T deSerialize(String json, Class<T> clazz) throws JsonProcessingException {
        return this.mapper.readValue(json, clazz);
    }
}

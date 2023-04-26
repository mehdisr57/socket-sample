package com.msrazavi.test.socket.server;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public final class ChannelRepository {
    private static ChannelRepository instance = null;
    private static final ReentrantLock lock = new ReentrantLock();
    private final Map<String, Channel> channelMap;

    private ChannelRepository() {
        channelMap = new ConcurrentHashMap<>();
    }

    public static ChannelRepository instance() {
        if (instance != null) return instance;
        createInstance();
        return instance;
    }

    public Channel put(String id, Channel channel) {
        return channelMap.put(id, channel);
    }

    public Optional<Channel> get(String id) {
        return Optional.ofNullable(channelMap.get(id));
    }

    public Set<String> channelList() {
        return channelMap.keySet();
    }

    private static void createInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ChannelRepository();
            }
        } finally {
            lock.unlock();
        }
    }
}

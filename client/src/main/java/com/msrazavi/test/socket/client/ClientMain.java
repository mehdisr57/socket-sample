package com.msrazavi.test.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author M_SadatRazavi
 */
public class ClientMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
        LOGGER.info("ClientMain started");
        Client client = new Client();
        client.start(5872);
        Runtime.getRuntime().exit(0);
    }
}

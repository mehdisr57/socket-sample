package com.msrazavi.test.concurrency.concurrency1.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author M_SadatRazavi
 */
public class ClientMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
        LOGGER.info("ClientMain started");
        try {
            Client client = new Client();
            client.start(5872);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

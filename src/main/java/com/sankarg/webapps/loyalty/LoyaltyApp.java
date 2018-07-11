package com.sankarg.webapps.loyalty;

import com.sankarg.webapps.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.exception;
import static spark.Spark.port;

/**
 * Hello world!
 */
public class LoyaltyApp {
    private final static Logger logger = LoggerFactory.getLogger(LoyaltyApp.class);

    public static void main(String[] args) throws Exception {
    }

    private static void init() {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(Integer.parseInt(AppConfig.get(AppConfig.APP_SERVICE_PORT)));
    }
}
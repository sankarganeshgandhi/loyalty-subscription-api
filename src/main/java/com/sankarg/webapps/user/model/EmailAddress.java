package com.sankarg.webapps.user.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class EmailAddress extends Address {
    private final static Logger logger = LoggerFactory.getLogger(EmailAddress.class);

    EmailAddress(String id, String value, LocationType locationType, Status status) {
        super(id, value, null, null, locationType, status);
    }

    public static EmailAddress create(String value, LocationType locationType) {
        return new EmailAddress(UUID.randomUUID().toString(), value, locationType, Status.active);
    }
}
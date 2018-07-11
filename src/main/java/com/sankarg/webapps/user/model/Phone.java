package com.sankarg.webapps.user.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Phone extends Address {
    private final static Logger logger = LoggerFactory.getLogger(Address.class);

    private PhoneType phoneType;

    Phone(String id, String value, PhoneType phoneType, Status status) {
        super(id, value, null, null, null, status);
        this.phoneType = phoneType;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public static Phone create(String value, PhoneType phoneType) {
        return new Phone(UUID.randomUUID().toString(), value, phoneType, Status.active);
    }
}
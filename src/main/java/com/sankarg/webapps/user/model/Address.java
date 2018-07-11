package com.sankarg.webapps.user.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Address {
    private final static Logger logger = LoggerFactory.getLogger(Address.class);

    private String id;
    private String value;
    private String city;
    private String postcode;
    private LocationType locationType; //home address or business address
    private Status status;

    Address(String id, String value, String city, String postcode, LocationType locationType,
            Status status) {
        this.id = id;
        this.value = value;
        this.city = city;
        this.postcode = postcode;
        this.locationType = locationType;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static Address create(String value, String city, String postcode, LocationType
            locationType) {
        return new Address(UUID.randomUUID().toString(), value, city, postcode, locationType,
            Status.active);
    }
}
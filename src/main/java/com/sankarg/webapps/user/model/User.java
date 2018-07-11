package com.sankarg.webapps.user.model;

import java.util.List;

public class User {
    public final static String FIELD_ID = "id";
    public final static String FIELD_USERNAME = "userName";
    public final static String FIELD_ADDERSSES = "addresses";
    public final static String FIELD_EMAILADDRESSES = "emailAddresses";
    public final static String FIELD_PHONENUMBERS = "phoneNumbers";
    public final static String FIELD_STATUS = "status";

    private String Id;
    private String userName;
    private List<Address> addresses;
    private List<EmailAddress> emailAddresses;
    private List<Phone> phoneNumbers;
    private Status status;

    public User(String id, String userName, Status status) {
        Id = id;
        this.userName = userName;
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public List<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
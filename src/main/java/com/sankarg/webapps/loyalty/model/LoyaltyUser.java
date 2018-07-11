package com.sankarg.webapps.loyalty.model;

import com.sankarg.webapps.user.model.Status;
import com.sankarg.webapps.user.model.User;

public class LoyaltyUser extends User {
    public LoyaltyUser(String id, String userName, Status status) {
        super(id, userName, status);
    }
}
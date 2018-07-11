package com.sankarg.webapps.auth.model;

import com.sankarg.webapps.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticatingUser extends User {
    private final static Logger logger = LoggerFactory.getLogger(AuthenticatingUser.class);

    public final static String FIELD_LOGINID = "loginId";
    public final static String FIELD_PASSWORD = "password";

    private String loginId;
    private String password;

    public AuthenticatingUser(User user, String loginId, String password) {
        super(user.getId(), user.getUserName(), user.getStatus());
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
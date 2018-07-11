package com.sankarg.webapps.auth.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedInUser extends AuthenticatingUser {
    private final static Logger logger = LoggerFactory.getLogger(LoggedInUser.class);

    public final static String FIELD_SESSIONID = "sessionId";
    public final static String FIELD_LOGINID = "loginId";
    public final static String FIELD_LOGIN_TIME = "loginTime";
    public final static String FIELD_LOGOUT_TIME = "logoutTime";

    private String sessionId;
    private long loginTime;
    private long logoutTime;

    public LoggedInUser(AuthenticatingUser user, String sessionId) {
        super(user, user.getLoginId(), null);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(long logoutTime) {
        this.logoutTime = logoutTime;
    }
}

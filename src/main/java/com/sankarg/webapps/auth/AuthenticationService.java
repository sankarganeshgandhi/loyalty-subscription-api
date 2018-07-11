package com.sankarg.webapps.auth;

import com.sankarg.webapps.auth.model.AuthenticatingUser;
import com.sankarg.webapps.auth.model.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final static Map<String, LoggedInUser> loggedInUserMap = new HashMap<>();

    public static boolean login(final String userIdentifier, final String password, final String sessionId) {
        AuthenticatingUser user = AuthDAO.findByLoginId(userIdentifier, password);
        boolean loginFlag = false;
        if (user != null) {
            loginFlag = true;
            LoggedInUser loggedInUser = new LoggedInUser(user, sessionId);
            loggedInUser.setLoginTime(System.currentTimeMillis());
            AuthDAO.createSession(loggedInUser);
            loggedInUserMap.put(sessionId, loggedInUser);
            return loginFlag;
        }
        return loginFlag;
    }

    public static boolean logout(final String sessionId) {
        LoggedInUser user = AuthDAO.findBySessionId(sessionId);
        if (user != null) {
            user.setLogoutTime(System.currentTimeMillis());
            AuthDAO.updateSession(user);
            loggedInUserMap.remove(sessionId);
            return true;
        }
        return false;
    }
}
package com.sankarg.webapps;

import com.sankarg.webapps.auth.AuthDAO;
import com.sankarg.webapps.auth.AuthenticatingUserCodec;
import com.sankarg.webapps.auth.AuthenticationService;
import com.sankarg.webapps.auth.LoggedInUserCodec;
import com.sankarg.webapps.auth.model.AuthenticatingUser;
import com.sankarg.webapps.user.UserCodec;
import com.sankarg.webapps.user.UserDAO;
import com.sankarg.webapps.user.model.Status;
import com.sankarg.webapps.user.model.User;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Hello world!
 */
public class ServiceApp {
    private final static Logger logger = LoggerFactory.getLogger(ServiceApp.class);

    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        final Thread mainThread = Thread.currentThread();
        runtime.addShutdownHook(new Thread() {
            public void run() {
            DBManager.close();
            mainThread.interrupt();
            }
        });

        AppConfig.init();

        List<Class> entityClassList = new ArrayList<>();
        entityClassList.add(UserCodec.class);
        entityClassList.add(AuthenticatingUserCodec.class);
        entityClassList.add(LoggedInUserCodec.class);
        DBManager.init(entityClassList);

        init();
    }

    private static void init() {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(Integer.parseInt(AppConfig.get(AppConfig.APP_SERVICE_PORT)));

        post("/users", (req, res) -> {
            List<NameValuePair> pairs = URLEncodedUtils.parse(req.body(), Charset.defaultCharset());
            Map<String, String> params = toMap(pairs);
            req.session().attribute("params", params);
            createUser(req);
            res.header("content-type", "application/json");
            res.status(HttpStatus.CREATED_201);
            return "";
        });

        post("/login", (req, res) -> {
            List<NameValuePair> pairs = URLEncodedUtils.parse(req.body(), Charset.defaultCharset());
            Map<String, String> params = toMap(pairs);
            req.session().attribute("params", params);
            String sessionId = loginUser(req);
            res.header("content-type", "application/json");
            if (sessionId == null) {
                res.status(HttpStatus.UNAUTHORIZED_401);
                return"";
            } else {
                res.status(HttpStatus.OK_200);
                return sessionId;
            }
        });

        post("/logout", (req, res) -> {
            String sessionId = logoutUser(req);
            res.header("content-type", "application/json");
            if (sessionId == null) {
                res.status(HttpStatus.UNAUTHORIZED_401);
                return "";
            } else {
                res.status(HttpStatus.OK_200);
                res.body("sessionid:" + sessionId);
                return sessionId;
            }
        });
    }

    private static void createUser(Request req) {
        String userName = (String) getParamValue(req, "userName");
        User newUser = new User(DBManager.getNextId(), userName, Status.created);
        UserDAO.create(newUser);
        String loginId = (String) getParamValue(req, "loginId");
        String password = (String) getParamValue(req, "password");
        AuthenticatingUser authUser = new AuthenticatingUser(newUser, loginId, password);
        AuthDAO.create(authUser);
    }

    private static String loginUser(Request req) {
        String loginId = (String) getParamValue(req, "loginId");
        String password = (String) getParamValue(req, "password");
        if (AuthenticationService.login(loginId, password, req.session().id())) {
            return req.session().id();
        }
        return null;
    }

    private static String logoutUser(Request req) {
        if (AuthenticationService.logout(req.session().id())) {
            return req.session().id();
        }
        return null;
    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for(int i=0; i<pairs.size(); i++){
            NameValuePair pair = pairs.get(i);
            map.put(pair.getName(), pair.getValue());
        }
        return map;
    }

    private static Object getParamValue(Request req, String fieldName) {
        return ((Map<String, Object>) req.session().attribute("params")).get(fieldName);
    }
}
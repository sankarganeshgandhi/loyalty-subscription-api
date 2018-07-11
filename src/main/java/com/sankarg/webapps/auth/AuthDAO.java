package com.sankarg.webapps.auth;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.sankarg.webapps.DBManager;
import com.sankarg.webapps.auth.model.AuthenticatingUser;
import com.sankarg.webapps.auth.model.LoggedInUser;
import org.bson.BsonDocument;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthDAO {
    private final static Logger logger = LoggerFactory.getLogger(AuthDAO.class);

    private final static String APP_DS_AUTH_COLLECTION = "auth-collection";

    private final static String APP_DS_SESSION_COLLECTION = "auth-session-collection";

    public static AuthenticatingUser findByLoginId(String loginId) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(AuthenticatingUser.FIELD_LOGINID, loginId);
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        FindIterable<AuthenticatingUser> userCollection = docCollection.find(whereQuery, AuthenticatingUser.class);
        AuthenticatingUser user = null;
        if (userCollection.iterator().hasNext()) {
            user = userCollection.iterator().next();
        }
        return user;
    }

    public static AuthenticatingUser findByLoginId(final String loginId, final String password) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(AuthenticatingUser.FIELD_LOGINID, loginId);
        whereQuery.put(AuthenticatingUser.FIELD_PASSWORD, password);
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        FindIterable<AuthenticatingUser> userCollection = docCollection.find(whereQuery, AuthenticatingUser.class);
        AuthenticatingUser user = null;
        if (userCollection.iterator().hasNext()) {
            user = userCollection.iterator().next();
        }
        return user;
    }

    public static void create(AuthenticatingUser newUser) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        Document document = Document.parse(DBManager.getJSON(newUser));
        docCollection.insertOne(document);
    }

    public static void update(AuthenticatingUser updatedUser) {
        /*BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(AuthenticatingUser.FIELD_LOGINID, updatedUser.getLoginId());

        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        FindIterable<AuthenticatingUser> todoCollection = docCollection.find(whereQuery, AuthenticatingUser.class);
        AuthenticatingUser existingUser = todoCollection.first();*/
        AuthenticatingUser existingUser = findByLoginId(updatedUser.getLoginId());
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        docCollection.replaceOne(BsonDocument.parse(DBManager.getJSON(existingUser)),
                Document.parse(DBManager.getJSON(updatedUser)));
    }

    public static void delete(AuthenticatingUser user) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        docCollection.deleteOne(Document.parse(DBManager.getJSON(user)));
    }

    public static void delete(List<AuthenticatingUser> userList) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_AUTH_COLLECTION);
        for (AuthenticatingUser user : userList) {
            docCollection.deleteMany(Document.parse(DBManager.getJSON(user)));
        }
    }

    public static LoggedInUser findBySessionId(String sessionId) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(LoggedInUser.FIELD_SESSIONID, sessionId);
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_SESSION_COLLECTION);
        FindIterable<LoggedInUser> userCollection = docCollection.find(whereQuery, LoggedInUser.class);
        LoggedInUser user = null;
        if (userCollection.iterator().hasNext()) {
            user = userCollection.iterator().next();
        }
        return user;
    }

    public static void createSession(final LoggedInUser user) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_SESSION_COLLECTION);
        Document document = Document.parse(DBManager.getJSON(user));
        docCollection.insertOne(document);
    }

    public static void updateSession(LoggedInUser updatedUser) {
        /*BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(LoggedInUser.FIELD_SESSIONID, updatedUser.getSessionId());

        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_SESSION_COLLECTION);
        FindIterable<LoggedInUser> userCollection = docCollection.find(whereQuery, LoggedInUser.class);
        LoggedInUser existingUser = userCollection.first();*/
        LoggedInUser existingUser = findBySessionId(updatedUser.getSessionId());
        logger.info("Existing User: " + DBManager.getJSON(existingUser));
        logger.info("Updated User: " + DBManager.getJSON(updatedUser));
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_SESSION_COLLECTION);
        UpdateResult result = docCollection.replaceOne(BsonDocument.parse(DBManager.getJSON(existingUser)),
            Document.parse(DBManager.getJSON(updatedUser)));
        logger.info("Number of rows updated: " + String.valueOf(result.getMatchedCount()));
    }
}
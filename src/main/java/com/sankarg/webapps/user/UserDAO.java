package com.sankarg.webapps.user;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.sankarg.webapps.DBManager;
import com.sankarg.webapps.user.model.Status;
import com.sankarg.webapps.user.model.User;
import org.bson.BsonDocument;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private final static String APP_DS_DB_COLLECTION = "appuser";

    public static List<User> findAll() {
        BasicDBObject whereQuery = new BasicDBObject();
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        List<User> userList = new ArrayList<>();
        for(User user : docCollection.find(whereQuery, User.class)) {
            userList.add(user);
        }
        return userList;
    }

    public static User findById(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(User.FIELD_ID, id);
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        FindIterable<User> userCollection = docCollection.find(whereQuery, User.class);
        User user = null;
        if (userCollection.iterator().hasNext()) {
            user = userCollection.iterator().next();
        }
        return user;
    }

    public static List<User> findByStatus(Status status) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(User.FIELD_STATUS, status.toString());

        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        FindIterable<User> userCollection = docCollection.find(whereQuery, User.class);
        List<User> userList = new ArrayList<>();
        for (User user : userCollection) {
            userList.add(user);
        }
        return userList;
    }

    public static void create(User newUser) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        Document document = Document.parse(DBManager.getJSON(newUser));
        docCollection.insertOne(document);
    }

    public static void update(User updatedUser) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(User.FIELD_ID, updatedUser.getId());

        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        FindIterable<User> todoCollection = docCollection.find(whereQuery, User.class);
        User existingUser = todoCollection.first();
        docCollection.replaceOne(BsonDocument.parse(DBManager.getJSON(existingUser)),
                Document.parse(DBManager.getJSON(updatedUser)));
    }

    public static void delete(User user) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        docCollection.deleteOne(Document.parse(DBManager.getJSON(user)));
    }

    public static void delete(List<User> userList) {
        MongoCollection<Document> docCollection = DBManager.getDatabase().getCollection(APP_DS_DB_COLLECTION);
        for (User user : userList) {
            docCollection.deleteMany(Document.parse(DBManager.getJSON(user)));
        }
    }
}